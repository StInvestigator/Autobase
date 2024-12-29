package org.example.autobase.repository;

import org.example.autobase.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findAllByCar_IsBroken(Boolean car_isBroken);
}
