package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.RuleDataService;
import com.mindforme.app.service.dto.RuleDataDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.RuleData}.
 */
@RestController
@RequestMapping("/api")
public class RuleDataResource {
    private final Logger log = LoggerFactory.getLogger(RuleDataResource.class);

    private static final String ENTITY_NAME = "ruleData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RuleDataService ruleDataService;

    public RuleDataResource(RuleDataService ruleDataService) {
        this.ruleDataService = ruleDataService;
    }

    /**
     * {@code POST  /rule-data} : Create a new ruleData.
     *
     * @param ruleDataDTO the ruleDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ruleDataDTO, or with status {@code 400 (Bad Request)} if the ruleData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rule-data")
    public ResponseEntity<RuleDataDTO> createRuleData(@Valid @RequestBody RuleDataDTO ruleDataDTO) throws URISyntaxException {
        log.debug("REST request to save RuleData : {}", ruleDataDTO);
        if (ruleDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new ruleData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RuleDataDTO result = ruleDataService.save(ruleDataDTO);
        return ResponseEntity
            .created(new URI("/api/rule-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rule-data} : Updates an existing ruleData.
     *
     * @param ruleDataDTO the ruleDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ruleDataDTO,
     * or with status {@code 400 (Bad Request)} if the ruleDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ruleDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rule-data")
    public ResponseEntity<RuleDataDTO> updateRuleData(@Valid @RequestBody RuleDataDTO ruleDataDTO) throws URISyntaxException {
        log.debug("REST request to update RuleData : {}", ruleDataDTO);
        if (ruleDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RuleDataDTO result = ruleDataService.save(ruleDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ruleDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rule-data} : get all the ruleData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ruleData in body.
     */
    @GetMapping("/rule-data")
    public ResponseEntity<List<RuleDataDTO>> getAllRuleData(Pageable pageable) {
        log.debug("REST request to get a page of RuleData");
        Page<RuleDataDTO> page = ruleDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rule-data/:id} : get the "id" ruleData.
     *
     * @param id the id of the ruleDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ruleDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rule-data/{id}")
    public ResponseEntity<RuleDataDTO> getRuleData(@PathVariable Long id) {
        log.debug("REST request to get RuleData : {}", id);
        Optional<RuleDataDTO> ruleDataDTO = ruleDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ruleDataDTO);
    }

    /**
     * {@code DELETE  /rule-data/:id} : delete the "id" ruleData.
     *
     * @param id the id of the ruleDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rule-data/{id}")
    public ResponseEntity<Void> deleteRuleData(@PathVariable Long id) {
        log.debug("REST request to delete RuleData : {}", id);
        ruleDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/rule-data?query=:query} : search for the ruleData corresponding
     * to the query.
     *
     * @param query the query of the ruleData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/rule-data")
    public ResponseEntity<List<RuleDataDTO>> searchRuleData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RuleData for query {}", query);
        Page<RuleDataDTO> page = ruleDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
