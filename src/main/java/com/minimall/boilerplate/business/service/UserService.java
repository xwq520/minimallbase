package com.minimall.boilerplate.business.service;

import com.minimall.boilerplate.business.dto.PasswordDTO;
import com.minimall.boilerplate.business.dto.UserDTO;
import com.minimall.boilerplate.business.dto.assembler.UserAssembler;
import com.minimall.boilerplate.business.entity.User;
import com.minimall.boilerplate.business.repository.UserRepository;
import com.minimall.boilerplate.business.repository.UserSpecification;
import com.minimall.boilerplate.common.CheckUtils;
import com.minimall.boilerplate.common.CryptoHelper;
import com.minimall.boilerplate.common.Message;
import com.minimall.boilerplate.common.MessageObject;
import com.minimall.boilerplate.exception.NotFoundRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.Objects.nonNull;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAssembler userAssembler;
    @Autowired
    private FileService fileService;

    // 新增
    @Transactional
    public boolean userAdd(UserDTO userDTO){
        // 是否存在
        Optional<User> userInfo = userRepository.findByUserInfo(userDTO.getUserId(),userDTO.getUserPhone());
        if(userInfo.isPresent()){
            return false;
        }
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setUserPhone(userDTO.getUserPhone());
        user.setUserSex(userDTO.getUserSex());
        user.setUserId(userDTO.getUserId());
        user.setOther(userDTO.getOther());
        user.setRemarks(userDTO.getRemarks());
        String[] str = UUID.randomUUID().toString().split("-");
       // user.setCodeKey(str[0]+""+str[1]);
        user.setCodeKey(UUID.randomUUID().toString());
        if(!CheckUtils.isEmpty(userDTO.getPassword())){
            user.setPassword(CryptoHelper.encode(userDTO.getPassword()));
        }
        user.setIsLock(userDTO.getIsLock());
        user.setRegisterTime(new Timestamp(System.currentTimeMillis()));
       // user.setLastTime(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
        return true;
    }

    // 编辑
    @Transactional
    public boolean userUpdate(UserDTO userDTO){
        if(!CheckUtils.isEmpty(userDTO.getUserId())){
            Optional<User> user = userRepository.findByUserId(userDTO.getUserId());
            if(!CheckUtils.isEmpty(userDTO.getUserName())){
                user.get().setUserName(userDTO.getUserName());
            }
            if(!CheckUtils.isEmpty(userDTO.getUserPhone())){
                user.get().setUserPhone(userDTO.getUserPhone());
            }
            if(!CheckUtils.isEmpty(userDTO.getUserSex())){
                user.get().setUserSex(userDTO.getUserSex());
            }
            //if(!CheckUtils.isEmpty(userDTO.getUserId())){
            //    user.get().setUserId(userDTO.getUserId());
            //}
            if(!CheckUtils.isEmpty(userDTO.getPassword())){
                user.get().setPassword(CryptoHelper.encode(userDTO.getPassword()));
            }
            if(!CheckUtils.isEmpty(userDTO.getIsLock())){
                user.get().setIsLock(userDTO.getIsLock());
            }
            user.get().setOther(userDTO.getOther());
            user.get().setRemarks(userDTO.getRemarks());
            if(!CheckUtils.isEmpty(userDTO.getPlay1()) && userDTO.getPlay1().indexOf("base64") >=0 ){
                BASE64Decoder decoder = new BASE64Decoder();
                try {
                    // 在线的base64图片转换带有："data:image/jpeg;base64," 解码之前这个得去掉。
                    String getPlay1 = userDTO.getPlay1();
                    byte[] imgbyte =  decoder.decodeBuffer(getPlay1.substring(getPlay1.indexOf(",")+1));
                    // commodity.setPreviewImg(this.saveImgs(null,true,imgbyte));
                    user.get().setPlay1(fileService.uploadFilesOss(imgbyte));
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }
            if(!CheckUtils.isEmpty(userDTO.getPlay2()) && userDTO.getPlay2().indexOf("base64") >=0 ){
                BASE64Decoder decoder = new BASE64Decoder();
                try {
                    // 在线的base64图片转换带有："data:image/jpeg;base64," 解码之前这个得去掉。
                    String getPlay2 = userDTO.getPlay2();
                    byte[] imgbyte =  decoder.decodeBuffer(getPlay2.substring(getPlay2.indexOf(",")+1));
                    // commodity.setPreviewImg(this.saveImgs(null,true,imgbyte));
                    user.get().setPlay2(fileService.uploadFilesOss(imgbyte));
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }
            userRepository.save(user.get());
        }
        return true;
    }


    // 列表
    @Transactional
    public List<UserDTO> userList(UserDTO userDTO,Pageable pageable,MessageObject mo){
        Page<User> users = userRepository.findAll(UserSpecification.conditionQuerySpec(userDTO), pageable);
        if(nonNull(users)){
            mo.put("total", users.getTotalElements());
        }
        return users.getContent().stream()
                .map(userAssembler::toDTO)
                .flatMap(dto -> dto.isPresent() ? Stream.of(dto.get()) : Stream.empty())
                .collect(Collectors.toList());
    }

    // 删除
    @Transactional
    public void deleteUser(UserDTO userDTO) {
        List<User> userList = new ArrayList<>();
        Arrays.stream(userDTO.getUserIds()).forEach(userId -> {
            Optional<User> deleteData = Optional.ofNullable(userRepository.findByUserId(userId))
                    .orElseThrow(() -> new NotFoundRequestException(Message.E109));
            deleteData.get().deletedAtNow();
            userList.add(deleteData.get());
        });

        if(userList.size()>0){
            userRepository.saveAll(userList);
        }
    }


  // 获取单条数据
    @Transactional
    public User userInfo(String codeKey){
        Optional<User> commodity = userRepository.findByCodeKey(codeKey);
        if(commodity.isPresent()){
             return commodity.get();
        }
        return null;
    }

    // 验证用户Id和用户密码是否存在
    @Transactional
    public User verification(UserDTO userDTO){
        Optional<User> user = userRepository.findByUserId(userDTO.getUserId());
        if(user.isPresent()){
            if(CryptoHelper.verify(userDTO.getPassword(),user.get().getPassword()) && user.get().getIsLock() == 0){
                user.get().setLastTime(new Timestamp(System.currentTimeMillis()));
                userRepository.save(user.get());
                return user.get();
            }
        }
        return null;
    }
/*
    public static void main(String[] args) {
        System.out.print(CryptoHelper.encode("123456"));
    }*/
        // 修改当前用户密码
    @Transactional
    public boolean changePassowrd(PasswordDTO passwordDTO) {
       // String pwd = CryptoHelper.encode(passwordDTO.getPassword());
        // 验证原密码
        Optional<User> user = userRepository.findByUserId(passwordDTO.getUserId());
        if(user.isPresent()){
            if(!CryptoHelper.verify(passwordDTO.getPassword(),user.get().getPassword())){
                return false;
            }
            // 保存修改后密码
            user.get().setPassword(CryptoHelper.encode(passwordDTO.getNewPassword()));
            userRepository.save(user.get());
            return true;
        }
        return false;
    }
}
