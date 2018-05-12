package com.minimall.boilerplate.business.service;

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
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setUserPhone(userDTO.getUserPhone());
        user.setUserSex(userDTO.getUserSex());
        user.setUserId(userDTO.getUserId());
        user.setPassword(userDTO.getPassword());
        user.setRegisterTime(new Timestamp(System.currentTimeMillis()));
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
            if(!CheckUtils.isEmpty(userDTO.getUserId())){
                user.get().setUserId(userDTO.getUserId());
            }
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
    public void deleteUser(Long id) {
        Optional<User> deleteData = Optional.ofNullable(userRepository.findById(id))
                .orElseThrow(() -> new NotFoundRequestException(Message.E109));
        deleteData.get().deletedAtNow();
        userRepository.save(deleteData.get());
    }

}
