package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.UserIdentificationService;
import com.mindforme.app.service.dto.UserIdentificationDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.UserIdentification}.
 */
@RestController
@RequestMapping("/api")
public class UserIdentificationResource {
    private final Logger log = LoggerFactory.getLogger(UserIdentificationResource.class);

    private static final String ENTITY_NAME = "userIdentification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserIdentificationService userIdentificationService;

    public UserIdentificationResource(UserIdentificationService userIdentificationService) {
        this.userIdentificationService = userIdentificationService;
    }

    /**
     * {@code POST  /user-identifications} : Create a new userIdentification.
     *
     * @param userIdentificationDTO the userIdentificationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userIdentificationDTO, or with status {@code 400 (Bad Request)} if the userIdentification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-identifications")
    public ResponseEntity<UserIdentificationDTO> createUserIdentification(@RequestBody UserIdentificationDTO userIdentificationDTO)
        throws URISyntaxException {
        log.debug("REST request to save UserIdentification : {}", userIdentificationDTO);
        if (userIdentificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new userIdentification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserIdentificationDTO result = userIdentificationService.save(userIdentificationDTO);
        return ResponseEntity
            .created(new URI("/api/user-identifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-identifications} : Updates an existing userIdentification.
     *
     * @param userIdentificationDTO the userIdentificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userIdentificationDTO,
     * or with status {@code 400 (Bad Request)} if the userIdentificationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userIdentificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-identifications")
    public ResponseEntity<UserIdentificationDTO> updateUserIdentification(@RequestBody UserIdentificationDTO userIdentificationDTO)
        throws URISyntaxException {
        log.debug("REST request to update UserIdentification : {}", userIdentificationDTO);
        if (userIdentificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserIdentificationDTO result = userIdentificationService.save(userIdentificationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userIdentificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-identifications} : get all the userIdentifications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userIdentifications in body.
     */
    @GetMapping("/user-identifications")
    public ResponseEntity<List<UserIdentificationDTO>> getAllUserIdentifications(Pageable pageable) {
        log.debug("REST request to get a page of UserIdentifications");
        Page<UserIdentificationDTO> page = userIdentificationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-identifications/:id} : get the "id" userIdentification.
     *
     * @param id the id of the userIdentificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userIdentificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-identifications/{id}")
    public ResponseEntity<UserIdentificationDTO> getUserIdentification(@PathVariable Long id) {
        log.debug("REST request to get UserIdentification : {}", id);
        Optional<UserIdentificationDTO> userIdentificationDTO = userIdentificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userIdentificationDTO);
    }

    /**
     * {@code DELETE  /user-identifications/:id} : delete the "id" userIdentification.
     *
     * @param id the id of the userIdentificationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-identifications/{id}")
    public ResponseEntity<Void> deleteUserIdentification(@PathVariable Long id) {
        log.debug("REST request to delete UserIdentification : {}", id);
        userIdentificationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/user-identifications?query=:query} : search for the userIdentification corresponding
     * to the query.
     *
     * @param query the query of the userIdentification search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/user-identifications")
    public ResponseEntity<List<UserIdentificationDTO>> searchUserIdentifications(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UserIdentifications for query {}", query);
        Page<UserIdentificationDTO> page = userIdentificationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
