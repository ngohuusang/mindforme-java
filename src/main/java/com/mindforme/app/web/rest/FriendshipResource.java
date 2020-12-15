package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.FriendshipService;
import com.mindforme.app.service.dto.FriendshipDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.Friendship}.
 */
@RestController
@RequestMapping("/api")
public class FriendshipResource {
    private final Logger log = LoggerFactory.getLogger(FriendshipResource.class);

    private static final String ENTITY_NAME = "friendship";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FriendshipService friendshipService;

    public FriendshipResource(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    /**
     * {@code POST  /friendships} : Create a new friendship.
     *
     * @param friendshipDTO the friendshipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new friendshipDTO, or with status {@code 400 (Bad Request)} if the friendship has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/friendships")
    public ResponseEntity<FriendshipDTO> createFriendship(@RequestBody FriendshipDTO friendshipDTO) throws URISyntaxException {
        log.debug("REST request to save Friendship : {}", friendshipDTO);
        if (friendshipDTO.getId() != null) {
            throw new BadRequestAlertException("A new friendship cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FriendshipDTO result = friendshipService.save(friendshipDTO);
        return ResponseEntity
            .created(new URI("/api/friendships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /friendships} : Updates an existing friendship.
     *
     * @param friendshipDTO the friendshipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated friendshipDTO,
     * or with status {@code 400 (Bad Request)} if the friendshipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the friendshipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/friendships")
    public ResponseEntity<FriendshipDTO> updateFriendship(@RequestBody FriendshipDTO friendshipDTO) throws URISyntaxException {
        log.debug("REST request to update Friendship : {}", friendshipDTO);
        if (friendshipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FriendshipDTO result = friendshipService.save(friendshipDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, friendshipDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /friendships} : get all the friendships.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of friendships in body.
     */
    @GetMapping("/friendships")
    public ResponseEntity<List<FriendshipDTO>> getAllFriendships(Pageable pageable) {
        log.debug("REST request to get a page of Friendships");
        Page<FriendshipDTO> page = friendshipService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /friendships/:id} : get the "id" friendship.
     *
     * @param id the id of the friendshipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the friendshipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/friendships/{id}")
    public ResponseEntity<FriendshipDTO> getFriendship(@PathVariable Long id) {
        log.debug("REST request to get Friendship : {}", id);
        Optional<FriendshipDTO> friendshipDTO = friendshipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(friendshipDTO);
    }

    /**
     * {@code DELETE  /friendships/:id} : delete the "id" friendship.
     *
     * @param id the id of the friendshipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/friendships/{id}")
    public ResponseEntity<Void> deleteFriendship(@PathVariable Long id) {
        log.debug("REST request to delete Friendship : {}", id);
        friendshipService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/friendships?query=:query} : search for the friendship corresponding
     * to the query.
     *
     * @param query the query of the friendship search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/friendships")
    public ResponseEntity<List<FriendshipDTO>> searchFriendships(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Friendships for query {}", query);
        Page<FriendshipDTO> page = friendshipService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
