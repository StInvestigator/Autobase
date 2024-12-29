package org.example.autobase.service.destinationPoint;

import org.example.autobase.entity.DestinationPoint;
import org.example.autobase.repository.DestinationPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DestinationPointServiceImpl implements DestinationPointService {
    @Autowired
    private DestinationPointRepository destinationPointRepository;

    @Override
    public void save(DestinationPoint destinationPoint) {
        destinationPointRepository.save(destinationPoint);
    }

    @Override
    public void saveAll(List<DestinationPoint> destinationPoints) {
        destinationPointRepository.saveAll(destinationPoints);
    }

    @Override
    public List<DestinationPoint> findAll() {
        return destinationPointRepository.findAll();
    }

    @Override
    public void deleteAll() {
        destinationPointRepository.deleteAll();
    }
}
