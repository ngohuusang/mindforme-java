package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.WorkingWithChildrenService;
import com.mindforme.app.service.dto.WorkingWithChildrenDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.WorkingWithChildren}.
 */
@RestController
@RequestMapping("/api")
public class WorkingWithChildrenResource {
    private final Logger log = LoggerFactory.getLogger(WorkingWithChildrenResource.class);

    private static final String ENTITY_NAME = "workingWithChildren";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkingWithChildrenService workingWithChildrenService;

    public WorkingWithChildrenResource(WorkingWithChildrenService workingWithChildrenService) {
        this.workingWithChildrenService = workingWithChildrenService;
    }

    /**
     * {@code POST  /working-with-children} : Create a new workingWithChildren.
     *
     * @param workingWithChildrenDTO the workingWithChildrenDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workingWithChildrenDTO, or with status {@code 400 (Bad Request)} if the workingWithChildren has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/working-with-children")
    public ResponseEntity<WorkingWithChildrenDTO> createWorkingWithChildren(@RequestBody WorkingWithChildrenDTO workingWithChildrenDTO)
        throws URISyntaxException {
        log.debug("REST request to save WorkingWithChildren : {}", workingWithChildrenDTO);
        if (workingWithChildrenDTO.getId() != null) {
            throw new BadRequestAlertException("A new workingWithChildren cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkingWithChildrenDTO result = workingWithChildrenService.save(workingWithChildrenDTO);
        return ResponseEntity
            .created(new URI("/api/working-with-children/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /working-with-children} : Updates an existing workingWithChildren.
     *
     * @param workingWithChildrenDTO the workingWithChildrenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workingWithChildrenDTO,
     * or with status {@code 400 (Bad Request)} if the workingWithChildrenDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workingWithChildrenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/working-with-children")
    public ResponseEntity<WorkingWithChildrenDTO> updateWorkingWithChildren(@RequestBody WorkingWithChildrenDTO workingWithChildrenDTO)
        throws URISyntaxException {
        log.debug("REST request to update WorkingWithChildren : {}", workingWithChildrenDTO);
        if (workingWithChildrenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkingWithChildrenDTO result = workingWithChildrenService.save(workingWithChildrenDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, workingWithChildrenDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /working-with-children} : get all the workingWithChildren.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workingWithChildren in body.
     */
    @GetMapping("/working-with-children")
    public ResponseEntity<List<WorkingWithChildrenDTO>> getAllWorkingWithChildren(Pageable pageable) {
        log.debug("REST request to get a page of WorkingWithChildren");
        Page<WorkingWithChildrenDTO> page = workingWithChildrenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /working-with-children/:id} : get the "id" workingWithChildren.
     *
     * @param id the id of the workingWithChildrenDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workingWithChildrenDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/working-with-children/{id}")
    public ResponseEntity<WorkingWithChildrenDTO> getWorkingWithChildren(@PathVariable Long id) {
        log.debug("REST request to get WorkingWithChildren : {}", id);
        Optional<WorkingWithChildrenDTO> workingWithChildrenDTO = workingWithChildrenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workingWithChildrenDTO);
    }

    /**
     * {@code DELETE  /working-with-children/:id} : delete the "id" workingWithChildren.
     *
     * @param id the id of the workingWithChildrenDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/working-with-children/{id}")
    public ResponseEntity<Void> deleteWorkingWithChildren(@PathVariable Long id) {
        log.debug("REST request to delete WorkingWithChildren : {}", id);
        workingWithChildrenService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/working-with-children?query=:query} : search for the workingWithChildren corresponding
     * to the query.
     *
     * @param query the query of the workingWithChildren search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/working-with-children")
    public ResponseEntity<List<WorkingWithChildrenDTO>> searchWorkingWithChildren(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of WorkingWithChildren for query {}", query);
        Page<WorkingWithChildrenDTO> page = workingWithChildrenService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
