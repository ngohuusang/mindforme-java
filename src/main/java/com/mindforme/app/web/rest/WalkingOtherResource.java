package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.WalkingOtherService;
import com.mindforme.app.service.dto.WalkingOtherDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.WalkingOther}.
 */
@RestController
@RequestMapping("/api")
public class WalkingOtherResource {
    private final Logger log = LoggerFactory.getLogger(WalkingOtherResource.class);

    private static final String ENTITY_NAME = "walkingOther";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WalkingOtherService walkingOtherService;

    public WalkingOtherResource(WalkingOtherService walkingOtherService) {
        this.walkingOtherService = walkingOtherService;
    }

    /**
     * {@code POST  /walking-others} : Create a new walkingOther.
     *
     * @param walkingOtherDTO the walkingOtherDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new walkingOtherDTO, or with status {@code 400 (Bad Request)} if the walkingOther has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/walking-others")
    public ResponseEntity<WalkingOtherDTO> createWalkingOther(@RequestBody WalkingOtherDTO walkingOtherDTO) throws URISyntaxException {
        log.debug("REST request to save WalkingOther : {}", walkingOtherDTO);
        if (walkingOtherDTO.getId() != null) {
            throw new BadRequestAlertException("A new walkingOther cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WalkingOtherDTO result = walkingOtherService.save(walkingOtherDTO);
        return ResponseEntity
            .created(new URI("/api/walking-others/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /walking-others} : Updates an existing walkingOther.
     *
     * @param walkingOtherDTO the walkingOtherDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated walkingOtherDTO,
     * or with status {@code 400 (Bad Request)} if the walkingOtherDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the walkingOtherDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/walking-others")
    public ResponseEntity<WalkingOtherDTO> updateWalkingOther(@RequestBody WalkingOtherDTO walkingOtherDTO) throws URISyntaxException {
        log.debug("REST request to update WalkingOther : {}", walkingOtherDTO);
        if (walkingOtherDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WalkingOtherDTO result = walkingOtherService.save(walkingOtherDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, walkingOtherDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /walking-others} : get all the walkingOthers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of walkingOthers in body.
     */
    @GetMapping("/walking-others")
    public ResponseEntity<List<WalkingOtherDTO>> getAllWalkingOthers(Pageable pageable) {
        log.debug("REST request to get a page of WalkingOthers");
        Page<WalkingOtherDTO> page = walkingOtherService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /walking-others/:id} : get the "id" walkingOther.
     *
     * @param id the id of the walkingOtherDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the walkingOtherDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/walking-others/{id}")
    public ResponseEntity<WalkingOtherDTO> getWalkingOther(@PathVariable Long id) {
        log.debug("REST request to get WalkingOther : {}", id);
        Optional<WalkingOtherDTO> walkingOtherDTO = walkingOtherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(walkingOtherDTO);
    }

    /**
     * {@code DELETE  /walking-others/:id} : delete the "id" walkingOther.
     *
     * @param id the id of the walkingOtherDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/walking-others/{id}")
    public ResponseEntity<Void> deleteWalkingOther(@PathVariable Long id) {
        log.debug("REST request to delete WalkingOther : {}", id);
        walkingOtherService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/walking-others?query=:query} : search for the walkingOther corresponding
     * to the query.
     *
     * @param query the query of the walkingOther search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/walking-others")
    public ResponseEntity<List<WalkingOtherDTO>> searchWalkingOthers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of WalkingOthers for query {}", query);
        Page<WalkingOtherDTO> page = walkingOtherService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
