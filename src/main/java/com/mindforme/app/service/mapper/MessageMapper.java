package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.MessageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Message} and its DTO {@link MessageDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface MessageMapper extends EntityMapper<MessageDTO, Message> {
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "sender.login", target = "senderLogin")
    @Mapping(source = "receiver.id", target = "receiverId")
    @Mapping(source = "receiver.login", target = "receiverLogin")
    MessageDTO toDto(Message message);

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "removeItem", ignore = true)
    @Mapping(source = "senderId", target = "sender")
    @Mapping(source = "receiverId", target = "receiver")
    Message toEntity(MessageDTO messageDTO);

    default Message fromId(Long id) {
        if (id == null) {
            return null;
        }
        Message message = new Message();
        message.setId(id);
        return message;
    }
}
