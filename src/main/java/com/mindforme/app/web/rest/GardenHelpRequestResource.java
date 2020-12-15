package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.GardenHelpRequestService;
import com.mindforme.app.service.dto.GardenHelpRequestDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.GardenHelpRequest}.
 */
@RestController
@RequestMapping("/api")
public class GardenHelpRequestResource {
    private final Logger log = LoggerFactory.getLogger(GardenHelpRequestResource.class);

    private static final String ENTITY_NAME = "gardenHelpRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GardenHelpRequestService gardenHelpRequestService;

    public GardenHelpRequestResource(GardenHelpRequestService gardenHelpRequestService) {
        this.gardenHelpRequestService = gardenHelpRequestService;
    }

    /**
     * {@code POST  /garden-help-requests} : Create a new gardenHelpRequest.
     *
     * @param gardenHelpRequestDTO the gardenHelpRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gardenHelpRequestDTO, or with status {@code 400 (Bad Request)} if the gardenHelpRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/garden-help-requests")
    public ResponseEntity<GardenHelpRequestDTO> createGardenHelpRequest(@Valid @RequestBody GardenHelpRequestDTO gardenHelpRequestDTO)
        throws URISyntaxException {
        log.debug("REST request to save GardenHelpRequest : {}", gardenHelpRequestDTO);
        if (gardenHelpRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new gardenHelpRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GardenHelpRequestDTO result = gardenHelpRequestService.save(gardenHelpRequestDTO);
        return ResponseEntity
            .created(new URI("/api/garden-help-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /garden-help-requests} : Updates an existing gardenHelpRequest.
     *
     * @param gardenHelpRequestDTO the gardenHelpRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gardenHelpRequestDTO,
     * or with status {@code 400 (Bad Request)} if the gardenHelpRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gardenHelpRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/garden-help-requests")
    public ResponseEntity<GardenHelpRequestDTO> updateGardenHelpRequest(@Valid @RequestBody GardenHelpRequestDTO gardenHelpRequestDTO)
        throws URISyntaxException {
        log.debug("REST request to update GardenHelpRequest : {}", gardenHelpRequestDTO);
        if (gardenHelpRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GardenHelpRequestDTO result = gardenHelpRequestService.save(gardenHelpRequestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gardenHelpRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /garden-help-requests} : get all the gardenHelpRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gardenHelpRequests in body.
     */
    @GetMapping("/garden-help-requests")
    public ResponseEntity<List<GardenHelpRequestDTO>> getAllGardenHelpRequests(Pageable pageable) {
        log.debug("REST request to get a page of GardenHelpRequests");
        Page<GardenHelpRequestDTO> page = gardenHelpRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /garden-help-requests/:id} : get the "id" gardenHelpRequest.
     *
     * @param id the id of the gardenHelpRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gardenHelpRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/garden-help-requests/{id}")
    public ResponseEntity<GardenHelpRequestDTO> getGardenHelpRequest(@PathVariable Long id) {
        log.debug("REST request to get GardenHelpRequest : {}", id);
        Optional<GardenHelpRequestDTO> gardenHelpRequestDTO = gardenHelpRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gardenHelpRequestDTO);
    }

    /**
     * {@code DELETE  /garden-help-requests/:id} : delete the "id" gardenHelpRequest.
     *
     * @param id the id of the gardenHelpRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/garden-help-requests/{id}")
    public ResponseEntity<Void> deleteGardenHelpRequest(@PathVariable Long id) {
        log.debug("REST request to delete GardenHelpRequest : {}", id);
        gardenHelpRequestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/garden-help-requests?query=:query} : search for the gardenHelpRequest corresponding
     * to the query.
     *
     * @param query the query of the gardenHelpRequest search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/garden-help-requests")
    public ResponseEntity<List<GardenHelpRequestDTO>> searchGardenHelpRequests(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GardenHelpRequests for query {}", query);
        Page<GardenHelpRequestDTO> page = gardenHelpRequestService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
