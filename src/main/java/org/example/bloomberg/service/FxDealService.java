package org.example.bloomberg.service;

import org.example.bloomberg.dto.FxDealRequest;
import org.example.bloomberg.dto.ImportSummaryResponse;

import java.util.List;

public interface FxDealService {

    public ImportSummaryResponse importDeals(List<FxDealRequest> requests);
    public void processSingleDeal(FxDealRequest request);
}
