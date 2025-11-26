package org.example.bloomberg.service;


import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bloomberg.entity.FxDeal;
import org.example.bloomberg.dto.FxDealRequest;
import org.example.bloomberg.repository.FxDealRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FxDealServiceImpl implements FxDealService {

    private final FxDealRepository repository;


    public void importDeals(List<FxDealRequest> requests) {
        log.info("Starting import of {} deals", requests.size());

        for (FxDealRequest request : requests) {
            try {
                processSingleDeal(request);
            } catch (Exception e) {
                // CORE REQUIREMENT: Catch exception per row so loop continues
                log.error("Failed to import deal ID: {}. Reason: {}", request.dealUniqueId(), e.getMessage());
            }
        }
    }

    public void processSingleDeal(FxDealRequest request) {
        // 1. Idempotency Check (Duplicate Check)
        if (repository.existsByDealUniqueId(request.dealUniqueId())) {
            log.warn("Skipping duplicate deal ID: {}", request.dealUniqueId());
            return;
        }

        // 2. Mapping
        FxDeal dealEntity = FxDeal.builder()
                .dealUniqueId(request.dealUniqueId())
                .fromCurrencyIso(request.fromCurrencyIso())
                .toCurrencyIso(request.toCurrencyIso())
                .dealTimestamp(request.dealTimestamp())
                .dealAmount(request.dealAmount())
                .build();

        // 3. Save
        repository.save(dealEntity);
        log.info("Successfully saved deal ID: {}", request.dealUniqueId());
    }

}