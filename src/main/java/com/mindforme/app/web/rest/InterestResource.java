package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.InterestService;
import com.mindforme.app.service.dto.InterestDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.Interest}.
 */
@RestController
@RequestMapping("/api")
public class InterestResource {
    private final Logger log = LoggerFactory.getLogger(InterestResource.class);

    private static final String ENTITY_NAME = "interest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InterestService interestService;

    public InterestResource(InterestService interestService) {
        this.interestService = interestService;
    }

    /**
     * {@code POST  /interests} : Create a new interest.
     *
     * @param interestDTO the interestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interestDTO, or with status {@code 400 (Bad Request)} if the interest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/interests")
    public ResponseEntity<InterestDTO> createInterest(@RequestBody InterestDTO interestDTO) throws URISyntaxException {
        log.debug("REST request to save Interest : {}", interestDTO);
        if (interestDTO.getId() != null) {
            throw new BadRequestAlertException("A new interest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InterestDTO result = interestService.save(interestDTO);
        return ResponseEntity
            .created(new URI("/api/interests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /interests} : Updates an existing interest.
     *
     * @param interestDTO the interestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interestDTO,
     * or with status {@code 400 (Bad Request)} if the interestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/interests")
    public ResponseEntity<InterestDTO> updateInterest(@RequestBody InterestDTO interestDTO) throws URISyntaxException {
        log.debug("REST request to update Interest : {}", interestDTO);
        if (interestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InterestDTO result = interestService.save(interestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, interestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /interests} : get all the interests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interests in body.
     */
    @GetMapping("/interests")
    public ResponseEntity<List<InterestDTO>> getAllInterests(Pageable pageable) {
        log.debug("REST request to get a page of Interests");
        Page<InterestDTO> page = interestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /interests/:id} : get the "id" interest.
     *
     * @param id the id of the interestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/interests/{id}")
    public ResponseEntity<InterestDTO> getInterest(@PathVariable Long id) {
        log.debug("REST request to get Interest : {}", id);
        Optional<InterestDTO> interestDTO = interestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(interestDTO);
    }

    /**
     * {@code DELETE  /interests/:id} : delete the "id" interest.
     *
     * @param id the id of the interestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/interests/{id}")
    public ResponseEntity<Void> deleteInterest(@PathVariable Long id) {
        log.debug("REST request to delete Interest : {}", id);
        interestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/interests?query=:query} : search for the interest corresponding
     * to the query.
     *
     * @param query the query of the interest search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/interests")
    public ResponseEntity<List<InterestDTO>> searchInterests(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Interests for query {}", query);
        Page<InterestDTO> page = interestService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
