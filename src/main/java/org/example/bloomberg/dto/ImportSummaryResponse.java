package org.example.bloomberg.dto;

import java.util.List;

public record  ImportSummaryResponse(
        long importedCount ,
        int  skipped,
        List<DealError> errors
) {
    public record DealError(String dealUniqueId, String reason) {}
}
