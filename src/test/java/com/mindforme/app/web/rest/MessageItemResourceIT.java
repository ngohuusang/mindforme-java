package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.MessageItem;
import com.mindforme.app.repository.MessageItemRepository;
import com.mindforme.app.repository.search.MessageItemSearchRepository;
import com.mindforme.app.service.MessageItemService;
import com.mindforme.app.service.dto.MessageItemDTO;
import com.mindforme.app.service.mapper.MessageItemMapper;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MessageItemResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MessageItemResourceIT {
    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_READ = false;
    private static final Boolean UPDATED_READ = true;

    @Autowired
    private MessageItemRepository messageItemRepository;

    @Autowired
    private MessageItemMapper messageItemMapper;

    @Autowired
    private MessageItemService messageItemService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.MessageItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private MessageItemSearchRepository mockMessageItemSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMessageItemMockMvc;

    private MessageItem messageItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageItem createEntity(EntityManager em) {
        MessageItem messageItem = new MessageItem().content(DEFAULT_CONTENT).read(DEFAULT_READ);
        return messageItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageItem createUpdatedEntity(EntityManager em) {
        MessageItem messageItem = new MessageItem().content(UPDATED_CONTENT).read(UPDATED_READ);
        return messageItem;
    }

    @BeforeEach
    public void initTest() {
        messageItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createMessageItem() throws Exception {
        int databaseSizeBeforeCreate = messageItemRepository.findAll().size();
        // Create the MessageItem
        MessageItemDTO messageItemDTO = messageItemMapper.toDto(messageItem);
        restMessageItemMockMvc
            .perform(
                post("/api/message-items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MessageItem in the database
        List<MessageItem> messageItemList = messageItemRepository.findAll();
        assertThat(messageItemList).hasSize(databaseSizeBeforeCreate + 1);
        MessageItem testMessageItem = messageItemList.get(messageItemList.size() - 1);
        assertThat(testMessageItem.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testMessageItem.isRead()).isEqualTo(DEFAULT_READ);

        // Validate the MessageItem in Elasticsearch
        verify(mockMessageItemSearchRepository, times(1)).save(testMessageItem);
    }

    @Test
    @Transactional
    public void createMessageItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageItemRepository.findAll().size();

        // Create the MessageItem with an existing ID
        messageItem.setId(1L);
        MessageItemDTO messageItemDTO = messageItemMapper.toDto(messageItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageItemMockMvc
            .perform(
                post("/api/message-items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageItem in the database
        List<MessageItem> messageItemList = messageItemRepository.findAll();
        assertThat(messageItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the MessageItem in Elasticsearch
        verify(mockMessageItemSearchRepository, times(0)).save(messageItem);
    }

    @Test
    @Transactional
    public void getAllMessageItems() throws Exception {
        // Initialize the database
        messageItemRepository.saveAndFlush(messageItem);

        // Get all the messageItemList
        restMessageItemMockMvc
            .perform(get("/api/message-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].read").value(hasItem(DEFAULT_READ.booleanValue())));
    }

    @Test
    @Transactional
    public void getMessageItem() throws Exception {
        // Initialize the database
        messageItemRepository.saveAndFlush(messageItem);

        // Get the messageItem
        restMessageItemMockMvc
            .perform(get("/api/message-items/{id}", messageItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(messageItem.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.read").value(DEFAULT_READ.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMessageItem() throws Exception {
        // Get the messageItem
        restMessageItemMockMvc.perform(get("/api/message-items/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessageItem() throws Exception {
        // Initialize the database
        messageItemRepository.saveAndFlush(messageItem);

        int databaseSizeBeforeUpdate = messageItemRepository.findAll().size();

        // Update the messageItem
        MessageItem updatedMessageItem = messageItemRepository.findById(messageItem.getId()).get();
        // Disconnect from session so that the updates on updatedMessageItem are not directly saved in db
        em.detach(updatedMessageItem);
        updatedMessageItem.content(UPDATED_CONTENT).read(UPDATED_READ);
        MessageItemDTO messageItemDTO = messageItemMapper.toDto(updatedMessageItem);

        restMessageItemMockMvc
            .perform(
                put("/api/message-items").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(messageItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the MessageItem in the database
        List<MessageItem> messageItemList = messageItemRepository.findAll();
        assertThat(messageItemList).hasSize(databaseSizeBeforeUpdate);
        MessageItem testMessageItem = messageItemList.get(messageItemList.size() - 1);
        assertThat(testMessageItem.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testMessageItem.isRead()).isEqualTo(UPDATED_READ);

        // Validate the MessageItem in Elasticsearch
        verify(mockMessageItemSearchRepository, times(1)).save(testMessageItem);
    }

    @Test
    @Transactional
    public void updateNonExistingMessageItem() throws Exception {
        int databaseSizeBeforeUpdate = messageItemRepository.findAll().size();

        // Create the MessageItem
        MessageItemDTO messageItemDTO = messageItemMapper.toDto(messageItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageItemMockMvc
            .perform(
                put("/api/message-items").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(messageItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageItem in the database
        List<MessageItem> messageItemList = messageItemRepository.findAll();
        assertThat(messageItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MessageItem in Elasticsearch
        verify(mockMessageItemSearchRepository, times(0)).save(messageItem);
    }

    @Test
    @Transactional
    public void deleteMessageItem() throws Exception {
        // Initialize the database
        messageItemRepository.saveAndFlush(messageItem);

        int databaseSizeBeforeDelete = messageItemRepository.findAll().size();

        // Delete the messageItem
        restMessageItemMockMvc
            .perform(delete("/api/message-items/{id}", messageItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MessageItem> messageItemList = messageItemRepository.findAll();
        assertThat(messageItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MessageItem in Elasticsearch
        verify(mockMessageItemSearchRepository, times(1)).deleteById(messageItem.getId());
    }

    @Test
    @Transactional
    public void searchMessageItem() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        messageItemRepository.saveAndFlush(messageItem);
        when(mockMessageItemSearchRepository.search(queryStringQuery("id:" + messageItem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(messageItem), PageRequest.of(0, 1), 1));

        // Search the messageItem
        restMessageItemMockMvc
            .perform(get("/api/_search/message-items?query=id:" + messageItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].read").value(hasItem(DEFAULT_READ.booleanValue())));
    }
}
