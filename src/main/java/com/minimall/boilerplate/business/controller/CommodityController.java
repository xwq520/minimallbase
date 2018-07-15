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

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.minimall.boilerplate.common.BusinessHelper.PageableConverter.toPageable;
import static com.minimall.boilerplate.common.Constants.JSON_UTF8;
import static com.minimall.boilerplate.common.Message.I102;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
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
    @RequestMapping(method = POST,value = "/add",produces = JSON_UTF8)
    public ResponseEntity<MessageObject> commodityAdd(@RequestBody CommodityDTO commodityDTO,
                                                      @RequestHeader Map<String, String> header){
        MessageObject mo = MessageObject.of(I102);
        if(CheckUtils.isEmpty(commodityDTO.getHeadline())
                || CheckUtils.isEmpty(commodityDTO.getSubtitle())||
                CheckUtils.isEmpty(header.get(Constants.HTTP_USER_ID))){
            mo = MessageObject.of(Message.E111);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        commodityDTO.setUserId(header.get(Constants.HTTP_USER_ID));
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
    @RequestMapping(method = POST,value = "/list",produces = JSON_UTF8)
    public ResponseEntity<MessageObject> commodityList(@RequestBody CommodityDTO commodityDTO,
                                                  @RequestHeader Map<String, String> header) {
        MessageObject mo = MessageObject.of(I102);
        // 头部 userId 用户i
        if(CheckUtils.isEmpty(header.get(Constants.HTTP_USER_ID))){
            mo = MessageObject.of(Message.E111);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        String userId = header.get(Constants.HTTP_USER_ID);
        commodityDTO.setUserId(userId);
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
    @RequestMapping(method = POST,value = "/update",produces = JSON_UTF8)
    public ResponseEntity<MessageObject> commodityUpdate(@RequestBody CommodityDTO commodityDTO ,
                                                         @RequestHeader Map<String, String> header){
        MessageObject mo = MessageObject.of(I102);
        if(CheckUtils.isEmpty(commodityDTO.getId())|| CheckUtils.isEmpty(header.get(Constants.HTTP_USER_ID))){
            mo = MessageObject.of(Message.E111);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        String userId = header.get(Constants.HTTP_USER_ID);
        commodityDTO.setUserId(userId);
        boolean commodity = commodityService.commodityUpdate(commodityDTO);
        if(!commodity){
            mo = MessageObject.of(Message.E124);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }


    /**
     * 删除
     * @param commodityDTO
     * @return
     */
    @RequestMapping(method = POST,value = "/delete",produces = JSON_UTF8)
    public ResponseEntity<MessageObject> deletePlayManage(@RequestBody CommodityDTO commodityDTO,
                                                          @RequestHeader Map<String, String> header){
        MessageObject mo = MessageObject.of(I102);
        if(CheckUtils.isEmpty(commodityDTO.getDelIds())||
                CheckUtils.isEmpty(header.get(Constants.HTTP_USER_ID))) {
            mo = MessageObject.of(Message.E111);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        commodityDTO.setUserId(header.get(Constants.HTTP_USER_ID));
        commodityService.delete(commodityDTO);
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }

    /**
     * 获取单条数据
     * @param commodityId
     * @return
     *//*
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
*/

    /**
     * wechat
     * 商品homepage 列表
     * @param commodityDTO
     * @return
     */
    @RequestMapping(method = POST,value = "/wechatPagelist",produces = JSON_UTF8)
    public ResponseEntity<MessageObject> wechatPagelist(@RequestBody CommodityDTO commodityDTO,
                                                       @RequestHeader Map<String, String> header) {
        MessageObject mo = MessageObject.of(I102);
        // 头部 HTTP_CODE_KEY 用户i
        if(CheckUtils.isEmpty(header.get(Constants.HTTP_CODE_KEY))){
            mo = MessageObject.of(Message.E111);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        String codeKey = header.get(Constants.HTTP_CODE_KEY);
        commodityDTO.setCodeKey(codeKey);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = toPageable(header, sort);
        List<CommodityDTO> commodityDTOs = commodityService.commodityList(commodityDTO,pageable,mo);
        if(nonNull(commodityDTOs) && commodityDTOs.size() > 0){
            mo.put("commodityHomeList",commodityDTOs);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        mo = MessageObject.of(Message.E124);
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }

    @RequestMapping(method = POST, value = "/mediaFiles",consumes = MULTIPART_FORM_DATA_VALUE, produces = JSON_UTF8)
    public ResponseEntity<MessageObject> mediaFilesUpload(HttpServletRequest request){
        MessageObject mo = MessageObject.of(I102);
        Map map = commodityService.uploadMediaFiles(request);
        if(map.isEmpty()){
            return new ResponseEntity<>(NO_CONTENT);
        }
        mo.put("mediaFiles", map);
        return new ResponseEntity<>(mo, OK);
    }

}
