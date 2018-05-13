package com.minimall.boilerplate.business.dto.assembler;

import com.minimall.boilerplate.business.dto.CommodityDTO;
import com.minimall.boilerplate.business.entity.Commodity;
import com.minimall.boilerplate.business.entity.User;
import com.minimall.boilerplate.business.repository.CommodityRepository;
import com.minimall.boilerplate.business.repository.UserRepository;
import com.minimall.boilerplate.common.Constants;
import com.minimall.boilerplate.common.DateHelper;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class CommodityAssembler implements IDTOAssembler<CommodityDTO,Commodity>{
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private UserRepository userRepository;

    public CommodityAssembler() {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.addMappings(new CommodityAssembler.CommodityDTOMap());
        modelMapper.addMappings(new CommodityAssembler.CommodityMap());
    }

    @Override
    public Optional<CommodityDTO> toDTO(Commodity commodity) {
        nonNull(commodity);
        return Optional.ofNullable(modelMapper.map(commodity, CommodityDTO.class));
    }

    @Override
    public Optional<Commodity> toEntity(CommodityDTO commodityDTO) {
        nonNull(commodityDTO);
        Commodity commodity;
        Long id = commodityDTO.getId();
        if (isNull(id))
            commodity = Optional.ofNullable(commodityRepository.getOne(id)).orElse(new Commodity());
        else
            commodity = new Commodity();
        modelMapper.map(commodityDTO, commodity);
        return Optional.of(commodity);
    }

    private class CommodityMap extends PropertyMap<CommodityDTO, Commodity> {
        @Override
        protected void configure() {

        }
    }

    private class CommodityDTOMap extends PropertyMap<Commodity, CommodityDTO> {
        @Override
        protected void configure() {
            using(toStatusName).map(source.getCommodityStatus(),destination.getCommodityStatusName());
            using(toUpdateTime).map(source.getUpdateTime(),destination.getUpdateTime());
            using(toUserName).map(source.getUpdaterId(),destination.getUpdaterName());

            using(toTime).map(source.getProducedDate(),destination.getProducedDate());
            using(toTime).map(source.getStartGuaPeriodDate(),destination.getStartGuaPeriodDate());
            using(toTime).map(source.getEndGuaPeriodDate(),destination.getEndGuaPeriodDate());

            map(source.getDictionary().getCode(),destination.getType());
            map(source.getDictionary().getName(),destination.getTypeName());

        }
    }

    // 状态值
    private Converter<Integer, String> toStatusName = new AbstractConverter<Integer, String>() {
        protected String convert(Integer status) {
            if (isNull(status))
                return "";
            String statusStr = "";
            switch (status) {
                case Constants.COMMODITY_STATUS_00:
                    statusStr = Constants.COMMODITY_STATUS_00_TXT;
                    break;
                case Constants.COMMODITY_STATUS_01:
                    statusStr = Constants.COMMODITY_STATUS_01_TXT;
                    break;
                case Constants.COMMODITY_STATUS_02:
                    statusStr = Constants.COMMODITY_STATUS_02_TXT;
                    break;
            }
            return statusStr;
        }
    };

    // 获取更新者名称
    private Converter<Long, String> toUserName = new AbstractConverter<Long, String>() {
        protected String convert(Long id) {
            String UserName = "";
            if (nonNull(id)) {
                Optional<User> user = userRepository.findById(id);
                if(user.isPresent())
                    UserName = user.get().getUserName();
            }
            return UserName;
        }
    };


    // 更新者时间转换
    private Converter<Long, String> toUpdateTime = new AbstractConverter<Long, String>() {
        protected String convert(Long time) {
            String times = null;
            if (time != null) {
                times = DateHelper.LongToStringFormat(time,"yyyy-MM-dd HH:mm:ss");
            }
            return times;
        }
    };

    // 时间转换
    private Converter<Timestamp, String> toTime = new AbstractConverter<Timestamp, String>() {
        protected String convert(Timestamp time) {
            String times = null;
            if (time != null) {
                times = DateHelper.timeStampFormater(time,"yyyy-MM-dd HH:mm:ss");
            }
            return times;
        }
    };
}
