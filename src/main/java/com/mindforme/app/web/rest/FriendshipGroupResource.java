package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.FriendshipGroupService;
import com.mindforme.app.service.dto.FriendshipGroupDTO;
import com.mindforme.app.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
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
 * REST controller for managing {@link com.mindforme.app.domain.FriendshipGroup}.
 */
@RestController
@RequestMapping("/api")
public class FriendshipGroupResource {
    private final Logger log = LoggerFactory.getLogger(FriendshipGroupResource.class);

    private static final String ENTITY_NAME = "friendshipGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FriendshipGroupService friendshipGroupService;

    public FriendshipGroupResource(FriendshipGroupService friendshipGroupService) {
        this.friendshipGroupService = friendshipGroupService;
    }

    /**
     * {@code POST  /friendship-groups} : Create a new friendshipGroup.
     *
     * @param friendshipGroupDTO the friendshipGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new friendshipGroupDTO, or with status {@code 400 (Bad Request)} if the friendshipGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/friendship-groups")
    public ResponseEntity<FriendshipGroupDTO> createFriendshipGroup(@Valid @RequestBody FriendshipGroupDTO friendshipGroupDTO)
        throws URISyntaxException {
        log.debug("REST request to save FriendshipGroup : {}", friendshipGroupDTO);
        if (friendshipGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new friendshipGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FriendshipGroupDTO result = friendshipGroupService.save(friendshipGroupDTO);
        return ResponseEntity
            .created(new URI("/api/friendship-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /friendship-groups} : Updates an existing friendshipGroup.
     *
     * @param friendshipGroupDTO the friendshipGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated friendshipGroupDTO,
     * or with status {@code 400 (Bad Request)} if the friendshipGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the friendshipGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/friendship-groups")
    public ResponseEntity<FriendshipGroupDTO> updateFriendshipGroup(@Valid @RequestBody FriendshipGroupDTO friendshipGroupDTO)
        throws URISyntaxException {
        log.debug("REST request to update FriendshipGroup : {}", friendshipGroupDTO);
        if (friendshipGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FriendshipGroupDTO result = friendshipGroupService.save(friendshipGroupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, friendshipGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /friendship-groups} : get all the friendshipGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of friendshipGroups in body.
     */
    @GetMapping("/friendship-groups")
    public ResponseEntity<List<FriendshipGroupDTO>> getAllFriendshipGroups(Pageable pageable) {
        log.debug("REST request to get a page of FriendshipGroups");
        Page<FriendshipGroupDTO> page = friendshipGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /friendship-groups/:id} : get the "id" friendshipGroup.
     *
     * @param id the id of the friendshipGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the friendshipGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/friendship-groups/{id}")
    public ResponseEntity<FriendshipGroupDTO> getFriendshipGroup(@PathVariable Long id) {
        log.debug("REST request to get FriendshipGroup : {}", id);
        Optional<FriendshipGroupDTO> friendshipGroupDTO = friendshipGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(friendshipGroupDTO);
    }

    /**
     * {@code DELETE  /friendship-groups/:id} : delete the "id" friendshipGroup.
     *
     * @param id the id of the friendshipGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/friendship-groups/{id}")
    public ResponseEntity<Void> deleteFriendshipGroup(@PathVariable Long id) {
        log.debug("REST request to delete FriendshipGroup : {}", id);
        friendshipGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/friendship-groups?query=:query} : search for the friendshipGroup corresponding
     * to the query.
     *
     * @param query the query of the friendshipGroup search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/friendship-groups")
    public ResponseEntity<List<FriendshipGroupDTO>> searchFriendshipGroups(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FriendshipGroups for query {}", query);
        Page<FriendshipGroupDTO> page = friendshipGroupService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
