package org.example.autobase.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer daysRemaining;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable=false)
    private Request request;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable=false)
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable=false)
    private Car car;
}
