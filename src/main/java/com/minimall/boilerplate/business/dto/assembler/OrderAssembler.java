package com.minimall.boilerplate.business.dto.assembler;

import com.minimall.boilerplate.business.dto.OrderDTO;
import com.minimall.boilerplate.business.entity.Order;
import com.minimall.boilerplate.business.entity.User;
import com.minimall.boilerplate.business.repository.OrderRepository;
import com.minimall.boilerplate.business.repository.UserRepository;
import com.minimall.boilerplate.common.Constants;
import com.minimall.boilerplate.common.DateHelper;
import com.minimall.boilerplate.common.Utils;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.rmi.CORBA.Util;
import java.sql.Timestamp;
import java.util.Optional;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class OrderAssembler implements IDTOAssembler<OrderDTO,Order> {
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    public OrderAssembler() {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.addMappings(new OrderAssembler.OrderDTOMap());
        modelMapper.addMappings(new OrderAssembler.OrderMap());
    }

    @Override
    public Optional<OrderDTO> toDTO(Order order) {
        nonNull(order);
        return Optional.ofNullable(modelMapper.map(order, OrderDTO.class));
    }

    @Override
    public Optional<Order> toEntity(OrderDTO orderDTO) {
        nonNull(orderDTO);
        Order order;
        Long id = orderDTO.getId();
        if (isNull(id))
            order = Optional.ofNullable(orderRepository.getOne(id)).orElse(new Order());
        else
            order = new Order();
        modelMapper.map(orderDTO, order);
        return Optional.of(order);
    }

    private class OrderMap extends PropertyMap<OrderDTO, Order> {
        @Override
        protected void configure() {

        }
    }

    private class OrderDTOMap extends PropertyMap<Order, OrderDTO> {
        @Override
        protected void configure() {
            using(toStatusName).map(source.getOrderStatus(),destination.getOrderStatusName());
            using(toTime).map(source.getOrderTime(),destination.getOrderTime());
            using(toTime).map(source.getPlayTime(),destination.getPlayTime());
            using(toTime).map(source.getCancelTime(),destination.getCancelTime());
            using(toTime).map(source.getShipmentsTime(),destination.getShipmentsTime());
            using(moneyFormart).map(source.getOrderMoney(),destination.getOrderMoney());
            // map(source.getCommodity().getHeadline(),destination.getCommodityName());
            // map(source.getCommodity().getComNo(),destination.getCommodityNo());
           // using(toUpdateTime).map(source.getUpdateTime(),destination.getUpdateTime());
            // using(toUserName).map(source.getUpdaterId(),destination.getUpdaterName());
        }
    }

    private Converter<Double, String> moneyFormart = new AbstractConverter<Double, String>() {
        protected String convert(Double money) {
            if (nonNull(money)) {
               return Utils.formatMoney(money,Utils.moneyFormat);
            }
            return "";
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
                times = DateHelper.LongToStringFormat(time,DateHelper.normalFormt);
            }
            return times;
        }
    };

    // 时间转换
    private Converter<Timestamp, String> toTime = new AbstractConverter<Timestamp, String>() {
        protected String convert(Timestamp time) {
            String times = null;
            if (time != null) {
                times = DateHelper.timeStampFormater(time,DateHelper.normalFormt);
            }
            return times;
        }
    };


    // 状态值
    private Converter<Integer, String> toStatusName = new AbstractConverter<Integer, String>() {
        protected String convert(Integer status) {
            if (isNull(status))
                return "";
            String statusStr = "";
            switch (status) {
                case Constants.ORDER_STATUS_01:
                    statusStr = Constants.ORDER_STATUS_01_TXT;
                    break;
                case Constants.ORDER_STATUS_02:
                    statusStr = Constants.ORDER_STATUS_02_TXT;
                    break;
                case Constants.ORDER_STATUS_03:
                    statusStr = Constants.ORDER_STATUS_03_TXT;
                    break;
                case Constants.ORDER_STATUS_04:
                    statusStr = Constants.ORDER_STATUS_04_TXT;
                    break;
            }
            return statusStr;
        }
    };
}
