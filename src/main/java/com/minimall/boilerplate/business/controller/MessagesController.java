package com.minimall.boilerplate.business.controller;

import com.minimall.boilerplate.business.dto.MessagesDTO;
import com.minimall.boilerplate.business.service.MessagesSerivce;
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
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    @Autowired
    private MessagesSerivce messagesSerivce;

    /**
     * 消息列表
     * @param messagesDTO
     * @param header
     * @return
     */
    @RequestMapping(method = POST,value = "/list",produces = Constants.JSON_UTF8)
    public ResponseEntity<MessageObject> messagesList(@RequestBody MessagesDTO messagesDTO,
                                                       @RequestHeader Map<String, String> header){
        MessageObject mo = MessageObject.of(Message.I102);
        // 头部 userId 用户i
        if(CheckUtils.isEmpty(header.get(Constants.HTTP_USER_ID))){
            mo = MessageObject.of(Message.E140);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        String userId = header.get(Constants.HTTP_USER_ID);
        messagesDTO.setUserId(Long.valueOf(userId));
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = toPageable(header, sort);
        List<MessagesDTO> messagesDTOS = messagesSerivce.messagesList(messagesDTO,pageable,mo);
        if(nonNull(messagesDTOS) && messagesDTOS.size() > 0){
            mo.put("messagesList",messagesDTOS);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        mo = MessageObject.of(Message.E124);
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }

    /**
     * 更新可读状态和已读时间
     * @param messageId
     * @return
     */
    @RequestMapping(method = GET, value = "/updateRead/{messageId}", produces = Constants.JSON_UTF8)
    public ResponseEntity<MessageObject> updateRead(@PathVariable("messageId") Long  messageId) {
        MessageObject mo = MessageObject.of(Message.I102);
        messagesSerivce.updateRead(messageId);
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }

    /**
     * 查看未读消息个数
     * @param header
     * @return
     */
    @RequestMapping(method = GET, value = "/getCount", produces = Constants.JSON_UTF8)
    public ResponseEntity<MessageObject> queryMessagesNotRead(@RequestHeader Map<String, String> header) {
        MessageObject mo = MessageObject.of(Message.I102);
        // 头部 userId 用户i
        if(CheckUtils.isEmpty(header.get(Constants.HTTP_USER_ID))){
            mo = MessageObject.of(Message.E140);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        Long userId = Long.valueOf(header.get(Constants.HTTP_USER_ID));
        Integer count = messagesSerivce.getCountNotRead(userId);
        mo.put("count", count);
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }


    /**
     * 查看已读消息个数
     *
     * @return

    @RequestMapping(method = GET, value = "/currentUser", produces = Constants.JSON_UTF8)
    public ResponseEntity<MessageObject> queryMessages(@RequestHeader Map<String, String> header) {
        MessageObject mo = MessageObject.of(Message.I102);
        // 头部 userId 用户i
        if(CheckUtils.isEmpty(header.get(Constants.HTTP_USER_ID))){
            mo = MessageObject.of(Message.E140);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        Long userId = Long.valueOf(header.get(Constants.HTTP_USER_ID));
        Integer count = messagesSerivce.getCount(userId);
        mo.put("count", count);
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }*/

}
