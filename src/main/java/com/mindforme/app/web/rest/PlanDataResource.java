package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.PlanDataService;
import com.mindforme.app.service.dto.PlanDataDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.PlanData}.
 */
@RestController
@RequestMapping("/api")
public class PlanDataResource {
    private final Logger log = LoggerFactory.getLogger(PlanDataResource.class);

    private static final String ENTITY_NAME = "planData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanDataService planDataService;

    public PlanDataResource(PlanDataService planDataService) {
        this.planDataService = planDataService;
    }

    /**
     * {@code POST  /plan-data} : Create a new planData.
     *
     * @param planDataDTO the planDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planDataDTO, or with status {@code 400 (Bad Request)} if the planData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plan-data")
    public ResponseEntity<PlanDataDTO> createPlanData(@Valid @RequestBody PlanDataDTO planDataDTO) throws URISyntaxException {
        log.debug("REST request to save PlanData : {}", planDataDTO);
        if (planDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new planData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanDataDTO result = planDataService.save(planDataDTO);
        return ResponseEntity
            .created(new URI("/api/plan-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plan-data} : Updates an existing planData.
     *
     * @param planDataDTO the planDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planDataDTO,
     * or with status {@code 400 (Bad Request)} if the planDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plan-data")
    public ResponseEntity<PlanDataDTO> updatePlanData(@Valid @RequestBody PlanDataDTO planDataDTO) throws URISyntaxException {
        log.debug("REST request to update PlanData : {}", planDataDTO);
        if (planDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlanDataDTO result = planDataService.save(planDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, planDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plan-data} : get all the planData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planData in body.
     */
    @GetMapping("/plan-data")
    public ResponseEntity<List<PlanDataDTO>> getAllPlanData(Pageable pageable) {
        log.debug("REST request to get a page of PlanData");
        Page<PlanDataDTO> page = planDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plan-data/:id} : get the "id" planData.
     *
     * @param id the id of the planDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plan-data/{id}")
    public ResponseEntity<PlanDataDTO> getPlanData(@PathVariable Long id) {
        log.debug("REST request to get PlanData : {}", id);
        Optional<PlanDataDTO> planDataDTO = planDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planDataDTO);
    }

    /**
     * {@code DELETE  /plan-data/:id} : delete the "id" planData.
     *
     * @param id the id of the planDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plan-data/{id}")
    public ResponseEntity<Void> deletePlanData(@PathVariable Long id) {
        log.debug("REST request to delete PlanData : {}", id);
        planDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/plan-data?query=:query} : search for the planData corresponding
     * to the query.
     *
     * @param query the query of the planData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/plan-data")
    public ResponseEntity<List<PlanDataDTO>> searchPlanData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PlanData for query {}", query);
        Page<PlanDataDTO> page = planDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
