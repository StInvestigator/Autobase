package org.example.autobase.service.car;

import org.example.autobase.entity.Car;
import org.example.autobase.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;

    @Override
    public void save(Car car) {
        carRepository.save(car);
    }

    @Override
    public void saveAll(List<Car> cars) {
        carRepository.saveAll(cars);
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public void deleteAll() {
        carRepository.deleteAll();
    }

    @Override
    public void setBrokenAfterDayRiding() {
        List<Car> brokenCars = new ArrayList<>();
        Random random = new Random();
        for (Car car : carRepository.findAllByIsFree(false)) {
            if (random.nextInt(0, 100) == 1) {
                car.setIsBroken(true);
                brokenCars.add(car);
            }
        }
        carRepository.saveAll(brokenCars);
    }

    @Override
    public Car findMostEfficientFreeCar(BigDecimal weight) {
        return carRepository.findFirstByMaxWeightIsGreaterThanEqualAndIsFreeOrderByMaxWeightAsc(weight, true);
    }
}
