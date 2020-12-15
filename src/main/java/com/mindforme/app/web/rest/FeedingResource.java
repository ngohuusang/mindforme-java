package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.FeedingService;
import com.mindforme.app.service.dto.FeedingDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.Feeding}.
 */
@RestController
@RequestMapping("/api")
public class FeedingResource {
    private final Logger log = LoggerFactory.getLogger(FeedingResource.class);

    private static final String ENTITY_NAME = "feeding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedingService feedingService;

    public FeedingResource(FeedingService feedingService) {
        this.feedingService = feedingService;
    }

    /**
     * {@code POST  /feedings} : Create a new feeding.
     *
     * @param feedingDTO the feedingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedingDTO, or with status {@code 400 (Bad Request)} if the feeding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feedings")
    public ResponseEntity<FeedingDTO> createFeeding(@RequestBody FeedingDTO feedingDTO) throws URISyntaxException {
        log.debug("REST request to save Feeding : {}", feedingDTO);
        if (feedingDTO.getId() != null) {
            throw new BadRequestAlertException("A new feeding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeedingDTO result = feedingService.save(feedingDTO);
        return ResponseEntity
            .created(new URI("/api/feedings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feedings} : Updates an existing feeding.
     *
     * @param feedingDTO the feedingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedingDTO,
     * or with status {@code 400 (Bad Request)} if the feedingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feedingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feedings")
    public ResponseEntity<FeedingDTO> updateFeeding(@RequestBody FeedingDTO feedingDTO) throws URISyntaxException {
        log.debug("REST request to update Feeding : {}", feedingDTO);
        if (feedingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FeedingDTO result = feedingService.save(feedingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feedingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /feedings} : get all the feedings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedings in body.
     */
    @GetMapping("/feedings")
    public ResponseEntity<List<FeedingDTO>> getAllFeedings(Pageable pageable) {
        log.debug("REST request to get a page of Feedings");
        Page<FeedingDTO> page = feedingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /feedings/:id} : get the "id" feeding.
     *
     * @param id the id of the feedingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feedings/{id}")
    public ResponseEntity<FeedingDTO> getFeeding(@PathVariable Long id) {
        log.debug("REST request to get Feeding : {}", id);
        Optional<FeedingDTO> feedingDTO = feedingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feedingDTO);
    }

    /**
     * {@code DELETE  /feedings/:id} : delete the "id" feeding.
     *
     * @param id the id of the feedingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feedings/{id}")
    public ResponseEntity<Void> deleteFeeding(@PathVariable Long id) {
        log.debug("REST request to delete Feeding : {}", id);
        feedingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/feedings?query=:query} : search for the feeding corresponding
     * to the query.
     *
     * @param query the query of the feeding search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/feedings")
    public ResponseEntity<List<FeedingDTO>> searchFeedings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Feedings for query {}", query);
        Page<FeedingDTO> page = feedingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
