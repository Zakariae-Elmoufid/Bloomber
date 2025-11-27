package org.example.bloomberg.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bloomberg.dto.FxDealRequest;
import org.example.bloomberg.dto.ImportSummaryResponse;
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
    public ResponseEntity<ImportSummaryResponse> importDeals(@RequestBody  List<FxDealRequest> dealRequests) {
        ImportSummaryResponse summary = service.importDeals(dealRequests);
        return ResponseEntity.ok(summary);
    }


}
