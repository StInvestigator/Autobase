package org.example.autobase.service.trip;

import org.example.autobase.entity.*;

import java.util.List;

public interface TripService {
    void save(Trip trip);
    void saveAll(List<Trip> trips);
    List<Trip> findAll();
    void deleteAll();
    CompletedTrip tripCompleted(Trip trip);
    void tripFailed(Trip trip);
    List<CompletedTrip> nextDay();
    boolean sendToTrip(Request request, Driver driver);
}
