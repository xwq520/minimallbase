package com.minimall.boilerplate.business.controller;

import com.minimall.boilerplate.business.dto.CommodityDTO;
import com.minimall.boilerplate.business.service.CommodityService;
import com.minimall.boilerplate.common.CheckUtils;
import com.minimall.boilerplate.common.Constants;
import com.minimall.boilerplate.common.Message;
import com.minimall.boilerplate.common.MessageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/commodity")
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    /**
     * 新增 商品
     * @param commodityDTO
     * @return
     */
    @RequestMapping(method = POST,value = "/add",produces = Constants.JSON_UTF8)
    public ResponseEntity<MessageObject> commodityAdd(@RequestBody CommodityDTO commodityDTO){
        MessageObject mo = MessageObject.of(Message.I102);
        if(CheckUtils.isEmpty(commodityDTO.getHeadline())
                || CheckUtils.isEmpty(commodityDTO.getSubtitle())
                || CheckUtils.isEmpty(commodityDTO.getOriginalPrice()) ){
            mo = MessageObject.of(Message.E111);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        boolean commodity = commodityService.commodityAdd(commodityDTO);
        if(!commodity){
            mo = MessageObject.of(Message.E124);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }


}
