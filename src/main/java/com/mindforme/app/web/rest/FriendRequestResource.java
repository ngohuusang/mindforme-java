package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.FriendRequestService;
import com.mindforme.app.service.dto.FriendRequestDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.FriendRequest}.
 */
@RestController
@RequestMapping("/api")
public class FriendRequestResource {
    private final Logger log = LoggerFactory.getLogger(FriendRequestResource.class);

    private static final String ENTITY_NAME = "friendRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FriendRequestService friendRequestService;

    public FriendRequestResource(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    /**
     * {@code POST  /friend-requests} : Create a new friendRequest.
     *
     * @param friendRequestDTO the friendRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new friendRequestDTO, or with status {@code 400 (Bad Request)} if the friendRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/friend-requests")
    public ResponseEntity<FriendRequestDTO> createFriendRequest(@RequestBody FriendRequestDTO friendRequestDTO) throws URISyntaxException {
        log.debug("REST request to save FriendRequest : {}", friendRequestDTO);
        if (friendRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new friendRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FriendRequestDTO result = friendRequestService.save(friendRequestDTO);
        return ResponseEntity
            .created(new URI("/api/friend-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /friend-requests} : Updates an existing friendRequest.
     *
     * @param friendRequestDTO the friendRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated friendRequestDTO,
     * or with status {@code 400 (Bad Request)} if the friendRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the friendRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/friend-requests")
    public ResponseEntity<FriendRequestDTO> updateFriendRequest(@RequestBody FriendRequestDTO friendRequestDTO) throws URISyntaxException {
        log.debug("REST request to update FriendRequest : {}", friendRequestDTO);
        if (friendRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FriendRequestDTO result = friendRequestService.save(friendRequestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, friendRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /friend-requests} : get all the friendRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of friendRequests in body.
     */
    @GetMapping("/friend-requests")
    public ResponseEntity<List<FriendRequestDTO>> getAllFriendRequests(Pageable pageable) {
        log.debug("REST request to get a page of FriendRequests");
        Page<FriendRequestDTO> page = friendRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /friend-requests/:id} : get the "id" friendRequest.
     *
     * @param id the id of the friendRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the friendRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/friend-requests/{id}")
    public ResponseEntity<FriendRequestDTO> getFriendRequest(@PathVariable Long id) {
        log.debug("REST request to get FriendRequest : {}", id);
        Optional<FriendRequestDTO> friendRequestDTO = friendRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(friendRequestDTO);
    }

    /**
     * {@code DELETE  /friend-requests/:id} : delete the "id" friendRequest.
     *
     * @param id the id of the friendRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/friend-requests/{id}")
    public ResponseEntity<Void> deleteFriendRequest(@PathVariable Long id) {
        log.debug("REST request to delete FriendRequest : {}", id);
        friendRequestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/friend-requests?query=:query} : search for the friendRequest corresponding
     * to the query.
     *
     * @param query the query of the friendRequest search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/friend-requests")
    public ResponseEntity<List<FriendRequestDTO>> searchFriendRequests(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FriendRequests for query {}", query);
        Page<FriendRequestDTO> page = friendRequestService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
