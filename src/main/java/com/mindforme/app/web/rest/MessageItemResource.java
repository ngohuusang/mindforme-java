package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.MessageItemService;
import com.mindforme.app.service.dto.MessageItemDTO;
import com.mindforme.app.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link com.mindforme.app.domain.MessageItem}.
 */
@RestController
@RequestMapping("/api")
public class MessageItemResource {
    private final Logger log = LoggerFactory.getLogger(MessageItemResource.class);

    private static final String ENTITY_NAME = "messageItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MessageItemService messageItemService;

    public MessageItemResource(MessageItemService messageItemService) {
        this.messageItemService = messageItemService;
    }

    /**
     * {@code POST  /message-items} : Create a new messageItem.
     *
     * @param messageItemDTO the messageItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new messageItemDTO, or with status {@code 400 (Bad Request)} if the messageItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/message-items")
    public ResponseEntity<MessageItemDTO> createMessageItem(@RequestBody MessageItemDTO messageItemDTO) throws URISyntaxException {
        log.debug("REST request to save MessageItem : {}", messageItemDTO);
        if (messageItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new messageItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MessageItemDTO result = messageItemService.save(messageItemDTO);
        return ResponseEntity
            .created(new URI("/api/message-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /message-items} : Updates an existing messageItem.
     *
     * @param messageItemDTO the messageItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageItemDTO,
     * or with status {@code 400 (Bad Request)} if the messageItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the messageItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/message-items")
    public ResponseEntity<MessageItemDTO> updateMessageItem(@RequestBody MessageItemDTO messageItemDTO) throws URISyntaxException {
        log.debug("REST request to update MessageItem : {}", messageItemDTO);
        if (messageItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MessageItemDTO result = messageItemService.save(messageItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, messageItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /message-items} : get all the messageItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of messageItems in body.
     */
    @GetMapping("/message-items")
    public ResponseEntity<List<MessageItemDTO>> getAllMessageItems(Pageable pageable) {
        log.debug("REST request to get a page of MessageItems");
        Page<MessageItemDTO> page = messageItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /message-items/:id} : get the "id" messageItem.
     *
     * @param id the id of the messageItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the messageItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/message-items/{id}")
    public ResponseEntity<MessageItemDTO> getMessageItem(@PathVariable Long id) {
        log.debug("REST request to get MessageItem : {}", id);
        Optional<MessageItemDTO> messageItemDTO = messageItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(messageItemDTO);
    }

    /**
     * {@code DELETE  /message-items/:id} : delete the "id" messageItem.
     *
     * @param id the id of the messageItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/message-items/{id}")
    public ResponseEntity<Void> deleteMessageItem(@PathVariable Long id) {
        log.debug("REST request to delete MessageItem : {}", id);
        messageItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/message-items?query=:query} : search for the messageItem corresponding
     * to the query.
     *
     * @param query the query of the messageItem search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/message-items")
    public ResponseEntity<List<MessageItemDTO>> searchMessageItems(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MessageItems for query {}", query);
        Page<MessageItemDTO> page = messageItemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
