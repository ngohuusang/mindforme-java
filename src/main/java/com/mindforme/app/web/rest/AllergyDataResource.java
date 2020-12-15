package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.AllergyDataService;
import com.mindforme.app.service.dto.AllergyDataDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.AllergyData}.
 */
@RestController
@RequestMapping("/api")
public class AllergyDataResource {
    private final Logger log = LoggerFactory.getLogger(AllergyDataResource.class);

    private static final String ENTITY_NAME = "allergyData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AllergyDataService allergyDataService;

    public AllergyDataResource(AllergyDataService allergyDataService) {
        this.allergyDataService = allergyDataService;
    }

    /**
     * {@code POST  /allergy-data} : Create a new allergyData.
     *
     * @param allergyDataDTO the allergyDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new allergyDataDTO, or with status {@code 400 (Bad Request)} if the allergyData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/allergy-data")
    public ResponseEntity<AllergyDataDTO> createAllergyData(@Valid @RequestBody AllergyDataDTO allergyDataDTO) throws URISyntaxException {
        log.debug("REST request to save AllergyData : {}", allergyDataDTO);
        if (allergyDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new allergyData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AllergyDataDTO result = allergyDataService.save(allergyDataDTO);
        return ResponseEntity
            .created(new URI("/api/allergy-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /allergy-data} : Updates an existing allergyData.
     *
     * @param allergyDataDTO the allergyDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated allergyDataDTO,
     * or with status {@code 400 (Bad Request)} if the allergyDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the allergyDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/allergy-data")
    public ResponseEntity<AllergyDataDTO> updateAllergyData(@Valid @RequestBody AllergyDataDTO allergyDataDTO) throws URISyntaxException {
        log.debug("REST request to update AllergyData : {}", allergyDataDTO);
        if (allergyDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AllergyDataDTO result = allergyDataService.save(allergyDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, allergyDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /allergy-data} : get all the allergyData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of allergyData in body.
     */
    @GetMapping("/allergy-data")
    public ResponseEntity<List<AllergyDataDTO>> getAllAllergyData(Pageable pageable) {
        log.debug("REST request to get a page of AllergyData");
        Page<AllergyDataDTO> page = allergyDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /allergy-data/:id} : get the "id" allergyData.
     *
     * @param id the id of the allergyDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the allergyDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/allergy-data/{id}")
    public ResponseEntity<AllergyDataDTO> getAllergyData(@PathVariable Long id) {
        log.debug("REST request to get AllergyData : {}", id);
        Optional<AllergyDataDTO> allergyDataDTO = allergyDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(allergyDataDTO);
    }

    /**
     * {@code DELETE  /allergy-data/:id} : delete the "id" allergyData.
     *
     * @param id the id of the allergyDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/allergy-data/{id}")
    public ResponseEntity<Void> deleteAllergyData(@PathVariable Long id) {
        log.debug("REST request to delete AllergyData : {}", id);
        allergyDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/allergy-data?query=:query} : search for the allergyData corresponding
     * to the query.
     *
     * @param query the query of the allergyData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/allergy-data")
    public ResponseEntity<List<AllergyDataDTO>> searchAllergyData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AllergyData for query {}", query);
        Page<AllergyDataDTO> page = allergyDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
