package com.minimall.boilerplate.business.service;

import com.minimall.boilerplate.business.dto.CommodityDTO;
import com.minimall.boilerplate.business.dto.assembler.CommodityAssembler;
import com.minimall.boilerplate.business.entity.Commodity;
import com.minimall.boilerplate.business.entity.Dictionary;
import com.minimall.boilerplate.business.entity.User;
import com.minimall.boilerplate.business.repository.CommodityRepository;
import com.minimall.boilerplate.business.repository.DictionaryRepository;
import com.minimall.boilerplate.business.repository.UserRepository;
import com.minimall.boilerplate.business.repository.CommoditySpecification;
import com.minimall.boilerplate.common.CheckUtils;
import com.minimall.boilerplate.common.Constants;
import com.minimall.boilerplate.common.Message;
import com.minimall.boilerplate.common.MessageObject;
import com.minimall.boilerplate.exception.NotFoundRequestException;
import com.sun.org.apache.bcel.internal.generic.LoadClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.Objects.nonNull;

@Service
public class CommodityService {

    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private DictionaryRepository dictionaryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommodityAssembler commodityAssembler;

    // 新增
    @Transactional
    public boolean commodityAdd(CommodityDTO commodityDTO){
        Commodity commodity = new Commodity();
        // 用户
        Optional<User> user = userRepository.findByUserId(String.valueOf(commodityDTO.getUserId()));
        if(!user.isPresent()){
            return false;
        }
        commodity.setUser(user.get());
        commodity.setInventory(commodityDTO.getInventory());
        commodity.setSalesVolume(commodityDTO.getSalesVolume());
        commodity.setManufacturer(commodityDTO.getManufacturer());
        commodity.setHeadline(commodityDTO.getHeadline());
        commodity.setSubtitle(commodityDTO.getSubtitle());
        commodity.setOriginalPrice(commodityDTO.getOriginalPrice());
        commodity.setSellingPrice(commodityDTO.getSellingPrice());
        // type 类型
        Optional<Dictionary> dictionary = dictionaryRepository.findByCode(commodityDTO.getType());
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


   // 列表
   @Transactional
   public List<CommodityDTO> commodityList(CommodityDTO commodityDTO, Pageable pageable, MessageObject mo){
       Page<Commodity> commodities = commodityRepository.findAll(CommoditySpecification.conditionQuerySpec(commodityDTO), pageable);
       if(nonNull(commodities)){
           mo.put("total", commodities.getTotalElements());
       }
       return commodities.getContent().stream()
               .map(commodityAssembler::toDTO)
               .flatMap(dto -> dto.isPresent() ? Stream.of(dto.get()) : Stream.empty())
               .collect(Collectors.toList());
   }

   // 编辑
   @Transactional
   public boolean commodityUpdate(CommodityDTO commodityDTO){
        if(!CheckUtils.isEmpty(commodityDTO.getId())){
            Optional<Commodity> commodity = commodityRepository.findById(commodityDTO.getId());
            if(commodity.isPresent()){
                if(!CheckUtils.isEmpty(commodityDTO.getCommodityStatus())){
                    commodity.get().setCommodityStatus(commodityDTO.getCommodityStatus());
                }
                if(!CheckUtils.isEmpty(commodityDTO.getInventory())){
                    commodity.get().setInventory(commodityDTO.getInventory());
                }
                if(!CheckUtils.isEmpty(commodityDTO.getSalesVolume())){
                    commodity.get().setSalesVolume(commodityDTO.getSalesVolume());
                }
                if(!CheckUtils.isEmpty(commodityDTO.getManufacturer())){
                    commodity.get().setManufacturer(commodityDTO.getManufacturer());
                }
                if(!CheckUtils.isEmpty(commodityDTO.getHeadline())){
                    commodity.get().setHeadline(commodityDTO.getHeadline());
                }
                if(!CheckUtils.isEmpty(commodityDTO.getSubtitle())){
                    commodity.get().setSubtitle(commodityDTO.getSubtitle());
                }
                if(!CheckUtils.isEmpty(commodityDTO.getOriginalPrice())){
                    commodity.get().setOriginalPrice(commodityDTO.getOriginalPrice());
                }
                if(!CheckUtils.isEmpty(commodityDTO.getSellingPrice())){
                    commodity.get().setSellingPrice(commodityDTO.getSellingPrice());
                }
                if(!CheckUtils.isEmpty(commodityDTO.getType())){
                    // type 类型
                    Optional<Dictionary> dictionary = dictionaryRepository.findByCode(commodityDTO.getType());
                    if(dictionary.isPresent()){
                        commodity.get().setDictionary(dictionary.get());
                    }
                }
                if(!CheckUtils.isEmpty(commodityDTO.getProducedDate())){
                    commodity.get().setProducedDate(Timestamp.valueOf(commodityDTO.getProducedDate()));
                }
                if(!CheckUtils.isEmpty(commodityDTO.getStartGuaPeriodDate())){
                    commodity.get().setStartGuaPeriodDate(Timestamp.valueOf(commodityDTO.getStartGuaPeriodDate()));
                }
                if(!CheckUtils.isEmpty(commodityDTO.getEndGuaPeriodDate())){
                    commodity.get().setEndGuaPeriodDate(Timestamp.valueOf(commodityDTO.getEndGuaPeriodDate()));
                }
                if(!CheckUtils.isEmpty(commodityDTO.getInventory())){
                    commodity.get().setInventory(commodityDTO.getInventory());
                }
                if(!CheckUtils.isEmpty(commodityDTO.getSalesVolume())){
                    commodity.get().setSalesVolume(commodityDTO.getSalesVolume());
                }
                if(!CheckUtils.isEmpty(commodityDTO.getManufacturer())){
                    commodity.get().setManufacturer(commodityDTO.getManufacturer());
                }
                // soldOutTime 下架时间
                if(!CheckUtils.isEmpty(commodityDTO.getCommodityStatus())
                        && commodityDTO.getCommodityStatus() == Constants.COMMODITY_STATUS_02){
                    commodity.get().setSoldOutTime(new Timestamp(System.currentTimeMillis()));
                }
                commodityRepository.save(commodity.get());
            }
        }
       return true;
   }

    // 删除
    @Transactional
    public void delete(Long id) {
        Optional<Commodity> deleteData = Optional.ofNullable(commodityRepository.findById(id))
                .orElseThrow(() -> new NotFoundRequestException(Message.E109));
        deleteData.get().deletedAtNow();
        commodityRepository.save(deleteData.get());
    }

    // 获取单条数据
    @Transactional
    public CommodityDTO commodityInfo(Long commodityId){
        Optional<Commodity> commodity = commodityRepository.findById(commodityId);
        if(commodity.isPresent()){
            Optional<CommodityDTO> commodityOptional = commodityAssembler.toDTO(commodity.get());
            if(commodityOptional.isPresent()){
                return commodityOptional.get();
            }
        }
        return null;
    }

}