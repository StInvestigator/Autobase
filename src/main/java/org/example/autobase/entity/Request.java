package org.example.autobase.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@ToString(exclude = {"id","isTaken"})
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal goodsWeight;

    private BigDecimal payment;

    private Boolean isTaken = false;

    @ManyToOne
    @JoinColumn(name = "destination_point_id", nullable=false)
    private DestinationPoint destinationPoint;

    @ManyToOne
    @JoinColumn(name = "goods_type_id", nullable=false)
    private GoodsType goodsType;
}
