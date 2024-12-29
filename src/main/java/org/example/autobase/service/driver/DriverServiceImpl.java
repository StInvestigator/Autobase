package org.example.autobase.service.driver;

import org.example.autobase.entity.Driver;
import org.example.autobase.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class DriverServiceImpl implements DriverService {
    @Autowired
    private DriverRepository driverRepository;

    @Override
    public void save(Driver driver) {
        driverRepository.save(driver);
    }

    @Override
    public void saveAll(List<Driver> drivers) {
        driverRepository.saveAll(drivers);
    }

    @Override
    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    @Override
    public void deleteAll() {
        driverRepository.deleteAll();
    }

    @Override
    public void endWork(Driver driver, BigDecimal salary) {
        driver.setBalance(driver.getBalance().add(salary));
        driver.setIsFree(true);
        driverRepository.save(driver);
    }

    @Override
    public List<Driver> getFreeDriversWithExperienceGreaterThan(Integer experience) {
        return driverRepository.findAllByExperienceGreaterThanEqualAndIsFreeOrderByExperienceAsc(experience, true);
    }

    @Override
    public List<Driver> findDriversWithMaxBalance() {
        return driverRepository.findDriversWithMaxBalance();
    }
}
