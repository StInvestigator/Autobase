package org.example.autobase.service.completedTrip;

import org.example.autobase.entity.CompletedTrip;
import org.example.autobase.entity.DestinationPoint;
import org.example.autobase.entity.Driver;
import org.example.autobase.entity.Trip;

import java.math.BigDecimal;
import java.util.List;

public interface CompletedTripService {
    void save(CompletedTrip completedTrip);
    CompletedTrip save(Trip trip);
    void saveAll(List<CompletedTrip> completedTrips);
    List<CompletedTrip> findAll();
    void deleteAll();
    BigDecimal getTotalWeightTransportedByDriver(Driver driver);
    BigDecimal getTotalWeightTransportedToDestination(DestinationPoint destinationPoint);
}
