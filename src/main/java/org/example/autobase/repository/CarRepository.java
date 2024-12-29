package org.example.autobase.repository;

import org.example.autobase.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Transactional
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByIsFree(Boolean isFree);
    Car findFirstByMaxWeightIsGreaterThanEqualAndIsFreeOrderByMaxWeightAsc(BigDecimal maxWeight, Boolean isFree);
}
