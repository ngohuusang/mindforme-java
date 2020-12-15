package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.SupportedRelationDataService;
import com.mindforme.app.service.dto.SupportedRelationDataDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.SupportedRelationData}.
 */
@RestController
@RequestMapping("/api")
public class SupportedRelationDataResource {
    private final Logger log = LoggerFactory.getLogger(SupportedRelationDataResource.class);

    private static final String ENTITY_NAME = "supportedRelationData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupportedRelationDataService supportedRelationDataService;

    public SupportedRelationDataResource(SupportedRelationDataService supportedRelationDataService) {
        this.supportedRelationDataService = supportedRelationDataService;
    }

    /**
     * {@code POST  /supported-relation-data} : Create a new supportedRelationData.
     *
     * @param supportedRelationDataDTO the supportedRelationDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supportedRelationDataDTO, or with status {@code 400 (Bad Request)} if the supportedRelationData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supported-relation-data")
    public ResponseEntity<SupportedRelationDataDTO> createSupportedRelationData(
        @Valid @RequestBody SupportedRelationDataDTO supportedRelationDataDTO
    )
        throws URISyntaxException {
        log.debug("REST request to save SupportedRelationData : {}", supportedRelationDataDTO);
        if (supportedRelationDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new supportedRelationData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupportedRelationDataDTO result = supportedRelationDataService.save(supportedRelationDataDTO);
        return ResponseEntity
            .created(new URI("/api/supported-relation-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supported-relation-data} : Updates an existing supportedRelationData.
     *
     * @param supportedRelationDataDTO the supportedRelationDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supportedRelationDataDTO,
     * or with status {@code 400 (Bad Request)} if the supportedRelationDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supportedRelationDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supported-relation-data")
    public ResponseEntity<SupportedRelationDataDTO> updateSupportedRelationData(
        @Valid @RequestBody SupportedRelationDataDTO supportedRelationDataDTO
    )
        throws URISyntaxException {
        log.debug("REST request to update SupportedRelationData : {}", supportedRelationDataDTO);
        if (supportedRelationDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupportedRelationDataDTO result = supportedRelationDataService.save(supportedRelationDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, supportedRelationDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /supported-relation-data} : get all the supportedRelationData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supportedRelationData in body.
     */
    @GetMapping("/supported-relation-data")
    public ResponseEntity<List<SupportedRelationDataDTO>> getAllSupportedRelationData(Pageable pageable) {
        log.debug("REST request to get a page of SupportedRelationData");
        Page<SupportedRelationDataDTO> page = supportedRelationDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /supported-relation-data/:id} : get the "id" supportedRelationData.
     *
     * @param id the id of the supportedRelationDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supportedRelationDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supported-relation-data/{id}")
    public ResponseEntity<SupportedRelationDataDTO> getSupportedRelationData(@PathVariable Long id) {
        log.debug("REST request to get SupportedRelationData : {}", id);
        Optional<SupportedRelationDataDTO> supportedRelationDataDTO = supportedRelationDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supportedRelationDataDTO);
    }

    /**
     * {@code DELETE  /supported-relation-data/:id} : delete the "id" supportedRelationData.
     *
     * @param id the id of the supportedRelationDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supported-relation-data/{id}")
    public ResponseEntity<Void> deleteSupportedRelationData(@PathVariable Long id) {
        log.debug("REST request to delete SupportedRelationData : {}", id);
        supportedRelationDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/supported-relation-data?query=:query} : search for the supportedRelationData corresponding
     * to the query.
     *
     * @param query the query of the supportedRelationData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/supported-relation-data")
    public ResponseEntity<List<SupportedRelationDataDTO>> searchSupportedRelationData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SupportedRelationData for query {}", query);
        Page<SupportedRelationDataDTO> page = supportedRelationDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
