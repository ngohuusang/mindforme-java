package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.ChildHelpRequestService;
import com.mindforme.app.service.dto.ChildHelpRequestDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.ChildHelpRequest}.
 */
@RestController
@RequestMapping("/api")
public class ChildHelpRequestResource {
    private final Logger log = LoggerFactory.getLogger(ChildHelpRequestResource.class);

    private static final String ENTITY_NAME = "childHelpRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChildHelpRequestService childHelpRequestService;

    public ChildHelpRequestResource(ChildHelpRequestService childHelpRequestService) {
        this.childHelpRequestService = childHelpRequestService;
    }

    /**
     * {@code POST  /child-help-requests} : Create a new childHelpRequest.
     *
     * @param childHelpRequestDTO the childHelpRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new childHelpRequestDTO, or with status {@code 400 (Bad Request)} if the childHelpRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/child-help-requests")
    public ResponseEntity<ChildHelpRequestDTO> createChildHelpRequest(@Valid @RequestBody ChildHelpRequestDTO childHelpRequestDTO)
        throws URISyntaxException {
        log.debug("REST request to save ChildHelpRequest : {}", childHelpRequestDTO);
        if (childHelpRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new childHelpRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChildHelpRequestDTO result = childHelpRequestService.save(childHelpRequestDTO);
        return ResponseEntity
            .created(new URI("/api/child-help-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /child-help-requests} : Updates an existing childHelpRequest.
     *
     * @param childHelpRequestDTO the childHelpRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated childHelpRequestDTO,
     * or with status {@code 400 (Bad Request)} if the childHelpRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the childHelpRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/child-help-requests")
    public ResponseEntity<ChildHelpRequestDTO> updateChildHelpRequest(@Valid @RequestBody ChildHelpRequestDTO childHelpRequestDTO)
        throws URISyntaxException {
        log.debug("REST request to update ChildHelpRequest : {}", childHelpRequestDTO);
        if (childHelpRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChildHelpRequestDTO result = childHelpRequestService.save(childHelpRequestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, childHelpRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /child-help-requests} : get all the childHelpRequests.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of childHelpRequests in body.
     */
    @GetMapping("/child-help-requests")
    public ResponseEntity<List<ChildHelpRequestDTO>> getAllChildHelpRequests(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of ChildHelpRequests");
        Page<ChildHelpRequestDTO> page;
        if (eagerload) {
            page = childHelpRequestService.findAllWithEagerRelationships(pageable);
        } else {
            page = childHelpRequestService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /child-help-requests/:id} : get the "id" childHelpRequest.
     *
     * @param id the id of the childHelpRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the childHelpRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/child-help-requests/{id}")
    public ResponseEntity<ChildHelpRequestDTO> getChildHelpRequest(@PathVariable Long id) {
        log.debug("REST request to get ChildHelpRequest : {}", id);
        Optional<ChildHelpRequestDTO> childHelpRequestDTO = childHelpRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(childHelpRequestDTO);
    }

    /**
     * {@code DELETE  /child-help-requests/:id} : delete the "id" childHelpRequest.
     *
     * @param id the id of the childHelpRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/child-help-requests/{id}")
    public ResponseEntity<Void> deleteChildHelpRequest(@PathVariable Long id) {
        log.debug("REST request to delete ChildHelpRequest : {}", id);
        childHelpRequestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/child-help-requests?query=:query} : search for the childHelpRequest corresponding
     * to the query.
     *
     * @param query the query of the childHelpRequest search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/child-help-requests")
    public ResponseEntity<List<ChildHelpRequestDTO>> searchChildHelpRequests(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ChildHelpRequests for query {}", query);
        Page<ChildHelpRequestDTO> page = childHelpRequestService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
