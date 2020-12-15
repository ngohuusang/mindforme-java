package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.FamilyService;
import com.mindforme.app.service.dto.FamilyDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.Family}.
 */
@RestController
@RequestMapping("/api")
public class FamilyResource {
    private final Logger log = LoggerFactory.getLogger(FamilyResource.class);

    private static final String ENTITY_NAME = "family";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FamilyService familyService;

    public FamilyResource(FamilyService familyService) {
        this.familyService = familyService;
    }

    /**
     * {@code POST  /families} : Create a new family.
     *
     * @param familyDTO the familyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new familyDTO, or with status {@code 400 (Bad Request)} if the family has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/families")
    public ResponseEntity<FamilyDTO> createFamily(@Valid @RequestBody FamilyDTO familyDTO) throws URISyntaxException {
        log.debug("REST request to save Family : {}", familyDTO);
        if (familyDTO.getId() != null) {
            throw new BadRequestAlertException("A new family cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FamilyDTO result = familyService.save(familyDTO);
        return ResponseEntity
            .created(new URI("/api/families/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /families} : Updates an existing family.
     *
     * @param familyDTO the familyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated familyDTO,
     * or with status {@code 400 (Bad Request)} if the familyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the familyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/families")
    public ResponseEntity<FamilyDTO> updateFamily(@Valid @RequestBody FamilyDTO familyDTO) throws URISyntaxException {
        log.debug("REST request to update Family : {}", familyDTO);
        if (familyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FamilyDTO result = familyService.save(familyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, familyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /families} : get all the families.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of families in body.
     */
    @GetMapping("/families")
    public ResponseEntity<List<FamilyDTO>> getAllFamilies(Pageable pageable) {
        log.debug("REST request to get a page of Families");
        Page<FamilyDTO> page = familyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /families/:id} : get the "id" family.
     *
     * @param id the id of the familyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the familyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/families/{id}")
    public ResponseEntity<FamilyDTO> getFamily(@PathVariable Long id) {
        log.debug("REST request to get Family : {}", id);
        Optional<FamilyDTO> familyDTO = familyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(familyDTO);
    }

    /**
     * {@code DELETE  /families/:id} : delete the "id" family.
     *
     * @param id the id of the familyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/families/{id}")
    public ResponseEntity<Void> deleteFamily(@PathVariable Long id) {
        log.debug("REST request to delete Family : {}", id);
        familyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/families?query=:query} : search for the family corresponding
     * to the query.
     *
     * @param query the query of the family search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/families")
    public ResponseEntity<List<FamilyDTO>> searchFamilies(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Families for query {}", query);
        Page<FamilyDTO> page = familyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
