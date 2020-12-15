package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.RuleService;
import com.mindforme.app.service.dto.RuleDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.Rule}.
 */
@RestController
@RequestMapping("/api")
public class RuleResource {
    private final Logger log = LoggerFactory.getLogger(RuleResource.class);

    private static final String ENTITY_NAME = "rule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RuleService ruleService;

    public RuleResource(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    /**
     * {@code POST  /rules} : Create a new rule.
     *
     * @param ruleDTO the ruleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ruleDTO, or with status {@code 400 (Bad Request)} if the rule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rules")
    public ResponseEntity<RuleDTO> createRule(@RequestBody RuleDTO ruleDTO) throws URISyntaxException {
        log.debug("REST request to save Rule : {}", ruleDTO);
        if (ruleDTO.getId() != null) {
            throw new BadRequestAlertException("A new rule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RuleDTO result = ruleService.save(ruleDTO);
        return ResponseEntity
            .created(new URI("/api/rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rules} : Updates an existing rule.
     *
     * @param ruleDTO the ruleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ruleDTO,
     * or with status {@code 400 (Bad Request)} if the ruleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ruleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rules")
    public ResponseEntity<RuleDTO> updateRule(@RequestBody RuleDTO ruleDTO) throws URISyntaxException {
        log.debug("REST request to update Rule : {}", ruleDTO);
        if (ruleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RuleDTO result = ruleService.save(ruleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ruleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rules} : get all the rules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rules in body.
     */
    @GetMapping("/rules")
    public ResponseEntity<List<RuleDTO>> getAllRules(Pageable pageable) {
        log.debug("REST request to get a page of Rules");
        Page<RuleDTO> page = ruleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rules/:id} : get the "id" rule.
     *
     * @param id the id of the ruleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ruleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rules/{id}")
    public ResponseEntity<RuleDTO> getRule(@PathVariable Long id) {
        log.debug("REST request to get Rule : {}", id);
        Optional<RuleDTO> ruleDTO = ruleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ruleDTO);
    }

    /**
     * {@code DELETE  /rules/:id} : delete the "id" rule.
     *
     * @param id the id of the ruleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rules/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        log.debug("REST request to delete Rule : {}", id);
        ruleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/rules?query=:query} : search for the rule corresponding
     * to the query.
     *
     * @param query the query of the rule search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/rules")
    public ResponseEntity<List<RuleDTO>> searchRules(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Rules for query {}", query);
        Page<RuleDTO> page = ruleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
