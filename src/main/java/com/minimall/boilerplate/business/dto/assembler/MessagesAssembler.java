package com.minimall.boilerplate.business.dto.assembler;

import com.minimall.boilerplate.business.dto.MessagesDTO;
import com.minimall.boilerplate.business.entity.Messages;
import com.minimall.boilerplate.business.repository.MessagesRepository;
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
public class MessagesAssembler implements IDTOAssembler<MessagesDTO,Messages> {
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private MessagesRepository messagesRepository;

    public MessagesAssembler() {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.addMappings(new MessagesAssembler.MessagesDTOMap());
        modelMapper.addMappings(new MessagesAssembler.MessagesMap());
    }

    @Override
    public Optional<MessagesDTO> toDTO(Messages messages) {
        nonNull(messages);
        return Optional.ofNullable(modelMapper.map(messages, MessagesDTO.class));
    }

    @Override
    public Optional<Messages> toEntity(MessagesDTO messagesDTO) {
        nonNull(messagesDTO);
        Messages messages;
        Long id = messagesDTO.getId();
        if (isNull(id))
            messages = Optional.ofNullable(messagesRepository.getOne(id)).orElse(new Messages());
        else
            messages = new Messages();
        modelMapper.map(messagesDTO, messages);
        return Optional.of(messages);
    }

    private class MessagesDTOMap extends PropertyMap<MessagesDTO, Messages> {
        @Override
        protected void configure() {

        }
    }

    private class MessagesMap extends PropertyMap<Messages, MessagesDTO> {
        @Override
        protected void configure() {
            map(source.getUser().getUserId(),destination.getUserId());
            map(source.getUser().getUserName(),destination.getUserName());

            map(source.getMessageType().getId(),destination.getType());
            map(source.getMessageType().getName(),destination.getTypeName());

            using(toMessages).map(source.getIsRead(),destination.getIsReadName());
            using(toTime).map(source.getReadTime(),destination.getReadTime());
            using(toTime).map(source.getSendTime(),destination.getSendTime());
        }
    }

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

    // 消息值
    private Converter<Integer, String> toMessages = new AbstractConverter<Integer, String>() {
        protected String convert(Integer status) {
            if (isNull(status))
                return "";
            String statusStr = "";
            switch (status) {
                case Constants.MESSAGES_00:
                    statusStr = Constants.MESSAGES_00_TXT;
                    break;
                case Constants.MESSAGES_01:
                    statusStr = Constants.MESSAGES_01_TXT;
                    break;
            }
            return statusStr;
        }
    };
}
