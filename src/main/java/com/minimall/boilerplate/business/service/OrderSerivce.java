package com.minimall.boilerplate.business.service;

import com.minimall.boilerplate.business.dto.OrderDTO;
import com.minimall.boilerplate.business.dto.assembler.OrderAssembler;
import com.minimall.boilerplate.business.entity.Commodity;
import com.minimall.boilerplate.business.entity.Order;
import com.minimall.boilerplate.business.repository.*;
import com.minimall.boilerplate.common.CheckUtils;
import com.minimall.boilerplate.common.Constants;
import com.minimall.boilerplate.common.DateHelper;
import com.minimall.boilerplate.common.MessageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

@Service
public class OrderSerivce {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CommodityRepository commodityRepository;
   // @Autowired
   // private UserRepository userRepository;
    @Autowired
    private OrderAssembler orderAssembler;

    // 新增
    @Transactional
    public boolean orderAdd(OrderDTO orderDTO){
        Order order = new Order();
        // 商品
        Optional<Commodity> commodity = commodityRepository.findById(orderDTO.getCommodityId());
        if(!commodity.isPresent()){
            return false;
        }
        order.setCommodity(commodity.get());
        order.setOrderNo("O"+DateHelper.LongToStringFormat(System.currentTimeMillis(),DateHelper.normalFormtNo)+ ""+ (int)((Math.random()*9+1)*1000));
        order.setUser(commodity.get().getUser());
        order.setCommodityNo(commodity.get().getComNo());
        order.setCommodityName(commodity.get().getHeadline());
        order.setPurchaseQuantity(orderDTO.getPurchaseQuantity());
        order.setOrderMoney(nonNull(Double.valueOf(orderDTO.getOrderMoney()))?Double.valueOf(orderDTO.getOrderMoney()):0);
        order.setAddress(orderDTO.getAddress());
        order.setOrderStatus(Constants.ORDER_STATUS_01); // 默认为待支付
        order.setPlayTime(new Timestamp(System.currentTimeMillis())); // 支付时间
        order.setOrderTime(new Timestamp(System.currentTimeMillis())); // 下单时间
        orderRepository.save(order);
        return true;
    }


    // 编辑
    @Transactional
    public boolean orderUpdate(OrderDTO orderDTO){
        if(!CheckUtils.isEmpty(orderDTO.getId())){
            Optional<Order> order = orderRepository.findById(orderDTO.getId());
            if(order.isPresent()){
                if(!CheckUtils.isEmpty(orderDTO.getOrderStatus())){
                    order.get().setOrderStatus(orderDTO.getOrderStatus());
                }
                if(!CheckUtils.isEmpty(orderDTO.getPurchaseQuantity())){
                    order.get().setPurchaseQuantity(orderDTO.getPurchaseQuantity());
                }
                if(!CheckUtils.isEmpty(orderDTO.getOrderMoney())){
                    order.get().setOrderMoney(Double.valueOf(orderDTO.getOrderMoney()));
                }
                if(!CheckUtils.isEmpty(orderDTO.getAddress())){
                    order.get().setAddress(orderDTO.getAddress());
                }
                // 支付时间
                if(!CheckUtils.isEmpty(orderDTO.getOrderStatus())
                        && orderDTO.getOrderStatus() == Constants.ORDER_STATUS_02){
                    order.get().setPlayTime(new Timestamp(System.currentTimeMillis()));
                }
                // 发货时间
                if(!CheckUtils.isEmpty(orderDTO.getOrderStatus())
                        && orderDTO.getOrderStatus() == Constants.ORDER_STATUS_03){
                    order.get().setShipmentsTime(new Timestamp(System.currentTimeMillis()));
                }
                // 取消订单时间
                if(!CheckUtils.isEmpty(orderDTO.getOrderStatus())
                        && orderDTO.getOrderStatus() == Constants.ORDER_STATUS_04){
                    order.get().setCancelTime(new Timestamp(System.currentTimeMillis()));
                }
                orderRepository.save(order.get());
            }
        }
        return true;
    }


    // 列表
    @Transactional
    public List<OrderDTO> orderList(OrderDTO orderDTO, Pageable pageable, MessageObject mo){
        Page<Order> orders = orderRepository.findAll(OrderSpecification.conditionQuerySpec(orderDTO), pageable);
        if(nonNull(orders)){
            mo.put("total", orders.getTotalElements());
        }
        return orders.getContent().stream()
                .map(orderAssembler::toDTO)
                .flatMap(dto -> dto.isPresent() ? Stream.of(dto.get()) : Stream.empty())
                .collect(Collectors.toList());
    }


}
