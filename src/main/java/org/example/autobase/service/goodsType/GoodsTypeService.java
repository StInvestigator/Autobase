package org.example.autobase.service.goodsType;

import org.example.autobase.entity.GoodsType;

import java.util.List;

public interface GoodsTypeService {
    void save(GoodsType goodsType);
    void saveAll(List<GoodsType> goodsTypes);
    List<GoodsType> findAll();
    void deleteAll();
}
