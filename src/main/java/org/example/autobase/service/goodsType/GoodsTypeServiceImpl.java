package org.example.autobase.service.goodsType;

import org.example.autobase.entity.GoodsType;
import org.example.autobase.repository.GoodsTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GoodsTypeServiceImpl implements GoodsTypeService {
    @Autowired
    private GoodsTypeRepository goodsTypeRepository;

    @Override
    public void save(GoodsType goodsType) {
        goodsTypeRepository.save(goodsType);
    }

    @Override
    public void saveAll(List<GoodsType> goodsTypes) {
        goodsTypeRepository.saveAll(goodsTypes);
    }

    @Override
    public List<GoodsType> findAll() {
        return goodsTypeRepository.findAll();
    }

    @Override
    public void deleteAll() {
        goodsTypeRepository.deleteAll();
    }
}
