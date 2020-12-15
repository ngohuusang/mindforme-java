package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.MessageItem;
import com.mindforme.app.repository.MessageItemRepository;
import com.mindforme.app.repository.search.MessageItemSearchRepository;
import com.mindforme.app.service.MessageItemService;
import com.mindforme.app.service.dto.MessageItemDTO;
import com.mindforme.app.service.mapper.MessageItemMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MessageItem}.
 */
@Service
@Transactional
public class MessageItemServiceImpl implements MessageItemService {
    private final Logger log = LoggerFactory.getLogger(MessageItemServiceImpl.class);

    private final MessageItemRepository messageItemRepository;

    private final MessageItemMapper messageItemMapper;

    private final MessageItemSearchRepository messageItemSearchRepository;

    public MessageItemServiceImpl(
        MessageItemRepository messageItemRepository,
        MessageItemMapper messageItemMapper,
        MessageItemSearchRepository messageItemSearchRepository
    ) {
        this.messageItemRepository = messageItemRepository;
        this.messageItemMapper = messageItemMapper;
        this.messageItemSearchRepository = messageItemSearchRepository;
    }

    @Override
    public MessageItemDTO save(MessageItemDTO messageItemDTO) {
        log.debug("Request to save MessageItem : {}", messageItemDTO);
        MessageItem messageItem = messageItemMapper.toEntity(messageItemDTO);
        messageItem = messageItemRepository.save(messageItem);
        MessageItemDTO result = messageItemMapper.toDto(messageItem);
        messageItemSearchRepository.save(messageItem);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MessageItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MessageItems");
        return messageItemRepository.findAll(pageable).map(messageItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MessageItemDTO> findOne(Long id) {
        log.debug("Request to get MessageItem : {}", id);
        return messageItemRepository.findById(id).map(messageItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MessageItem : {}", id);
        messageItemRepository.deleteById(id);
        messageItemSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MessageItemDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MessageItems for query {}", query);
        return messageItemSearchRepository.search(queryStringQuery(query), pageable).map(messageItemMapper::toDto);
    }
}
