package com.mindforme.app.service.mapper;

import com.mindforme.app.domain.*;
import com.mindforme.app.service.dto.MessageItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MessageItem} and its DTO {@link MessageItemDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, MessageMapper.class })
public interface MessageItemMapper extends EntityMapper<MessageItemDTO, MessageItem> {
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "sender.login", target = "senderLogin")
    @Mapping(source = "message.id", target = "messageId")
    MessageItemDTO toDto(MessageItem messageItem);

    @Mapping(source = "senderId", target = "sender")
    @Mapping(source = "messageId", target = "message")
    MessageItem toEntity(MessageItemDTO messageItemDTO);

    default MessageItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        MessageItem messageItem = new MessageItem();
        messageItem.setId(id);
        return messageItem;
    }
}
