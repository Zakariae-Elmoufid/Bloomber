package org.example.bloomberg.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bloomberg.dto.FxDealRequest;
import org.example.bloomberg.service.FxDealService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Validated
public class FxDealController {

    private final FxDealService service;

    @PostMapping
    public ResponseEntity<String> importDeals(@RequestBody @Valid List<FxDealRequest> dealRequests) {
        service.importDeals(dealRequests);
        return ResponseEntity.ok("Batch processing completed. Check logs for details on specific rows.");
    }


}
