package org.example.autobase.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@ToString(exclude = {"id","isFree"})
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private Integer experience;

    private BigDecimal balance = BigDecimal.ZERO;

    private Boolean isFree = true;
}
