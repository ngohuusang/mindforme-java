package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.ChildRelationService;
import com.mindforme.app.service.dto.ChildRelationDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.ChildRelation}.
 */
@RestController
@RequestMapping("/api")
public class ChildRelationResource {
    private final Logger log = LoggerFactory.getLogger(ChildRelationResource.class);

    private static final String ENTITY_NAME = "childRelation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChildRelationService childRelationService;

    public ChildRelationResource(ChildRelationService childRelationService) {
        this.childRelationService = childRelationService;
    }

    /**
     * {@code POST  /child-relations} : Create a new childRelation.
     *
     * @param childRelationDTO the childRelationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new childRelationDTO, or with status {@code 400 (Bad Request)} if the childRelation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/child-relations")
    public ResponseEntity<ChildRelationDTO> createChildRelation(@RequestBody ChildRelationDTO childRelationDTO) throws URISyntaxException {
        log.debug("REST request to save ChildRelation : {}", childRelationDTO);
        if (childRelationDTO.getId() != null) {
            throw new BadRequestAlertException("A new childRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChildRelationDTO result = childRelationService.save(childRelationDTO);
        return ResponseEntity
            .created(new URI("/api/child-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /child-relations} : Updates an existing childRelation.
     *
     * @param childRelationDTO the childRelationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated childRelationDTO,
     * or with status {@code 400 (Bad Request)} if the childRelationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the childRelationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/child-relations")
    public ResponseEntity<ChildRelationDTO> updateChildRelation(@RequestBody ChildRelationDTO childRelationDTO) throws URISyntaxException {
        log.debug("REST request to update ChildRelation : {}", childRelationDTO);
        if (childRelationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChildRelationDTO result = childRelationService.save(childRelationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, childRelationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /child-relations} : get all the childRelations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of childRelations in body.
     */
    @GetMapping("/child-relations")
    public ResponseEntity<List<ChildRelationDTO>> getAllChildRelations(Pageable pageable) {
        log.debug("REST request to get a page of ChildRelations");
        Page<ChildRelationDTO> page = childRelationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /child-relations/:id} : get the "id" childRelation.
     *
     * @param id the id of the childRelationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the childRelationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/child-relations/{id}")
    public ResponseEntity<ChildRelationDTO> getChildRelation(@PathVariable Long id) {
        log.debug("REST request to get ChildRelation : {}", id);
        Optional<ChildRelationDTO> childRelationDTO = childRelationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(childRelationDTO);
    }

    /**
     * {@code DELETE  /child-relations/:id} : delete the "id" childRelation.
     *
     * @param id the id of the childRelationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/child-relations/{id}")
    public ResponseEntity<Void> deleteChildRelation(@PathVariable Long id) {
        log.debug("REST request to delete ChildRelation : {}", id);
        childRelationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/child-relations?query=:query} : search for the childRelation corresponding
     * to the query.
     *
     * @param query the query of the childRelation search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/child-relations")
    public ResponseEntity<List<ChildRelationDTO>> searchChildRelations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ChildRelations for query {}", query);
        Page<ChildRelationDTO> page = childRelationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
