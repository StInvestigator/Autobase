package org.example.autobase.service.car;

import org.example.autobase.entity.Car;

import java.math.BigDecimal;
import java.util.List;

public interface CarService {
    void save(Car car);
    void saveAll(List<Car> cars);
    List<Car> findAll();
    void deleteAll();
    void setBrokenAfterDayRiding();
    Car findMostEfficientFreeCar(BigDecimal weight);
    boolean isCarForWeightAvailable(BigDecimal weight);
}
