package org.example.autobase.service.destinationPoint;

import org.example.autobase.entity.DestinationPoint;

import java.util.List;

public interface DestinationPointService {
    void save(DestinationPoint destinationPoint);
    void saveAll(List<DestinationPoint> destinationPoints);
    List<DestinationPoint> findAll();
    void deleteAll();
}
