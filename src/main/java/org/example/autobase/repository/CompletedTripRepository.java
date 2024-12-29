package org.example.autobase.repository;

import org.example.autobase.entity.CompletedTrip;
import org.example.autobase.entity.DestinationPoint;
import org.example.autobase.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
@Transactional
public interface CompletedTripRepository extends JpaRepository<CompletedTrip, Long> {
    @Query("SELECT sum(ct.goodsWeight) FROM CompletedTrip ct " +
            "JOIN Driver d ON ct.driver = d " +
            "WHERE d = :driver")
    BigDecimal sumWeightTransportedByDriver(Driver driver);

    @Query("SELECT sum(ct.goodsWeight) FROM CompletedTrip ct " +
            "JOIN DestinationPoint d ON ct.destinationPoint = d " +
            "WHERE d = :destinationPoint")
    BigDecimal sumWeightTransportedToDestination(DestinationPoint destinationPoint);
}
