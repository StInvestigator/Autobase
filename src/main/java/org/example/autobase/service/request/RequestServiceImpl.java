package org.example.autobase.service.request;

import org.example.autobase.entity.DestinationPoint;
import org.example.autobase.entity.GoodsType;
import org.example.autobase.entity.Request;
import org.example.autobase.repository.RequestRepository;
import org.example.autobase.service.car.CarService;
import org.example.autobase.service.destinationPoint.DestinationPointService;
import org.example.autobase.service.driver.DriverService;
import org.example.autobase.service.goodsType.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {
    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private GoodsTypeService goodsTypeService;

    @Autowired
    private DestinationPointService destinationPointService;

    @Override
    public void save(Request request) {
        requestRepository.save(request);
    }

    @Override
    public void saveAll(List<Request> requests) {
        requestRepository.saveAll(requests);
    }

    @Override
    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    @Override
    public List<Request> findAllNotTaken() {
        return requestRepository.findAllByIsTaken(false);
    }

    @Override
    public void deleteAll() {
        requestRepository.deleteAll();
    }

    @Override
    public List<Request> getNextDayRandomRequests() {
        Random random = new Random();
        List<GoodsType> goodsTypes = goodsTypeService.findAll();
        List<DestinationPoint> destinationPoints = destinationPointService.findAll();
        List<Request> requests = new ArrayList<>();
        int count = random.nextInt(2, 4);
        for (int i = 0; i < count; i++) {
            Request request = new Request();

            GoodsType goodsType = goodsTypes.get(random.nextInt(goodsTypes.size()));
            request.setGoodsType(goodsType);

            DestinationPoint destinationPoint = destinationPoints.get(random.nextInt(destinationPoints.size()));
            request.setDestinationPoint(destinationPoint);

            request.setPayment(BigDecimal.valueOf(random.nextInt(100, 300) / 100.
                    * destinationPoint.getDistance() / 100 * (goodsType.getExperienceNeeded() + 1) * 50));

            request.setGoodsWeight(BigDecimal.valueOf(random.nextInt(1000, 4000) / 100.));

            requests.add(request);
        }
        return requestRepository.saveAll(requests);
    }
}
