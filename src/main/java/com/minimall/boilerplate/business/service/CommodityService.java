package com.minimall.boilerplate.business.service;

import com.minimall.boilerplate.business.dto.CommodityDTO;
import com.minimall.boilerplate.business.entity.Commodity;
import com.minimall.boilerplate.business.entity.Dictionary;
import com.minimall.boilerplate.business.repository.CommodityRepository;
import com.minimall.boilerplate.business.repository.DictionaryRepository;
import com.minimall.boilerplate.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class CommodityService {

    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private DictionaryRepository dictionaryRepository;

    // 新增
    @Transactional
    public boolean commodityAdd(CommodityDTO commodityDTO){
        Commodity commodity = new Commodity();

        commodity.setHeadline(commodityDTO.getHeadline());
        commodity.setSubtitle(commodityDTO.getSubtitle());
        commodity.setOriginalPrice(commodityDTO.getOriginalPrice());
        commodity.setSellingPrice(commodityDTO.getSellingPrice());
        // type 类型
        Optional<Dictionary> dictionary = dictionaryRepository.findById(commodityDTO.getType());
        if(dictionary.isPresent()){
            commodity.setDictionary(dictionary.get());
        }
        commodity.setProducedDate(Timestamp.valueOf(commodityDTO.getProducedDate()));
        commodity.setStartGuaPeriodDate(Timestamp.valueOf(commodityDTO.getStartGuaPeriodDate()));
        commodity.setEndGuaPeriodDate(Timestamp.valueOf(commodityDTO.getEndGuaPeriodDate()));
        commodity.setInventory(commodityDTO.getInventory());
        commodity.setSalesVolume(commodityDTO.getSalesVolume());
        commodity.setManufacturer(commodityDTO.getManufacturer());
        commodity.setCommodityStatus(Constants.COMMODITY_STATUS_00);
        commodityRepository.save(commodity);
        return true;
    }


}
