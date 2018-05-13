package com.minimall.boilerplate.business.controller;

import com.minimall.boilerplate.business.dto.OrderDTO;
import com.minimall.boilerplate.business.service.OrderSerivce;
import com.minimall.boilerplate.common.CheckUtils;
import com.minimall.boilerplate.common.Constants;
import com.minimall.boilerplate.common.Message;
import com.minimall.boilerplate.common.MessageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.minimall.boilerplate.common.BusinessHelper.PageableConverter.toPageable;
import static java.util.Objects.nonNull;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderSerivce orderSerivce;

    /**
     * 新增订单
     * @param orderDTO
     * @return
     */
    @RequestMapping(method = POST,value = "/add",produces = Constants.JSON_UTF8)
    public ResponseEntity<MessageObject> orderAdd(@RequestBody OrderDTO orderDTO) {
        MessageObject mo = MessageObject.of(Message.I102);
        if(CheckUtils.isEmpty(orderDTO.getCommodityId())
                || CheckUtils.isEmpty(orderDTO.getUserId()) ){
            mo = MessageObject.of(Message.E111);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        boolean order = orderSerivce.orderAdd(orderDTO);
        if(!order){
            mo = MessageObject.of(Message.E124);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }


    /**
     * 编辑
     * @param orderDTO
     * @return
     */
    @RequestMapping(method = POST,value = "/update",produces = Constants.JSON_UTF8)
    public ResponseEntity<MessageObject> orderUpdate(@RequestBody OrderDTO orderDTO) {
        MessageObject mo = MessageObject.of(Message.I102);
        if(CheckUtils.isEmpty(orderDTO.getId())){
            mo = MessageObject.of(Message.E111);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        boolean order = orderSerivce.orderUpdate(orderDTO);
        if(!order){
            mo = MessageObject.of(Message.E124);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }


    /**
     * 列表
     * @param orderDTO
     * @return
     */
    @RequestMapping(method = POST,value = "/list",produces = Constants.JSON_UTF8)
    public ResponseEntity<MessageObject> orderList(@RequestBody OrderDTO orderDTO,
                                                   @RequestHeader Map<String, String> header) {
        MessageObject mo = MessageObject.of(Message.I102);

        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = toPageable(header, sort);
        List<OrderDTO> orderDTOs = orderSerivce.orderList(orderDTO,pageable,mo);
        if(nonNull(orderDTOs) && orderDTOs.size() > 0){
            mo.put("orderList",orderDTOs);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        mo = MessageObject.of(Message.E124);
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }


}
