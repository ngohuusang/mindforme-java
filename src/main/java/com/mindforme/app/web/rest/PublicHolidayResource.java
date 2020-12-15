package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.PublicHolidayService;
import com.mindforme.app.service.dto.PublicHolidayDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.PublicHoliday}.
 */
@RestController
@RequestMapping("/api")
public class PublicHolidayResource {
    private final Logger log = LoggerFactory.getLogger(PublicHolidayResource.class);

    private static final String ENTITY_NAME = "publicHoliday";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublicHolidayService publicHolidayService;

    public PublicHolidayResource(PublicHolidayService publicHolidayService) {
        this.publicHolidayService = publicHolidayService;
    }

    /**
     * {@code POST  /public-holidays} : Create a new publicHoliday.
     *
     * @param publicHolidayDTO the publicHolidayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicHolidayDTO, or with status {@code 400 (Bad Request)} if the publicHoliday has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/public-holidays")
    public ResponseEntity<PublicHolidayDTO> createPublicHoliday(@RequestBody PublicHolidayDTO publicHolidayDTO) throws URISyntaxException {
        log.debug("REST request to save PublicHoliday : {}", publicHolidayDTO);
        if (publicHolidayDTO.getId() != null) {
            throw new BadRequestAlertException("A new publicHoliday cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PublicHolidayDTO result = publicHolidayService.save(publicHolidayDTO);
        return ResponseEntity
            .created(new URI("/api/public-holidays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /public-holidays} : Updates an existing publicHoliday.
     *
     * @param publicHolidayDTO the publicHolidayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicHolidayDTO,
     * or with status {@code 400 (Bad Request)} if the publicHolidayDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publicHolidayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/public-holidays")
    public ResponseEntity<PublicHolidayDTO> updatePublicHoliday(@RequestBody PublicHolidayDTO publicHolidayDTO) throws URISyntaxException {
        log.debug("REST request to update PublicHoliday : {}", publicHolidayDTO);
        if (publicHolidayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PublicHolidayDTO result = publicHolidayService.save(publicHolidayDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, publicHolidayDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /public-holidays} : get all the publicHolidays.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicHolidays in body.
     */
    @GetMapping("/public-holidays")
    public ResponseEntity<List<PublicHolidayDTO>> getAllPublicHolidays(Pageable pageable) {
        log.debug("REST request to get a page of PublicHolidays");
        Page<PublicHolidayDTO> page = publicHolidayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /public-holidays/:id} : get the "id" publicHoliday.
     *
     * @param id the id of the publicHolidayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicHolidayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/public-holidays/{id}")
    public ResponseEntity<PublicHolidayDTO> getPublicHoliday(@PathVariable Long id) {
        log.debug("REST request to get PublicHoliday : {}", id);
        Optional<PublicHolidayDTO> publicHolidayDTO = publicHolidayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(publicHolidayDTO);
    }

    /**
     * {@code DELETE  /public-holidays/:id} : delete the "id" publicHoliday.
     *
     * @param id the id of the publicHolidayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/public-holidays/{id}")
    public ResponseEntity<Void> deletePublicHoliday(@PathVariable Long id) {
        log.debug("REST request to delete PublicHoliday : {}", id);
        publicHolidayService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/public-holidays?query=:query} : search for the publicHoliday corresponding
     * to the query.
     *
     * @param query the query of the publicHoliday search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/public-holidays")
    public ResponseEntity<List<PublicHolidayDTO>> searchPublicHolidays(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PublicHolidays for query {}", query);
        Page<PublicHolidayDTO> page = publicHolidayService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
