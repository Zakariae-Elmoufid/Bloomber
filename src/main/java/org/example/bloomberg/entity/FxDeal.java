package org.example.bloomberg.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "fx_deals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FxDeal {
    @Id
    @Column(name = "deal_unique_id", nullable = false, unique = true)
    private String dealUniqueId;

    @Column(name = "from_currency_iso", length = 3, nullable = false)
    private String fromCurrencyIso;

    @Column(name = "to_currency_iso", length = 3, nullable = false)
    private String toCurrencyIso;

    @Column(name = "deal_timestamp", nullable = false)
    private LocalDateTime dealTimestamp;

    @Column(name = "deal_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal dealAmount;
}
