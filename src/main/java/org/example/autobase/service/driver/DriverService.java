package org.example.autobase.service.driver;

import org.example.autobase.entity.Driver;

import java.math.BigDecimal;
import java.util.List;

public interface DriverService {
    void save(Driver driver);
    void saveAll(List<Driver> drivers);
    Driver findById(Long id);
    List<Driver> findAll();
    void deleteAll();
    void endWork(Driver driver, BigDecimal salary);
    List<Driver> getFreeDriversWithExperienceGreaterThan(Integer experience);
    boolean isDriverWithExperienceAvailable(Integer experience);
    List<Driver> findDriversWithMaxBalance();
    List<Driver> findAllFreeDrivers();
    BigDecimal getMaxBalance();
}
