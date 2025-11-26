package org.example.bloomberg.service;


import org.example.bloomberg.dto.FxDealRequest;
import org.example.bloomberg.entity.FxDeal;
import org.example.bloomberg.repository.FxDealRepository;
import org.example.bloomberg.service.FxDealService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FxDealServiceTest {

    @Mock
    private FxDealRepository repository;

    @InjectMocks
    private FxDealServiceImpl service;

    @Test
    void shouldSaveValidDeal() {
        FxDealRequest req =  FxDealRequest.builder()
                .dealUniqueId("ID-123")
                .dealAmount(BigDecimal.TEN)
                .fromCurrencyIso("USD")
                .toCurrencyIso("EUR")
                .dealTimestamp(LocalDateTime.now())
                .build();

        when(repository.existsByDealUniqueId("ID-123")).thenReturn(false);

        service.importDeals(Collections.singletonList(req));

        verify(repository, times(1)).save(any(FxDeal.class));
    }

    @Test
    void shouldSkipDuplicateDeal() {
        FxDealRequest req = FxDealRequest.builder()
                .dealUniqueId("ID-123").build();

        when(repository.existsByDealUniqueId("ID-123")).thenReturn(true);

        service.importDeals(Collections.singletonList(req));

        verify(repository, never()).save(any(FxDeal.class));
    }
}