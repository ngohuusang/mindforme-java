package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.StateDataService;
import com.mindforme.app.service.dto.StateDataDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.StateData}.
 */
@RestController
@RequestMapping("/api")
public class StateDataResource {
    private final Logger log = LoggerFactory.getLogger(StateDataResource.class);

    private static final String ENTITY_NAME = "stateData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StateDataService stateDataService;

    public StateDataResource(StateDataService stateDataService) {
        this.stateDataService = stateDataService;
    }

    /**
     * {@code POST  /state-data} : Create a new stateData.
     *
     * @param stateDataDTO the stateDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stateDataDTO, or with status {@code 400 (Bad Request)} if the stateData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/state-data")
    public ResponseEntity<StateDataDTO> createStateData(@Valid @RequestBody StateDataDTO stateDataDTO) throws URISyntaxException {
        log.debug("REST request to save StateData : {}", stateDataDTO);
        if (stateDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new stateData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StateDataDTO result = stateDataService.save(stateDataDTO);
        return ResponseEntity
            .created(new URI("/api/state-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /state-data} : Updates an existing stateData.
     *
     * @param stateDataDTO the stateDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stateDataDTO,
     * or with status {@code 400 (Bad Request)} if the stateDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stateDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/state-data")
    public ResponseEntity<StateDataDTO> updateStateData(@Valid @RequestBody StateDataDTO stateDataDTO) throws URISyntaxException {
        log.debug("REST request to update StateData : {}", stateDataDTO);
        if (stateDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StateDataDTO result = stateDataService.save(stateDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stateDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /state-data} : get all the stateData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stateData in body.
     */
    @GetMapping("/state-data")
    public ResponseEntity<List<StateDataDTO>> getAllStateData(Pageable pageable) {
        log.debug("REST request to get a page of StateData");
        Page<StateDataDTO> page = stateDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /state-data/:id} : get the "id" stateData.
     *
     * @param id the id of the stateDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stateDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/state-data/{id}")
    public ResponseEntity<StateDataDTO> getStateData(@PathVariable Long id) {
        log.debug("REST request to get StateData : {}", id);
        Optional<StateDataDTO> stateDataDTO = stateDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stateDataDTO);
    }

    /**
     * {@code DELETE  /state-data/:id} : delete the "id" stateData.
     *
     * @param id the id of the stateDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/state-data/{id}")
    public ResponseEntity<Void> deleteStateData(@PathVariable Long id) {
        log.debug("REST request to delete StateData : {}", id);
        stateDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/state-data?query=:query} : search for the stateData corresponding
     * to the query.
     *
     * @param query the query of the stateData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/state-data")
    public ResponseEntity<List<StateDataDTO>> searchStateData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StateData for query {}", query);
        Page<StateDataDTO> page = stateDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
