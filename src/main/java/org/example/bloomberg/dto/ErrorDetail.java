package org.example.bloomberg.dto;


import lombok.Builder;
import lombok.Data;

@Builder
public record ErrorDetail(
        String objectName,
        String fieldName,
        String rejectedValue,
        String message
) {
}
