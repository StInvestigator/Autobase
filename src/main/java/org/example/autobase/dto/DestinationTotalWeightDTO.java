package org.example.autobase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DestinationTotalWeightDTO {
    private String name;
    private BigDecimal weight;
}
