package org.example.autobase.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@ToString(exclude = "id")
@Table(name = "completed_trips")
public class CompletedTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date completionDate;

    private BigDecimal goodsWeight;

    private BigDecimal payment;

    @ManyToOne
    @JoinColumn(name = "destination_point_id", nullable=false)
    private DestinationPoint destinationPoint;

    @ManyToOne
    @JoinColumn(name = "goods_type_id", nullable=false)
    private GoodsType goodsType;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable=false)
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable=false)
    private Car car;
}
