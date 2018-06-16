package com.minimall.boilerplate.business.service;

import com.minimall.boilerplate.business.dto.MessagesDTO;
import com.minimall.boilerplate.business.dto.assembler.MessagesAssembler;
import com.minimall.boilerplate.business.entity.Messages;
import com.minimall.boilerplate.business.repository.MessagesSpecification;
import com.minimall.boilerplate.business.repository.MessagesRepository;
import com.minimall.boilerplate.common.MessageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.Objects.nonNull;

@Service
public class MessagesSerivce {

    @Autowired
    private MessagesRepository messagesRepository;
    @Autowired
    private MessagesAssembler messagesAssembler;

    // 列表
    @Transactional
    public List<MessagesDTO> messagesList(MessagesDTO messagesDTO, Pageable pageable, MessageObject mo) {
        Page<Messages> messages = messagesRepository.findAll(MessagesSpecification.conditionQuerySpec(messagesDTO), pageable);
        if (nonNull(messages)) {
            mo.put("total", messages.getTotalElements());
        }
        return messages.getContent().stream()
                .map(messagesAssembler::toDTO)
                .flatMap(dto -> dto.isPresent() ? Stream.of(dto.get()) : Stream.empty())
                .collect(Collectors.toList());
    }


    //更新消息表的可读,和读取时间
    @Transactional
    public void updateRead(Long messageId) {
        messagesRepository.updateIsRead(messageId);
    }


    //获取未读消息条数
    @Transactional
    public Integer getCountNotRead(Long userId) {
        return messagesRepository.countByUserIdAndIsRead(userId, 0);
    }

    /*
    //获取消息条数
    @Transactional
    public Integer getCount(Long userId) {
        return messagesRepository.countByUserId(userId);
    }*/

}
