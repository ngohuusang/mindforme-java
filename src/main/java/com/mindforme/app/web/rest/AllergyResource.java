package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.AllergyService;
import com.mindforme.app.service.dto.AllergyDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.Allergy}.
 */
@RestController
@RequestMapping("/api")
public class AllergyResource {
    private final Logger log = LoggerFactory.getLogger(AllergyResource.class);

    private static final String ENTITY_NAME = "allergy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AllergyService allergyService;

    public AllergyResource(AllergyService allergyService) {
        this.allergyService = allergyService;
    }

    /**
     * {@code POST  /allergies} : Create a new allergy.
     *
     * @param allergyDTO the allergyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new allergyDTO, or with status {@code 400 (Bad Request)} if the allergy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/allergies")
    public ResponseEntity<AllergyDTO> createAllergy(@RequestBody AllergyDTO allergyDTO) throws URISyntaxException {
        log.debug("REST request to save Allergy : {}", allergyDTO);
        if (allergyDTO.getId() != null) {
            throw new BadRequestAlertException("A new allergy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AllergyDTO result = allergyService.save(allergyDTO);
        return ResponseEntity
            .created(new URI("/api/allergies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /allergies} : Updates an existing allergy.
     *
     * @param allergyDTO the allergyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated allergyDTO,
     * or with status {@code 400 (Bad Request)} if the allergyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the allergyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/allergies")
    public ResponseEntity<AllergyDTO> updateAllergy(@RequestBody AllergyDTO allergyDTO) throws URISyntaxException {
        log.debug("REST request to update Allergy : {}", allergyDTO);
        if (allergyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AllergyDTO result = allergyService.save(allergyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, allergyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /allergies} : get all the allergies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of allergies in body.
     */
    @GetMapping("/allergies")
    public ResponseEntity<List<AllergyDTO>> getAllAllergies(Pageable pageable) {
        log.debug("REST request to get a page of Allergies");
        Page<AllergyDTO> page = allergyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /allergies/:id} : get the "id" allergy.
     *
     * @param id the id of the allergyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the allergyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/allergies/{id}")
    public ResponseEntity<AllergyDTO> getAllergy(@PathVariable Long id) {
        log.debug("REST request to get Allergy : {}", id);
        Optional<AllergyDTO> allergyDTO = allergyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(allergyDTO);
    }

    /**
     * {@code DELETE  /allergies/:id} : delete the "id" allergy.
     *
     * @param id the id of the allergyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/allergies/{id}")
    public ResponseEntity<Void> deleteAllergy(@PathVariable Long id) {
        log.debug("REST request to delete Allergy : {}", id);
        allergyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/allergies?query=:query} : search for the allergy corresponding
     * to the query.
     *
     * @param query the query of the allergy search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/allergies")
    public ResponseEntity<List<AllergyDTO>> searchAllergies(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Allergies for query {}", query);
        Page<AllergyDTO> page = allergyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
