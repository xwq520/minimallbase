package com.minimall.boilerplate.business.controller;

import com.minimall.boilerplate.business.dto.PasswordDTO;
import com.minimall.boilerplate.business.dto.UserDTO;
import com.minimall.boilerplate.business.service.UserService;
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

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 验证用户Id和用户密码是否存在
    @RequestMapping(method = POST,value = "/checkUser",produces = Constants.JSON_UTF8)
    public ResponseEntity<MessageObject> verification(@RequestBody UserDTO userDTO){
        MessageObject mo = MessageObject.of(Message.I101);
        if(CheckUtils.isEmpty(userDTO.getUserId()) ||
                CheckUtils.isEmpty(userDTO.getPassword())){
            mo = MessageObject.of(Message.E111);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        boolean user = userService.verification(userDTO);
        if(!user){
            mo = MessageObject.of(Message.E110);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }

    // 修改当前用户密码
    @RequestMapping(method = POST,value = "/changePassowrd",produces = Constants.JSON_UTF8)
    public ResponseEntity<MessageObject> changePassowrd(@RequestBody PasswordDTO passwordDTO,
                                                        @RequestHeader Map<String, String> header){
        MessageObject mo = MessageObject.of(Message.I102);
        if(CheckUtils.isEmpty(header.get(Constants.HTTP_USER_ID))
                || CheckUtils.isEmpty(passwordDTO.getPassword())
                || CheckUtils.isEmpty(passwordDTO.getNewPassword()) ){
            mo = MessageObject.of(Message.E111);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        passwordDTO.setUserId(header.get(Constants.HTTP_USER_ID));
        boolean password = userService.changePassowrd(passwordDTO);
        if(!password){
            mo = MessageObject.of(Message.E124);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }


    /**
     * 新增用户
     * @param userDTO
     * @return
     */
    @RequestMapping(method = POST,value = "/add",produces = Constants.JSON_UTF8)
    public ResponseEntity<MessageObject> userAdd(@RequestBody UserDTO userDTO){
        MessageObject mo = MessageObject.of(Message.I102);
        if(CheckUtils.isEmpty(userDTO.getUserName())
            || CheckUtils.isEmpty(userDTO.getUserPhone())
            || CheckUtils.isEmpty(userDTO.getUserId())){
            mo = MessageObject.of(Message.E111);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        boolean user = userService.userAdd(userDTO);
        if(!user){
            mo = MessageObject.of(Message.E110);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }

    /**
     * 编辑
     * @param userDTO
     * @return
     */
    @RequestMapping(method = POST,value = "/update",produces = Constants.JSON_UTF8)
    public ResponseEntity<MessageObject> userUpdate(@RequestBody UserDTO userDTO){
        MessageObject mo = MessageObject.of(Message.I102);
        if(CheckUtils.isEmpty(userDTO.getId()) ){
            mo = MessageObject.of(Message.E111);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        boolean user = userService.userUpdate(userDTO);
        if(!user){
            mo = MessageObject.of(Message.E110);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }

    /**
     * 列表
     * @param userDTO
     * @return
     */
    @RequestMapping(method = POST,value = "/list",produces = Constants.JSON_UTF8)
    public ResponseEntity<MessageObject> userList(@RequestBody UserDTO userDTO,
                                                    @RequestHeader Map<String, String> header) {
        MessageObject mo = MessageObject.of(Message.I102);
        // 头部 userId 用户i
//        if(CheckUtils.isEmpty(header.get(Constants.HTTP_USER_ID))){
//            mo = MessageObject.of(Message.E111);
//            return new ResponseEntity<>(mo, HttpStatus.OK);
//        }
       // String userId = header.get(Constants.HTTP_USER_ID);
      //  userDTO.setUserId(userId);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = toPageable(header, sort);
        List<UserDTO> userDTOS = userService.userList(userDTO,pageable,mo);
        if(nonNull(userDTOS) && userDTOS.size() > 0){
            mo.put("userList",userDTOS);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        mo = MessageObject.of(Message.E124);
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }


    /**
     * 删除
     * @param userDTO
     * @return
     */
    @RequestMapping(method = POST,value = "/delete",produces = Constants.JSON_UTF8)
    public ResponseEntity<MessageObject> deletePlayManage(@RequestBody UserDTO userDTO){
        MessageObject mo = MessageObject.of(Message.I102);
        if(CheckUtils.isEmpty(userDTO.getUserIds())) {
            mo = MessageObject.of(Message.E111);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        userService.deleteUser(userDTO);
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }

    /**
     * 单条数据
     * @param id
     * @return
     */
   /* @RequestMapping(method = GET,value = "/userInfo/{id}",produces = Constants.JSON_UTF8)
    public ResponseEntity<MessageObject> userInfo (@PathVariable("id") Long id){
        MessageObject mo = MessageObject.of(Message.I102);
        if(CheckUtils.isEmpty(id)){
            mo = MessageObject.of(Message.E111);
            return new ResponseEntity<>(mo, HttpStatus.NO_CONTENT);
        }
        UserDTO userInfo = userService.userInfo(id);
        if(CheckUtils.isEmpty(userInfo)){
            mo = MessageObject.of(Message.E124);
            return new ResponseEntity<>(mo, HttpStatus.OK);
        }
        mo.put("userInfo",userInfo);
        return new ResponseEntity<>(mo, HttpStatus.OK);
    }*/

}
