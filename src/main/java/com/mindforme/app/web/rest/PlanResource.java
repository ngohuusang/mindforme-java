package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.PlanService;
import com.mindforme.app.service.dto.PlanDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.Plan}.
 */
@RestController
@RequestMapping("/api")
public class PlanResource {
    private final Logger log = LoggerFactory.getLogger(PlanResource.class);

    private static final String ENTITY_NAME = "plan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanService planService;

    public PlanResource(PlanService planService) {
        this.planService = planService;
    }

    /**
     * {@code POST  /plans} : Create a new plan.
     *
     * @param planDTO the planDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planDTO, or with status {@code 400 (Bad Request)} if the plan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plans")
    public ResponseEntity<PlanDTO> createPlan(@Valid @RequestBody PlanDTO planDTO) throws URISyntaxException {
        log.debug("REST request to save Plan : {}", planDTO);
        if (planDTO.getId() != null) {
            throw new BadRequestAlertException("A new plan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanDTO result = planService.save(planDTO);
        return ResponseEntity
            .created(new URI("/api/plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plans} : Updates an existing plan.
     *
     * @param planDTO the planDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planDTO,
     * or with status {@code 400 (Bad Request)} if the planDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plans")
    public ResponseEntity<PlanDTO> updatePlan(@Valid @RequestBody PlanDTO planDTO) throws URISyntaxException {
        log.debug("REST request to update Plan : {}", planDTO);
        if (planDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlanDTO result = planService.save(planDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, planDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plans} : get all the plans.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plans in body.
     */
    @GetMapping("/plans")
    public ResponseEntity<List<PlanDTO>> getAllPlans(Pageable pageable) {
        log.debug("REST request to get a page of Plans");
        Page<PlanDTO> page = planService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plans/:id} : get the "id" plan.
     *
     * @param id the id of the planDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plans/{id}")
    public ResponseEntity<PlanDTO> getPlan(@PathVariable Long id) {
        log.debug("REST request to get Plan : {}", id);
        Optional<PlanDTO> planDTO = planService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planDTO);
    }

    /**
     * {@code DELETE  /plans/:id} : delete the "id" plan.
     *
     * @param id the id of the planDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plans/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        log.debug("REST request to delete Plan : {}", id);
        planService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/plans?query=:query} : search for the plan corresponding
     * to the query.
     *
     * @param query the query of the plan search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/plans")
    public ResponseEntity<List<PlanDTO>> searchPlans(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Plans for query {}", query);
        Page<PlanDTO> page = planService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
