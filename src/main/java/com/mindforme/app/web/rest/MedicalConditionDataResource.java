package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.MedicalConditionDataService;
import com.mindforme.app.service.dto.MedicalConditionDataDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.MedicalConditionData}.
 */
@RestController
@RequestMapping("/api")
public class MedicalConditionDataResource {
    private final Logger log = LoggerFactory.getLogger(MedicalConditionDataResource.class);

    private static final String ENTITY_NAME = "medicalConditionData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicalConditionDataService medicalConditionDataService;

    public MedicalConditionDataResource(MedicalConditionDataService medicalConditionDataService) {
        this.medicalConditionDataService = medicalConditionDataService;
    }

    /**
     * {@code POST  /medical-condition-data} : Create a new medicalConditionData.
     *
     * @param medicalConditionDataDTO the medicalConditionDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicalConditionDataDTO, or with status {@code 400 (Bad Request)} if the medicalConditionData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medical-condition-data")
    public ResponseEntity<MedicalConditionDataDTO> createMedicalConditionData(
        @Valid @RequestBody MedicalConditionDataDTO medicalConditionDataDTO
    )
        throws URISyntaxException {
        log.debug("REST request to save MedicalConditionData : {}", medicalConditionDataDTO);
        if (medicalConditionDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicalConditionData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalConditionDataDTO result = medicalConditionDataService.save(medicalConditionDataDTO);
        return ResponseEntity
            .created(new URI("/api/medical-condition-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medical-condition-data} : Updates an existing medicalConditionData.
     *
     * @param medicalConditionDataDTO the medicalConditionDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicalConditionDataDTO,
     * or with status {@code 400 (Bad Request)} if the medicalConditionDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicalConditionDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medical-condition-data")
    public ResponseEntity<MedicalConditionDataDTO> updateMedicalConditionData(
        @Valid @RequestBody MedicalConditionDataDTO medicalConditionDataDTO
    )
        throws URISyntaxException {
        log.debug("REST request to update MedicalConditionData : {}", medicalConditionDataDTO);
        if (medicalConditionDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalConditionDataDTO result = medicalConditionDataService.save(medicalConditionDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, medicalConditionDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medical-condition-data} : get all the medicalConditionData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicalConditionData in body.
     */
    @GetMapping("/medical-condition-data")
    public ResponseEntity<List<MedicalConditionDataDTO>> getAllMedicalConditionData(Pageable pageable) {
        log.debug("REST request to get a page of MedicalConditionData");
        Page<MedicalConditionDataDTO> page = medicalConditionDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medical-condition-data/:id} : get the "id" medicalConditionData.
     *
     * @param id the id of the medicalConditionDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicalConditionDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medical-condition-data/{id}")
    public ResponseEntity<MedicalConditionDataDTO> getMedicalConditionData(@PathVariable Long id) {
        log.debug("REST request to get MedicalConditionData : {}", id);
        Optional<MedicalConditionDataDTO> medicalConditionDataDTO = medicalConditionDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalConditionDataDTO);
    }

    /**
     * {@code DELETE  /medical-condition-data/:id} : delete the "id" medicalConditionData.
     *
     * @param id the id of the medicalConditionDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medical-condition-data/{id}")
    public ResponseEntity<Void> deleteMedicalConditionData(@PathVariable Long id) {
        log.debug("REST request to delete MedicalConditionData : {}", id);
        medicalConditionDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/medical-condition-data?query=:query} : search for the medicalConditionData corresponding
     * to the query.
     *
     * @param query the query of the medicalConditionData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/medical-condition-data")
    public ResponseEntity<List<MedicalConditionDataDTO>> searchMedicalConditionData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MedicalConditionData for query {}", query);
        Page<MedicalConditionDataDTO> page = medicalConditionDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
