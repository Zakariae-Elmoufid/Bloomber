package org.example.bloomberg.service;

import org.example.bloomberg.dto.FxDealRequest;

import java.util.List;

public interface FxDealService {

    public void importDeals(List<FxDealRequest> requests);
    public void processSingleDeal(FxDealRequest request);
}
