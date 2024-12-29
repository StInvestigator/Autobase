package org.example.autobase.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@ToString(exclude = "id")
@Table(name = "destination_points")
public class DestinationPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer distance;
}
