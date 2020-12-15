package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.ChildRelationDataService;
import com.mindforme.app.service.dto.ChildRelationDataDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.ChildRelationData}.
 */
@RestController
@RequestMapping("/api")
public class ChildRelationDataResource {
    private final Logger log = LoggerFactory.getLogger(ChildRelationDataResource.class);

    private static final String ENTITY_NAME = "childRelationData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChildRelationDataService childRelationDataService;

    public ChildRelationDataResource(ChildRelationDataService childRelationDataService) {
        this.childRelationDataService = childRelationDataService;
    }

    /**
     * {@code POST  /child-relation-data} : Create a new childRelationData.
     *
     * @param childRelationDataDTO the childRelationDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new childRelationDataDTO, or with status {@code 400 (Bad Request)} if the childRelationData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/child-relation-data")
    public ResponseEntity<ChildRelationDataDTO> createChildRelationData(@Valid @RequestBody ChildRelationDataDTO childRelationDataDTO)
        throws URISyntaxException {
        log.debug("REST request to save ChildRelationData : {}", childRelationDataDTO);
        if (childRelationDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new childRelationData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChildRelationDataDTO result = childRelationDataService.save(childRelationDataDTO);
        return ResponseEntity
            .created(new URI("/api/child-relation-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /child-relation-data} : Updates an existing childRelationData.
     *
     * @param childRelationDataDTO the childRelationDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated childRelationDataDTO,
     * or with status {@code 400 (Bad Request)} if the childRelationDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the childRelationDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/child-relation-data")
    public ResponseEntity<ChildRelationDataDTO> updateChildRelationData(@Valid @RequestBody ChildRelationDataDTO childRelationDataDTO)
        throws URISyntaxException {
        log.debug("REST request to update ChildRelationData : {}", childRelationDataDTO);
        if (childRelationDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChildRelationDataDTO result = childRelationDataService.save(childRelationDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, childRelationDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /child-relation-data} : get all the childRelationData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of childRelationData in body.
     */
    @GetMapping("/child-relation-data")
    public ResponseEntity<List<ChildRelationDataDTO>> getAllChildRelationData(Pageable pageable) {
        log.debug("REST request to get a page of ChildRelationData");
        Page<ChildRelationDataDTO> page = childRelationDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /child-relation-data/:id} : get the "id" childRelationData.
     *
     * @param id the id of the childRelationDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the childRelationDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/child-relation-data/{id}")
    public ResponseEntity<ChildRelationDataDTO> getChildRelationData(@PathVariable Long id) {
        log.debug("REST request to get ChildRelationData : {}", id);
        Optional<ChildRelationDataDTO> childRelationDataDTO = childRelationDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(childRelationDataDTO);
    }

    /**
     * {@code DELETE  /child-relation-data/:id} : delete the "id" childRelationData.
     *
     * @param id the id of the childRelationDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/child-relation-data/{id}")
    public ResponseEntity<Void> deleteChildRelationData(@PathVariable Long id) {
        log.debug("REST request to delete ChildRelationData : {}", id);
        childRelationDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/child-relation-data?query=:query} : search for the childRelationData corresponding
     * to the query.
     *
     * @param query the query of the childRelationData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/child-relation-data")
    public ResponseEntity<List<ChildRelationDataDTO>> searchChildRelationData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ChildRelationData for query {}", query);
        Page<ChildRelationDataDTO> page = childRelationDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
