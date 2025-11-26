package org.example.bloomberg.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Builder
public record FxDealRequest(
        @NotBlank(message = "Deal Unique Id is required")
        String dealUniqueId,

        @NotBlank(message = "From Currency is required")
        @Size(min = 3, max = 3, message = "Currency code must be 3 characters")
        @Pattern(regexp = "[A-Z]{3}", message = "Currency must be uppercase ISO code")
        String fromCurrencyIso,

        @NotBlank(message = "To Currency is required")
        @Size(min = 3, max = 3, message = "Currency code must be 3 characters")
        String toCurrencyIso,

        @NotNull(message = "Timestamp is required")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime dealTimestamp,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be greater than 0")
        BigDecimal dealAmount
) {
}
