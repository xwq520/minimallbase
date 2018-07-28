package com.minimall.boilerplate.business.service;

import com.minimall.boilerplate.business.dto.OrderDTO;
import com.minimall.boilerplate.business.dto.OrderHomeDTO;
import com.minimall.boilerplate.business.dto.assembler.OrderAssembler;
import com.minimall.boilerplate.business.entity.Commodity;
import com.minimall.boilerplate.business.entity.Order;
import com.minimall.boilerplate.business.entity.User;
import com.minimall.boilerplate.business.repository.*;
import com.minimall.boilerplate.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

@Service
public class OrderSerivce {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderAssembler orderAssembler;

    // 新增
    @Transactional
    public boolean orderAdd(OrderDTO orderDTO,String codeKey){

        Optional<User> userOptional = userRepository.findByCodeKey(codeKey);
        if(!userOptional.isPresent()){
            return false;
        }

        Order order = new Order();
        // 商品
        Optional<Commodity> commodity = commodityRepository.findByIdAndComNo(orderDTO.getCommodityId(),orderDTO.getCommodityNo());
        if(!commodity.isPresent()){
            return false;
        }
        order.setCommodity(commodity.get());
        order.setOrderNo("O"+DateHelper.LongToStringFormat(System.currentTimeMillis(),DateHelper.normalFormtNo)+ ""+ (int)((Math.random()*9+1)*1000));
        order.setUser(commodity.get().getUser());
        order.setCommodityNo(commodity.get().getComNo());
        order.setCommodityName(commodity.get().getHeadline());
        order.setPurchaseQuantity(orderDTO.getPurchaseQuantity());
        order.setUnitPrice(commodity.get().getSellingPrice());// 销售单价
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
                if(!order.get().getOrderNo().equals(orderDTO.getOrderNo()) ||
                        !order.get().getCommodity().getComNo().equals(orderDTO.getCommodityNo()) ||
                        !order.get().getUser().getUserId().equals(orderDTO.getUserId())){
                    return  false;
                }
                if(!CheckUtils.isEmpty(orderDTO.getOrderStatus())){
                    order.get().setOrderStatus(orderDTO.getOrderStatus());
                }
              /*  if(!CheckUtils.isEmpty(orderDTO.getPurchaseQuantity())){
                    order.get().setPurchaseQuantity(orderDTO.getPurchaseQuantity());
                }
                if(!CheckUtils.isEmpty(orderDTO.getOrderMoney())){
                    order.get().setOrderMoney(Double.valueOf(orderDTO.getOrderMoney()));
                }*/

                // 支付时间
               /* if(!CheckUtils.isEmpty(orderDTO.getOrderStatus())
                        && orderDTO.getOrderStatus() == Constants.ORDER_STATUS_02){
                    order.get().setPlayTime(new Timestamp(System.currentTimeMillis()));
                }*/

                order.get().setRemarks(orderDTO.getRemarks());
                // 发货时间
                if(!CheckUtils.isEmpty(orderDTO.getOrderStatus())
                        && orderDTO.getOrderStatus() == Constants.ORDER_STATUS_03){
                    order.get().setShipmentsTime(new Timestamp(System.currentTimeMillis()));
                }
                // 取消订单时间
                else if(!CheckUtils.isEmpty(orderDTO.getOrderStatus())
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


    // 销售统计列表
    @Transactional
    public List<OrderHomeDTO> orderVolumeList(String userId){

        List<Object[]>  orderObjs = orderRepository.findByOrderVolumeList(userId);
        List<OrderHomeDTO> orders = new ArrayList<>();
        if (!CheckUtils.isEmpty(orderObjs))
            orderObjs.stream().forEach(orderobj -> {
                OrderHomeDTO dto = new OrderHomeDTO();
                //商品No
                if (!CheckUtils.isEmpty(orderobj[0])) {
                    dto.setCommodityNo(orderobj[0].toString());
                }
                //商品Name
                if (!CheckUtils.isEmpty(orderobj[1])) {
                    dto.setCommodityName(orderobj[1].toString());
                }
                //销售
                if (!CheckUtils.isEmpty(orderobj[2])) {
                    dto.setCountNum(orderobj[2].toString());
                }

                orders.add(dto);
            });

        return  orders;
    }

    //  首页 销售总数量
    @Transactional
    public OrderHomeDTO findByCountOrderInfo(String userId){

        OrderHomeDTO orderHomeDTO = new OrderHomeDTO();
        List<Object[]>  objArr = orderRepository.findByCountOrderInfo(userId);
        if(!CheckUtils.isEmpty(objArr) && objArr.size()>0 && !CheckUtils.isEmpty(objArr.get(0)[0]) ){
            String sumCount = objArr.get(0)[0].toString();
            orderHomeDTO.setSumCount(sumCount);
            String sumMoney = objArr.get(0)[1].toString();
            orderHomeDTO.setSumMoney(Utils.formatMoney(Double.valueOf(sumMoney),Utils.moneyFormat));
            String waritCount = objArr.get(0)[2].toString();
            orderHomeDTO.setWaritCount(waritCount);
        }

        return orderHomeDTO;
    }


}
