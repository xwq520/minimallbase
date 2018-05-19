package com.minimall.boilerplate.business.controller;

import com.minimall.boilerplate.business.dto.CommodityDTO;
import com.minimall.boilerplate.business.service.CommodityService;
import com.minimall.boilerplate.common.CheckUtils;
import com.minimall.boilerplate.common.Constants;
import com.minimall.boilerplate.common.Message;
import com.minimall.boilerplate.common.MessageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.minimall.boilerplate.common.BusinessHelper.PageableConverter.toPageable;
import static java.util.Objects.nonNull;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
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


    /**
     * 商品 列表
     * @param commodityDTO
     * @return
     */
    @RequestMapping(method = POST,value = "/list",produces = Constants.JSON_UTF8)
    public ResponseEntity<MessageObject> commodityList(@RequestBody CommodityDTO commodityDTO,
                                                  @RequestHeader Map<String, String> header) {
        MessageObject mo = MessageObject.of(Message.I102);
        // 头部 userId 用户i
        if(CheckUtils.isEmpty(header.get(Constants.HTTP_USER_ID))){
            mo = MessageObject.of(Message.E111);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        String userId = header.get(Constants.HTTP_USER_ID);
        commodityDTO.setUserId(Long.valueOf(userId));
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = toPageable(header, sort);
        List<CommodityDTO> commodityDTOs = commodityService.commodityList(commodityDTO,pageable,mo);
        if(nonNull(commodityDTOs) && commodityDTOs.size() > 0){
            mo.put("commodityList",commodityDTOs);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        mo = MessageObject.of(Message.E124);
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }

    /**
     * 编辑 商品
     * @param commodityDTO
     * @return
     */
    @RequestMapping(method = POST,value = "/update",produces = Constants.JSON_UTF8)
    public ResponseEntity<MessageObject> commodityUpdate(@RequestBody CommodityDTO commodityDTO){
        MessageObject mo = MessageObject.of(Message.I102);
        if(CheckUtils.isEmpty(commodityDTO.getId())){
            mo = MessageObject.of(Message.E111);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        boolean commodity = commodityService.commodityUpdate(commodityDTO);
        if(!commodity){
            mo = MessageObject.of(Message.E124);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(method = GET,value = "/delete/{id}",produces = Constants.JSON_UTF8)
    public ResponseEntity<MessageObject> deletePlayManage(@PathVariable("id") Long id){
        MessageObject mo = MessageObject.of(Message.I102);
        if(CheckUtils.isEmpty(id)) {
            mo = MessageObject.of(Message.E111);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        commodityService.delete(id);
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }

    /**
     * 获取单条数据
     * @param commodityId
     * @return
     */
    @RequestMapping(method = GET,value = "/commodityInfo/{commodityId}",produces = Constants.JSON_UTF8)
    public ResponseEntity<MessageObject> commodityInfo (@PathVariable("commodityId") Long commodityId){
        MessageObject mo = MessageObject.of(Message.I102);
        if(CheckUtils.isEmpty(commodityId)){
            mo = MessageObject.of(Message.E111);
            return new ResponseEntity<>(mo, HttpStatus.NO_CONTENT);
        }
        CommodityDTO commodityInfo = commodityService.commodityInfo(commodityId);
        if(CheckUtils.isEmpty(commodityInfo)){
            mo = MessageObject.of(Message.E124);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        mo.put("commodityInfo",commodityInfo);
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }

}
