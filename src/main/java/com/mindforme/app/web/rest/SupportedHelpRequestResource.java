package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.SupportedHelpRequestService;
import com.mindforme.app.service.dto.SupportedHelpRequestDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.SupportedHelpRequest}.
 */
@RestController
@RequestMapping("/api")
public class SupportedHelpRequestResource {
    private final Logger log = LoggerFactory.getLogger(SupportedHelpRequestResource.class);

    private static final String ENTITY_NAME = "supportedHelpRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupportedHelpRequestService supportedHelpRequestService;

    public SupportedHelpRequestResource(SupportedHelpRequestService supportedHelpRequestService) {
        this.supportedHelpRequestService = supportedHelpRequestService;
    }

    /**
     * {@code POST  /supported-help-requests} : Create a new supportedHelpRequest.
     *
     * @param supportedHelpRequestDTO the supportedHelpRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supportedHelpRequestDTO, or with status {@code 400 (Bad Request)} if the supportedHelpRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supported-help-requests")
    public ResponseEntity<SupportedHelpRequestDTO> createSupportedHelpRequest(
        @Valid @RequestBody SupportedHelpRequestDTO supportedHelpRequestDTO
    )
        throws URISyntaxException {
        log.debug("REST request to save SupportedHelpRequest : {}", supportedHelpRequestDTO);
        if (supportedHelpRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new supportedHelpRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupportedHelpRequestDTO result = supportedHelpRequestService.save(supportedHelpRequestDTO);
        return ResponseEntity
            .created(new URI("/api/supported-help-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supported-help-requests} : Updates an existing supportedHelpRequest.
     *
     * @param supportedHelpRequestDTO the supportedHelpRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supportedHelpRequestDTO,
     * or with status {@code 400 (Bad Request)} if the supportedHelpRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supportedHelpRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supported-help-requests")
    public ResponseEntity<SupportedHelpRequestDTO> updateSupportedHelpRequest(
        @Valid @RequestBody SupportedHelpRequestDTO supportedHelpRequestDTO
    )
        throws URISyntaxException {
        log.debug("REST request to update SupportedHelpRequest : {}", supportedHelpRequestDTO);
        if (supportedHelpRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupportedHelpRequestDTO result = supportedHelpRequestService.save(supportedHelpRequestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, supportedHelpRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /supported-help-requests} : get all the supportedHelpRequests.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supportedHelpRequests in body.
     */
    @GetMapping("/supported-help-requests")
    public ResponseEntity<List<SupportedHelpRequestDTO>> getAllSupportedHelpRequests(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of SupportedHelpRequests");
        Page<SupportedHelpRequestDTO> page;
        if (eagerload) {
            page = supportedHelpRequestService.findAllWithEagerRelationships(pageable);
        } else {
            page = supportedHelpRequestService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /supported-help-requests/:id} : get the "id" supportedHelpRequest.
     *
     * @param id the id of the supportedHelpRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supportedHelpRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supported-help-requests/{id}")
    public ResponseEntity<SupportedHelpRequestDTO> getSupportedHelpRequest(@PathVariable Long id) {
        log.debug("REST request to get SupportedHelpRequest : {}", id);
        Optional<SupportedHelpRequestDTO> supportedHelpRequestDTO = supportedHelpRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supportedHelpRequestDTO);
    }

    /**
     * {@code DELETE  /supported-help-requests/:id} : delete the "id" supportedHelpRequest.
     *
     * @param id the id of the supportedHelpRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supported-help-requests/{id}")
    public ResponseEntity<Void> deleteSupportedHelpRequest(@PathVariable Long id) {
        log.debug("REST request to delete SupportedHelpRequest : {}", id);
        supportedHelpRequestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/supported-help-requests?query=:query} : search for the supportedHelpRequest corresponding
     * to the query.
     *
     * @param query the query of the supportedHelpRequest search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/supported-help-requests")
    public ResponseEntity<List<SupportedHelpRequestDTO>> searchSupportedHelpRequests(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SupportedHelpRequests for query {}", query);
        Page<SupportedHelpRequestDTO> page = supportedHelpRequestService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
