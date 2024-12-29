package org.example.autobase.service.completedTrip;

import org.example.autobase.entity.CompletedTrip;
import org.example.autobase.entity.DestinationPoint;
import org.example.autobase.entity.Driver;
import org.example.autobase.entity.Trip;
import org.example.autobase.repository.CompletedTripRepository;
import org.example.autobase.service.VirtualDateStaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CompletedTripServiceImpl implements CompletedTripService {
    @Autowired
    private CompletedTripRepository completedTripRepository;

    @Override
    public void save(CompletedTrip completedTrip) {
        completedTripRepository.save(completedTrip);
    }

    @Override
    public CompletedTrip save(Trip trip) {
        CompletedTrip completedTrip = new CompletedTrip();
        completedTrip.setCompletionDate(VirtualDateStaticService.getCurerentVirtualUtilDate());
        completedTrip.setCar(trip.getCar());
        completedTrip.setDriver(trip.getDriver());
        completedTrip.setPayment(trip.getRequest().getPayment());
        completedTrip.setDestinationPoint(trip.getRequest().getDestinationPoint());
        completedTrip.setGoodsType(trip.getRequest().getGoodsType());
        completedTrip.setGoodsWeight(trip.getRequest().getGoodsWeight());

        return completedTripRepository.save(completedTrip);
    }

    @Override
    public void saveAll(List<CompletedTrip> completedTrips) {
        completedTripRepository.saveAll(completedTrips);
    }

    @Override
    public List<CompletedTrip> findAll() {
        return completedTripRepository.findAll();
    }

    @Override
    public void deleteAll() {
        completedTripRepository.deleteAll();
    }

    @Override
    public BigDecimal getTotalWeightTransportedByDriver(Driver driver) {
        return completedTripRepository.sumWeightTransportedByDriver(driver);
    }

    @Override
    public BigDecimal getTotalWeightTransportedToDestination(DestinationPoint destinationPoint) {
        return completedTripRepository.sumWeightTransportedToDestination(destinationPoint);
    }
}
