package com.minimall.boilerplate.business.dto.assembler;

import com.minimall.boilerplate.business.dto.UserDTO;
import com.minimall.boilerplate.business.entity.User;
import com.minimall.boilerplate.business.repository.UserRepository;
import com.minimall.boilerplate.common.Constants;
import com.minimall.boilerplate.common.DateHelper;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Optional;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class UserAssembler implements IDTOAssembler<UserDTO,User>{
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private UserRepository userRepository;

    public UserAssembler() {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.addMappings(new UserDTOMap());
        modelMapper.addMappings(new UserMap());
    }

    @Override
    public Optional<UserDTO> toDTO(User user) {
        nonNull(user);
        return Optional.ofNullable(modelMapper.map(user, UserDTO.class));
    }

    @Override
    public Optional<User> toEntity(UserDTO userDTO) {
        nonNull(userDTO);
        User user;
        Long id = userDTO.getId();
        if (isNull(id))
            user = Optional.ofNullable(userRepository.getOne(id)).orElse(new User());
        else
            user = new User();
        modelMapper.map(userDTO, user);
        return Optional.of(user);
    }

    private class UserMap extends PropertyMap<UserDTO, User> {
        @Override
        protected void configure() {

        }
    }

    private class UserDTOMap extends PropertyMap<User, UserDTO> {
        @Override
        protected void configure() {
            using(toSexName).map(source.getUserSex(),destination.getUserSexName());
            using(toTime).map(source.getRegisterTime(),destination.getRegisterTime());
            using(toTime).map(source.getLastTime(),destination.getLastTime());
            map("",destination.getPassword());
            map(source.getId(),destination.getId());
            map(source.getIsLock(),destination.getIsLock());
            using(isLock).map(source.getIsLock(),destination.getLockTxt());
           // using(toUpdateTime).map(source.getUpdateTime(),destination.getUpdateTime());
           // using(toUserName).map(source.getUpdaterId(),destination.getUpdaterName());
        }
    }

    // 获取更新者名称
    private Converter<Long, String> toUserName = new AbstractConverter<Long, String>() {
        protected String convert(Long userId) {
            String UserName = "";
            if (nonNull(userId)) {
                 Optional<User> user = userRepository.findById(userId);
                if(user.isPresent())
                    UserName = user.get().getUserName();
            }
            return UserName;
        }
    };


    // 更新者时间转换
    private Converter<Long, String> toUpdateTime = new AbstractConverter<Long, String>() {
        protected String convert(Long time) {
            String times = null;
            if (time != null) {
                times = DateHelper.LongToStringFormat(time,DateHelper.normalFormt);
            }
            return times;
        }
    };


    // 时间转换
    private Converter<Timestamp, String> toTime = new AbstractConverter<Timestamp, String>() {
        protected String convert(Timestamp time) {
            String times = null;
            if (time != null) {
                times = DateHelper.timeStampFormater(time,DateHelper.normalFormt);
            }
            return times;
        }
    };


    // 性别
    private Converter<Integer, String> toSexName = new AbstractConverter<Integer, String>() {
        protected String convert(Integer status) {
            if (isNull(status))
                return "";
            String statusStr = "";
            switch (status) {
                case Constants.SEX_00:
                    statusStr = Constants.SEX_00_TXT;
                    break;
                case Constants.SEX_01:
                    statusStr = Constants.SEX_01_TXT;
                    break;
            }
            return statusStr;
        }
    };

    // is lock
    private Converter<Integer, String> isLock = new AbstractConverter<Integer, String>() {
        protected String convert(Integer status) {
            if (isNull(status))
                return "";
            String statusStr = "";
            switch (status) {
                case Constants.LOCK_00:
                    statusStr = Constants.LOCK_00_TXT;
                    break;
                case Constants.LOCK_01:
                    statusStr = Constants.LOCK_01_TXT;
                    break;
            }
            return statusStr;
        }
    };

}
