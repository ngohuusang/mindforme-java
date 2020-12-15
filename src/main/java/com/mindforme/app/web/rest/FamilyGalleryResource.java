package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.FamilyGalleryService;
import com.mindforme.app.service.dto.FamilyGalleryDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.FamilyGallery}.
 */
@RestController
@RequestMapping("/api")
public class FamilyGalleryResource {
    private final Logger log = LoggerFactory.getLogger(FamilyGalleryResource.class);

    private static final String ENTITY_NAME = "familyGallery";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FamilyGalleryService familyGalleryService;

    public FamilyGalleryResource(FamilyGalleryService familyGalleryService) {
        this.familyGalleryService = familyGalleryService;
    }

    /**
     * {@code POST  /family-galleries} : Create a new familyGallery.
     *
     * @param familyGalleryDTO the familyGalleryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new familyGalleryDTO, or with status {@code 400 (Bad Request)} if the familyGallery has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/family-galleries")
    public ResponseEntity<FamilyGalleryDTO> createFamilyGallery(@RequestBody FamilyGalleryDTO familyGalleryDTO) throws URISyntaxException {
        log.debug("REST request to save FamilyGallery : {}", familyGalleryDTO);
        if (familyGalleryDTO.getId() != null) {
            throw new BadRequestAlertException("A new familyGallery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FamilyGalleryDTO result = familyGalleryService.save(familyGalleryDTO);
        return ResponseEntity
            .created(new URI("/api/family-galleries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /family-galleries} : Updates an existing familyGallery.
     *
     * @param familyGalleryDTO the familyGalleryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated familyGalleryDTO,
     * or with status {@code 400 (Bad Request)} if the familyGalleryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the familyGalleryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/family-galleries")
    public ResponseEntity<FamilyGalleryDTO> updateFamilyGallery(@RequestBody FamilyGalleryDTO familyGalleryDTO) throws URISyntaxException {
        log.debug("REST request to update FamilyGallery : {}", familyGalleryDTO);
        if (familyGalleryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FamilyGalleryDTO result = familyGalleryService.save(familyGalleryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, familyGalleryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /family-galleries} : get all the familyGalleries.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of familyGalleries in body.
     */
    @GetMapping("/family-galleries")
    public ResponseEntity<List<FamilyGalleryDTO>> getAllFamilyGalleries(Pageable pageable) {
        log.debug("REST request to get a page of FamilyGalleries");
        Page<FamilyGalleryDTO> page = familyGalleryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /family-galleries/:id} : get the "id" familyGallery.
     *
     * @param id the id of the familyGalleryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the familyGalleryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/family-galleries/{id}")
    public ResponseEntity<FamilyGalleryDTO> getFamilyGallery(@PathVariable Long id) {
        log.debug("REST request to get FamilyGallery : {}", id);
        Optional<FamilyGalleryDTO> familyGalleryDTO = familyGalleryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(familyGalleryDTO);
    }

    /**
     * {@code DELETE  /family-galleries/:id} : delete the "id" familyGallery.
     *
     * @param id the id of the familyGalleryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/family-galleries/{id}")
    public ResponseEntity<Void> deleteFamilyGallery(@PathVariable Long id) {
        log.debug("REST request to delete FamilyGallery : {}", id);
        familyGalleryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/family-galleries?query=:query} : search for the familyGallery corresponding
     * to the query.
     *
     * @param query the query of the familyGallery search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/family-galleries")
    public ResponseEntity<List<FamilyGalleryDTO>> searchFamilyGalleries(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FamilyGalleries for query {}", query);
        Page<FamilyGalleryDTO> page = familyGalleryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
