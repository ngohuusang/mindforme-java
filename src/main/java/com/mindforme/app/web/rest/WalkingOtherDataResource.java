package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.WalkingOtherDataService;
import com.mindforme.app.service.dto.WalkingOtherDataDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.WalkingOtherData}.
 */
@RestController
@RequestMapping("/api")
public class WalkingOtherDataResource {
    private final Logger log = LoggerFactory.getLogger(WalkingOtherDataResource.class);

    private static final String ENTITY_NAME = "walkingOtherData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WalkingOtherDataService walkingOtherDataService;

    public WalkingOtherDataResource(WalkingOtherDataService walkingOtherDataService) {
        this.walkingOtherDataService = walkingOtherDataService;
    }

    /**
     * {@code POST  /walking-other-data} : Create a new walkingOtherData.
     *
     * @param walkingOtherDataDTO the walkingOtherDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new walkingOtherDataDTO, or with status {@code 400 (Bad Request)} if the walkingOtherData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/walking-other-data")
    public ResponseEntity<WalkingOtherDataDTO> createWalkingOtherData(@Valid @RequestBody WalkingOtherDataDTO walkingOtherDataDTO)
        throws URISyntaxException {
        log.debug("REST request to save WalkingOtherData : {}", walkingOtherDataDTO);
        if (walkingOtherDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new walkingOtherData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WalkingOtherDataDTO result = walkingOtherDataService.save(walkingOtherDataDTO);
        return ResponseEntity
            .created(new URI("/api/walking-other-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /walking-other-data} : Updates an existing walkingOtherData.
     *
     * @param walkingOtherDataDTO the walkingOtherDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated walkingOtherDataDTO,
     * or with status {@code 400 (Bad Request)} if the walkingOtherDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the walkingOtherDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/walking-other-data")
    public ResponseEntity<WalkingOtherDataDTO> updateWalkingOtherData(@Valid @RequestBody WalkingOtherDataDTO walkingOtherDataDTO)
        throws URISyntaxException {
        log.debug("REST request to update WalkingOtherData : {}", walkingOtherDataDTO);
        if (walkingOtherDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WalkingOtherDataDTO result = walkingOtherDataService.save(walkingOtherDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, walkingOtherDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /walking-other-data} : get all the walkingOtherData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of walkingOtherData in body.
     */
    @GetMapping("/walking-other-data")
    public ResponseEntity<List<WalkingOtherDataDTO>> getAllWalkingOtherData(Pageable pageable) {
        log.debug("REST request to get a page of WalkingOtherData");
        Page<WalkingOtherDataDTO> page = walkingOtherDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /walking-other-data/:id} : get the "id" walkingOtherData.
     *
     * @param id the id of the walkingOtherDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the walkingOtherDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/walking-other-data/{id}")
    public ResponseEntity<WalkingOtherDataDTO> getWalkingOtherData(@PathVariable Long id) {
        log.debug("REST request to get WalkingOtherData : {}", id);
        Optional<WalkingOtherDataDTO> walkingOtherDataDTO = walkingOtherDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(walkingOtherDataDTO);
    }

    /**
     * {@code DELETE  /walking-other-data/:id} : delete the "id" walkingOtherData.
     *
     * @param id the id of the walkingOtherDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/walking-other-data/{id}")
    public ResponseEntity<Void> deleteWalkingOtherData(@PathVariable Long id) {
        log.debug("REST request to delete WalkingOtherData : {}", id);
        walkingOtherDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/walking-other-data?query=:query} : search for the walkingOtherData corresponding
     * to the query.
     *
     * @param query the query of the walkingOtherData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/walking-other-data")
    public ResponseEntity<List<WalkingOtherDataDTO>> searchWalkingOtherData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of WalkingOtherData for query {}", query);
        Page<WalkingOtherDataDTO> page = walkingOtherDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
