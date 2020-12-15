package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.FeedingDataService;
import com.mindforme.app.service.dto.FeedingDataDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.FeedingData}.
 */
@RestController
@RequestMapping("/api")
public class FeedingDataResource {
    private final Logger log = LoggerFactory.getLogger(FeedingDataResource.class);

    private static final String ENTITY_NAME = "feedingData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedingDataService feedingDataService;

    public FeedingDataResource(FeedingDataService feedingDataService) {
        this.feedingDataService = feedingDataService;
    }

    /**
     * {@code POST  /feeding-data} : Create a new feedingData.
     *
     * @param feedingDataDTO the feedingDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedingDataDTO, or with status {@code 400 (Bad Request)} if the feedingData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feeding-data")
    public ResponseEntity<FeedingDataDTO> createFeedingData(@Valid @RequestBody FeedingDataDTO feedingDataDTO) throws URISyntaxException {
        log.debug("REST request to save FeedingData : {}", feedingDataDTO);
        if (feedingDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new feedingData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeedingDataDTO result = feedingDataService.save(feedingDataDTO);
        return ResponseEntity
            .created(new URI("/api/feeding-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feeding-data} : Updates an existing feedingData.
     *
     * @param feedingDataDTO the feedingDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedingDataDTO,
     * or with status {@code 400 (Bad Request)} if the feedingDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feedingDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feeding-data")
    public ResponseEntity<FeedingDataDTO> updateFeedingData(@Valid @RequestBody FeedingDataDTO feedingDataDTO) throws URISyntaxException {
        log.debug("REST request to update FeedingData : {}", feedingDataDTO);
        if (feedingDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FeedingDataDTO result = feedingDataService.save(feedingDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feedingDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /feeding-data} : get all the feedingData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedingData in body.
     */
    @GetMapping("/feeding-data")
    public ResponseEntity<List<FeedingDataDTO>> getAllFeedingData(Pageable pageable) {
        log.debug("REST request to get a page of FeedingData");
        Page<FeedingDataDTO> page = feedingDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /feeding-data/:id} : get the "id" feedingData.
     *
     * @param id the id of the feedingDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedingDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feeding-data/{id}")
    public ResponseEntity<FeedingDataDTO> getFeedingData(@PathVariable Long id) {
        log.debug("REST request to get FeedingData : {}", id);
        Optional<FeedingDataDTO> feedingDataDTO = feedingDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feedingDataDTO);
    }

    /**
     * {@code DELETE  /feeding-data/:id} : delete the "id" feedingData.
     *
     * @param id the id of the feedingDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feeding-data/{id}")
    public ResponseEntity<Void> deleteFeedingData(@PathVariable Long id) {
        log.debug("REST request to delete FeedingData : {}", id);
        feedingDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/feeding-data?query=:query} : search for the feedingData corresponding
     * to the query.
     *
     * @param query the query of the feedingData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/feeding-data")
    public ResponseEntity<List<FeedingDataDTO>> searchFeedingData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FeedingData for query {}", query);
        Page<FeedingDataDTO> page = feedingDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
