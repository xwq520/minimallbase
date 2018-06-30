package com.minimall.boilerplate.business.service;

import com.minimall.boilerplate.business.dto.PasswordDTO;
import com.minimall.boilerplate.business.dto.UserDTO;
import com.minimall.boilerplate.business.dto.assembler.UserAssembler;
import com.minimall.boilerplate.business.entity.User;
import com.minimall.boilerplate.business.repository.UserRepository;
import com.minimall.boilerplate.business.repository.UserSpecification;
import com.minimall.boilerplate.common.CheckUtils;
import com.minimall.boilerplate.common.Message;
import com.minimall.boilerplate.common.MessageObject;
import com.minimall.boilerplate.exception.NotFoundRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.Objects.nonNull;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAssembler userAssembler;

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
        user.setPassword(userDTO.getPassword());
        user.setRegisterTime(new Timestamp(System.currentTimeMillis()));
       // user.setLastTime(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
        return true;
    }

    // 编辑
    @Transactional
    public boolean userUpdate(UserDTO userDTO){
        if(!CheckUtils.isEmpty(userDTO.getId())){
            Optional<User> user = userRepository.findById(userDTO.getId());
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
                user.get().setPassword(userDTO.getPassword());
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
    public UserDTO userInfo(Long id){
        Optional<User> commodity = userRepository.findById(id);
        if(commodity.isPresent()){
            Optional<UserDTO> userDTOOptional = userAssembler.toDTO(commodity.get());
            if(userDTOOptional.isPresent()){
                return userDTOOptional.get();
            }
        }
        return null;
    }

    // 验证用户Id和用户密码是否存在
    @Transactional
    public boolean verification(UserDTO userDTO){
        Optional<User> user = userRepository.findByUserIdAndPassword(userDTO.getUserId(),userDTO.getPassword());
        if(user.isPresent()){
            return true;
        }
        return false;
    }

    // 修改当前用户密码
    @Transactional
    public boolean changePassowrd(PasswordDTO passwordDTO) {
        Optional<User> user = userRepository.findByUserIdAndPassword(passwordDTO.getUserId(),passwordDTO.getPassword());
        if(user.isPresent()){
            // 验证原密码
            if(passwordDTO.getPassword().equals(user.get().getPassword())){
                // 保存修改后密码
                user.get().setPassword(passwordDTO.getNewPassword());
                userRepository.save(user.get());
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


}
