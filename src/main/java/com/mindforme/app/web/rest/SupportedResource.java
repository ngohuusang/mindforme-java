package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.SupportedService;
import com.mindforme.app.service.dto.SupportedDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.Supported}.
 */
@RestController
@RequestMapping("/api")
public class SupportedResource {
    private final Logger log = LoggerFactory.getLogger(SupportedResource.class);

    private static final String ENTITY_NAME = "supported";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupportedService supportedService;

    public SupportedResource(SupportedService supportedService) {
        this.supportedService = supportedService;
    }

    /**
     * {@code POST  /supporteds} : Create a new supported.
     *
     * @param supportedDTO the supportedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supportedDTO, or with status {@code 400 (Bad Request)} if the supported has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supporteds")
    public ResponseEntity<SupportedDTO> createSupported(@RequestBody SupportedDTO supportedDTO) throws URISyntaxException {
        log.debug("REST request to save Supported : {}", supportedDTO);
        if (supportedDTO.getId() != null) {
            throw new BadRequestAlertException("A new supported cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupportedDTO result = supportedService.save(supportedDTO);
        return ResponseEntity
            .created(new URI("/api/supporteds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supporteds} : Updates an existing supported.
     *
     * @param supportedDTO the supportedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supportedDTO,
     * or with status {@code 400 (Bad Request)} if the supportedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supportedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supporteds")
    public ResponseEntity<SupportedDTO> updateSupported(@RequestBody SupportedDTO supportedDTO) throws URISyntaxException {
        log.debug("REST request to update Supported : {}", supportedDTO);
        if (supportedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupportedDTO result = supportedService.save(supportedDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, supportedDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /supporteds} : get all the supporteds.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supporteds in body.
     */
    @GetMapping("/supporteds")
    public ResponseEntity<List<SupportedDTO>> getAllSupporteds(Pageable pageable) {
        log.debug("REST request to get a page of Supporteds");
        Page<SupportedDTO> page = supportedService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /supporteds/:id} : get the "id" supported.
     *
     * @param id the id of the supportedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supportedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supporteds/{id}")
    public ResponseEntity<SupportedDTO> getSupported(@PathVariable Long id) {
        log.debug("REST request to get Supported : {}", id);
        Optional<SupportedDTO> supportedDTO = supportedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supportedDTO);
    }

    /**
     * {@code DELETE  /supporteds/:id} : delete the "id" supported.
     *
     * @param id the id of the supportedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supporteds/{id}")
    public ResponseEntity<Void> deleteSupported(@PathVariable Long id) {
        log.debug("REST request to delete Supported : {}", id);
        supportedService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/supporteds?query=:query} : search for the supported corresponding
     * to the query.
     *
     * @param query the query of the supported search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/supporteds")
    public ResponseEntity<List<SupportedDTO>> searchSupporteds(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Supporteds for query {}", query);
        Page<SupportedDTO> page = supportedService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
