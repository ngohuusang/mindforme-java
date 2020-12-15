package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.ChildService;
import com.mindforme.app.service.dto.ChildDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.Child}.
 */
@RestController
@RequestMapping("/api")
public class ChildResource {
    private final Logger log = LoggerFactory.getLogger(ChildResource.class);

    private static final String ENTITY_NAME = "child";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChildService childService;

    public ChildResource(ChildService childService) {
        this.childService = childService;
    }

    /**
     * {@code POST  /children} : Create a new child.
     *
     * @param childDTO the childDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new childDTO, or with status {@code 400 (Bad Request)} if the child has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/children")
    public ResponseEntity<ChildDTO> createChild(@RequestBody ChildDTO childDTO) throws URISyntaxException {
        log.debug("REST request to save Child : {}", childDTO);
        if (childDTO.getId() != null) {
            throw new BadRequestAlertException("A new child cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChildDTO result = childService.save(childDTO);
        return ResponseEntity
            .created(new URI("/api/children/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /children} : Updates an existing child.
     *
     * @param childDTO the childDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated childDTO,
     * or with status {@code 400 (Bad Request)} if the childDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the childDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/children")
    public ResponseEntity<ChildDTO> updateChild(@RequestBody ChildDTO childDTO) throws URISyntaxException {
        log.debug("REST request to update Child : {}", childDTO);
        if (childDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChildDTO result = childService.save(childDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, childDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /children} : get all the children.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of children in body.
     */
    @GetMapping("/children")
    public ResponseEntity<List<ChildDTO>> getAllChildren(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Children");
        Page<ChildDTO> page;
        if (eagerload) {
            page = childService.findAllWithEagerRelationships(pageable);
        } else {
            page = childService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /children/:id} : get the "id" child.
     *
     * @param id the id of the childDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the childDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/children/{id}")
    public ResponseEntity<ChildDTO> getChild(@PathVariable Long id) {
        log.debug("REST request to get Child : {}", id);
        Optional<ChildDTO> childDTO = childService.findOne(id);
        return ResponseUtil.wrapOrNotFound(childDTO);
    }

    /**
     * {@code DELETE  /children/:id} : delete the "id" child.
     *
     * @param id the id of the childDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/children/{id}")
    public ResponseEntity<Void> deleteChild(@PathVariable Long id) {
        log.debug("REST request to delete Child : {}", id);
        childService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/children?query=:query} : search for the child corresponding
     * to the query.
     *
     * @param query the query of the child search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/children")
    public ResponseEntity<List<ChildDTO>> searchChildren(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Children for query {}", query);
        Page<ChildDTO> page = childService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
