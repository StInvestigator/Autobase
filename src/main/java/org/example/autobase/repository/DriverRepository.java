package org.example.autobase.repository;

import org.example.autobase.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findAllByExperienceGreaterThanEqualAndIsFreeOrderByExperienceAsc(Integer experience, Boolean isFree);

    @Query(value = "SELECT d FROM Driver d WHERE d.balance = (select max(d2.balance) from Driver d2)")
    List<Driver> findDriversWithMaxBalance();
}
