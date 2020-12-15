package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.HelpRequestService;
import com.mindforme.app.service.dto.HelpRequestDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.HelpRequest}.
 */
@RestController
@RequestMapping("/api")
public class HelpRequestResource {
    private final Logger log = LoggerFactory.getLogger(HelpRequestResource.class);

    private static final String ENTITY_NAME = "helpRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HelpRequestService helpRequestService;

    public HelpRequestResource(HelpRequestService helpRequestService) {
        this.helpRequestService = helpRequestService;
    }

    /**
     * {@code POST  /help-requests} : Create a new helpRequest.
     *
     * @param helpRequestDTO the helpRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new helpRequestDTO, or with status {@code 400 (Bad Request)} if the helpRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/help-requests")
    public ResponseEntity<HelpRequestDTO> createHelpRequest(@Valid @RequestBody HelpRequestDTO helpRequestDTO) throws URISyntaxException {
        log.debug("REST request to save HelpRequest : {}", helpRequestDTO);
        if (helpRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new helpRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HelpRequestDTO result = helpRequestService.save(helpRequestDTO);
        return ResponseEntity
            .created(new URI("/api/help-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /help-requests} : Updates an existing helpRequest.
     *
     * @param helpRequestDTO the helpRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated helpRequestDTO,
     * or with status {@code 400 (Bad Request)} if the helpRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the helpRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/help-requests")
    public ResponseEntity<HelpRequestDTO> updateHelpRequest(@Valid @RequestBody HelpRequestDTO helpRequestDTO) throws URISyntaxException {
        log.debug("REST request to update HelpRequest : {}", helpRequestDTO);
        if (helpRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HelpRequestDTO result = helpRequestService.save(helpRequestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, helpRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /help-requests} : get all the helpRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of helpRequests in body.
     */
    @GetMapping("/help-requests")
    public ResponseEntity<List<HelpRequestDTO>> getAllHelpRequests(Pageable pageable) {
        log.debug("REST request to get a page of HelpRequests");
        Page<HelpRequestDTO> page = helpRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /help-requests/:id} : get the "id" helpRequest.
     *
     * @param id the id of the helpRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the helpRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/help-requests/{id}")
    public ResponseEntity<HelpRequestDTO> getHelpRequest(@PathVariable Long id) {
        log.debug("REST request to get HelpRequest : {}", id);
        Optional<HelpRequestDTO> helpRequestDTO = helpRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(helpRequestDTO);
    }

    /**
     * {@code DELETE  /help-requests/:id} : delete the "id" helpRequest.
     *
     * @param id the id of the helpRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/help-requests/{id}")
    public ResponseEntity<Void> deleteHelpRequest(@PathVariable Long id) {
        log.debug("REST request to delete HelpRequest : {}", id);
        helpRequestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/help-requests?query=:query} : search for the helpRequest corresponding
     * to the query.
     *
     * @param query the query of the helpRequest search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/help-requests")
    public ResponseEntity<List<HelpRequestDTO>> searchHelpRequests(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of HelpRequests for query {}", query);
        Page<HelpRequestDTO> page = helpRequestService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
