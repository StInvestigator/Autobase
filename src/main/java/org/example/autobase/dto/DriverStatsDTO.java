package org.example.autobase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DriverStatsDTO {
    private String fullName;
    private BigDecimal transported;
    private BigDecimal balance;
    private boolean isMaxBalance;
}
