package org.example.autobase.service.util;

import org.example.autobase.entity.Request;
import org.example.autobase.service.car.CarService;
import org.example.autobase.service.driver.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestDoabilityChecker {
    @Autowired
    private DriverService driverService;

    @Autowired
    private CarService carService;

    public boolean isRequestDoable(Request request) {
        return isCarAvailable(request) && isDriverAvailable(request);
    }

    public boolean isCarAvailable(Request request) {
        return carService.isCarForWeightAvailable(request.getGoodsWeight());
    }

    public boolean isDriverAvailable(Request request) {
        return driverService.isDriverWithExperienceAvailable(request.getGoodsType().getExperienceNeeded());
    }
}
