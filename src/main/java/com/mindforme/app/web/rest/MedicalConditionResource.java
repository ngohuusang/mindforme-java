package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.MedicalConditionService;
import com.mindforme.app.service.dto.MedicalConditionDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.MedicalCondition}.
 */
@RestController
@RequestMapping("/api")
public class MedicalConditionResource {
    private final Logger log = LoggerFactory.getLogger(MedicalConditionResource.class);

    private static final String ENTITY_NAME = "medicalCondition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicalConditionService medicalConditionService;

    public MedicalConditionResource(MedicalConditionService medicalConditionService) {
        this.medicalConditionService = medicalConditionService;
    }

    /**
     * {@code POST  /medical-conditions} : Create a new medicalCondition.
     *
     * @param medicalConditionDTO the medicalConditionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicalConditionDTO, or with status {@code 400 (Bad Request)} if the medicalCondition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medical-conditions")
    public ResponseEntity<MedicalConditionDTO> createMedicalCondition(@RequestBody MedicalConditionDTO medicalConditionDTO)
        throws URISyntaxException {
        log.debug("REST request to save MedicalCondition : {}", medicalConditionDTO);
        if (medicalConditionDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicalCondition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalConditionDTO result = medicalConditionService.save(medicalConditionDTO);
        return ResponseEntity
            .created(new URI("/api/medical-conditions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medical-conditions} : Updates an existing medicalCondition.
     *
     * @param medicalConditionDTO the medicalConditionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicalConditionDTO,
     * or with status {@code 400 (Bad Request)} if the medicalConditionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicalConditionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medical-conditions")
    public ResponseEntity<MedicalConditionDTO> updateMedicalCondition(@RequestBody MedicalConditionDTO medicalConditionDTO)
        throws URISyntaxException {
        log.debug("REST request to update MedicalCondition : {}", medicalConditionDTO);
        if (medicalConditionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalConditionDTO result = medicalConditionService.save(medicalConditionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, medicalConditionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medical-conditions} : get all the medicalConditions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicalConditions in body.
     */
    @GetMapping("/medical-conditions")
    public ResponseEntity<List<MedicalConditionDTO>> getAllMedicalConditions(Pageable pageable) {
        log.debug("REST request to get a page of MedicalConditions");
        Page<MedicalConditionDTO> page = medicalConditionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medical-conditions/:id} : get the "id" medicalCondition.
     *
     * @param id the id of the medicalConditionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicalConditionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medical-conditions/{id}")
    public ResponseEntity<MedicalConditionDTO> getMedicalCondition(@PathVariable Long id) {
        log.debug("REST request to get MedicalCondition : {}", id);
        Optional<MedicalConditionDTO> medicalConditionDTO = medicalConditionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalConditionDTO);
    }

    /**
     * {@code DELETE  /medical-conditions/:id} : delete the "id" medicalCondition.
     *
     * @param id the id of the medicalConditionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medical-conditions/{id}")
    public ResponseEntity<Void> deleteMedicalCondition(@PathVariable Long id) {
        log.debug("REST request to delete MedicalCondition : {}", id);
        medicalConditionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/medical-conditions?query=:query} : search for the medicalCondition corresponding
     * to the query.
     *
     * @param query the query of the medicalCondition search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/medical-conditions")
    public ResponseEntity<List<MedicalConditionDTO>> searchMedicalConditions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MedicalConditions for query {}", query);
        Page<MedicalConditionDTO> page = medicalConditionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
