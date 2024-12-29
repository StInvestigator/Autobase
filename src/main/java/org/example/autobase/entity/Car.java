package org.example.autobase.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@ToString(exclude = {"id", "isFree"})
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal maxWeight;

    private Boolean isBroken = false;

    private Boolean isFree = true;
}
