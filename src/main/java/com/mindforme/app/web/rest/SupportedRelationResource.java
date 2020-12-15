package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.SupportedRelationService;
import com.mindforme.app.service.dto.SupportedRelationDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.SupportedRelation}.
 */
@RestController
@RequestMapping("/api")
public class SupportedRelationResource {
    private final Logger log = LoggerFactory.getLogger(SupportedRelationResource.class);

    private static final String ENTITY_NAME = "supportedRelation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupportedRelationService supportedRelationService;

    public SupportedRelationResource(SupportedRelationService supportedRelationService) {
        this.supportedRelationService = supportedRelationService;
    }

    /**
     * {@code POST  /supported-relations} : Create a new supportedRelation.
     *
     * @param supportedRelationDTO the supportedRelationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supportedRelationDTO, or with status {@code 400 (Bad Request)} if the supportedRelation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supported-relations")
    public ResponseEntity<SupportedRelationDTO> createSupportedRelation(@RequestBody SupportedRelationDTO supportedRelationDTO)
        throws URISyntaxException {
        log.debug("REST request to save SupportedRelation : {}", supportedRelationDTO);
        if (supportedRelationDTO.getId() != null) {
            throw new BadRequestAlertException("A new supportedRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupportedRelationDTO result = supportedRelationService.save(supportedRelationDTO);
        return ResponseEntity
            .created(new URI("/api/supported-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supported-relations} : Updates an existing supportedRelation.
     *
     * @param supportedRelationDTO the supportedRelationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supportedRelationDTO,
     * or with status {@code 400 (Bad Request)} if the supportedRelationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supportedRelationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supported-relations")
    public ResponseEntity<SupportedRelationDTO> updateSupportedRelation(@RequestBody SupportedRelationDTO supportedRelationDTO)
        throws URISyntaxException {
        log.debug("REST request to update SupportedRelation : {}", supportedRelationDTO);
        if (supportedRelationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupportedRelationDTO result = supportedRelationService.save(supportedRelationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, supportedRelationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /supported-relations} : get all the supportedRelations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supportedRelations in body.
     */
    @GetMapping("/supported-relations")
    public ResponseEntity<List<SupportedRelationDTO>> getAllSupportedRelations(Pageable pageable) {
        log.debug("REST request to get a page of SupportedRelations");
        Page<SupportedRelationDTO> page = supportedRelationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /supported-relations/:id} : get the "id" supportedRelation.
     *
     * @param id the id of the supportedRelationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supportedRelationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supported-relations/{id}")
    public ResponseEntity<SupportedRelationDTO> getSupportedRelation(@PathVariable Long id) {
        log.debug("REST request to get SupportedRelation : {}", id);
        Optional<SupportedRelationDTO> supportedRelationDTO = supportedRelationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supportedRelationDTO);
    }

    /**
     * {@code DELETE  /supported-relations/:id} : delete the "id" supportedRelation.
     *
     * @param id the id of the supportedRelationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supported-relations/{id}")
    public ResponseEntity<Void> deleteSupportedRelation(@PathVariable Long id) {
        log.debug("REST request to delete SupportedRelation : {}", id);
        supportedRelationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/supported-relations?query=:query} : search for the supportedRelation corresponding
     * to the query.
     *
     * @param query the query of the supportedRelation search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/supported-relations")
    public ResponseEntity<List<SupportedRelationDTO>> searchSupportedRelations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SupportedRelations for query {}", query);
        Page<SupportedRelationDTO> page = supportedRelationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
