package org.example.bloomberg.service;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bloomberg.dto.ImportSummaryResponse;
import org.example.bloomberg.entity.FxDeal;
import org.example.bloomberg.dto.FxDealRequest;
import org.example.bloomberg.repository.FxDealRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FxDealServiceImpl implements FxDealService {

    private final FxDealRepository repository;
    private final Validator validator;
   private  List<ImportSummaryResponse.DealError> errors = new ArrayList<>();


    public ImportSummaryResponse importDeals(List<FxDealRequest> requests) {
        log.info("Starting import of {} deals", requests.size());
        long importedCount = 0;

        for (FxDealRequest request : requests) {
            Set<ConstraintViolation<FxDealRequest>> violations = validator.validate(request);

            if (!violations.isEmpty()) {
                String errorMsg = violations.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", "));
                errors.add(new ImportSummaryResponse.DealError(request.dealUniqueId(), errorMsg));
                log.warn("Validation failed for deal {}: {}", request.dealUniqueId(), errorMsg);
                continue;
            }

            try {
                processSingleDeal(request);
                importedCount++;
            } catch (Exception e) {
                errors.add(new ImportSummaryResponse.DealError(request.dealUniqueId(),  "An unexpected error occurred: " + e.getMessage()));
                log.error("Failed to import deal ID: {}. Reason: {}", request.dealUniqueId(), e.getMessage());
            }

        }

        return new ImportSummaryResponse(importedCount, errors.size(), errors);
    }

    public void processSingleDeal(FxDealRequest request) {
        // 1. Idempotency Check (Duplicate Check)
        if (repository.existsByDealUniqueId(request.dealUniqueId())) {
            log.warn("Skipping duplicate deal ID: {}", request.dealUniqueId());
            String reason = "Duplicate entry for dealUniqueId";
            errors.add(new ImportSummaryResponse.DealError(request.dealUniqueId(), reason));
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