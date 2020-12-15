package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.InterestDataService;
import com.mindforme.app.service.dto.InterestDataDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.InterestData}.
 */
@RestController
@RequestMapping("/api")
public class InterestDataResource {
    private final Logger log = LoggerFactory.getLogger(InterestDataResource.class);

    private static final String ENTITY_NAME = "interestData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InterestDataService interestDataService;

    public InterestDataResource(InterestDataService interestDataService) {
        this.interestDataService = interestDataService;
    }

    /**
     * {@code POST  /interest-data} : Create a new interestData.
     *
     * @param interestDataDTO the interestDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interestDataDTO, or with status {@code 400 (Bad Request)} if the interestData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/interest-data")
    public ResponseEntity<InterestDataDTO> createInterestData(@Valid @RequestBody InterestDataDTO interestDataDTO)
        throws URISyntaxException {
        log.debug("REST request to save InterestData : {}", interestDataDTO);
        if (interestDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new interestData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InterestDataDTO result = interestDataService.save(interestDataDTO);
        return ResponseEntity
            .created(new URI("/api/interest-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /interest-data} : Updates an existing interestData.
     *
     * @param interestDataDTO the interestDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interestDataDTO,
     * or with status {@code 400 (Bad Request)} if the interestDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interestDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/interest-data")
    public ResponseEntity<InterestDataDTO> updateInterestData(@Valid @RequestBody InterestDataDTO interestDataDTO)
        throws URISyntaxException {
        log.debug("REST request to update InterestData : {}", interestDataDTO);
        if (interestDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InterestDataDTO result = interestDataService.save(interestDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, interestDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /interest-data} : get all the interestData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interestData in body.
     */
    @GetMapping("/interest-data")
    public ResponseEntity<List<InterestDataDTO>> getAllInterestData(Pageable pageable) {
        log.debug("REST request to get a page of InterestData");
        Page<InterestDataDTO> page = interestDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /interest-data/:id} : get the "id" interestData.
     *
     * @param id the id of the interestDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interestDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/interest-data/{id}")
    public ResponseEntity<InterestDataDTO> getInterestData(@PathVariable Long id) {
        log.debug("REST request to get InterestData : {}", id);
        Optional<InterestDataDTO> interestDataDTO = interestDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(interestDataDTO);
    }

    /**
     * {@code DELETE  /interest-data/:id} : delete the "id" interestData.
     *
     * @param id the id of the interestDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/interest-data/{id}")
    public ResponseEntity<Void> deleteInterestData(@PathVariable Long id) {
        log.debug("REST request to delete InterestData : {}", id);
        interestDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/interest-data?query=:query} : search for the interestData corresponding
     * to the query.
     *
     * @param query the query of the interestData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/interest-data")
    public ResponseEntity<List<InterestDataDTO>> searchInterestData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of InterestData for query {}", query);
        Page<InterestDataDTO> page = interestDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
