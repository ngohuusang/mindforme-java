package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.MindingNotificationService;
import com.mindforme.app.service.dto.MindingNotificationDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.MindingNotification}.
 */
@RestController
@RequestMapping("/api")
public class MindingNotificationResource {
    private final Logger log = LoggerFactory.getLogger(MindingNotificationResource.class);

    private static final String ENTITY_NAME = "mindingNotification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MindingNotificationService mindingNotificationService;

    public MindingNotificationResource(MindingNotificationService mindingNotificationService) {
        this.mindingNotificationService = mindingNotificationService;
    }

    /**
     * {@code POST  /minding-notifications} : Create a new mindingNotification.
     *
     * @param mindingNotificationDTO the mindingNotificationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mindingNotificationDTO, or with status {@code 400 (Bad Request)} if the mindingNotification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/minding-notifications")
    public ResponseEntity<MindingNotificationDTO> createMindingNotification(@RequestBody MindingNotificationDTO mindingNotificationDTO)
        throws URISyntaxException {
        log.debug("REST request to save MindingNotification : {}", mindingNotificationDTO);
        if (mindingNotificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new mindingNotification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MindingNotificationDTO result = mindingNotificationService.save(mindingNotificationDTO);
        return ResponseEntity
            .created(new URI("/api/minding-notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /minding-notifications} : Updates an existing mindingNotification.
     *
     * @param mindingNotificationDTO the mindingNotificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mindingNotificationDTO,
     * or with status {@code 400 (Bad Request)} if the mindingNotificationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mindingNotificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/minding-notifications")
    public ResponseEntity<MindingNotificationDTO> updateMindingNotification(@RequestBody MindingNotificationDTO mindingNotificationDTO)
        throws URISyntaxException {
        log.debug("REST request to update MindingNotification : {}", mindingNotificationDTO);
        if (mindingNotificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MindingNotificationDTO result = mindingNotificationService.save(mindingNotificationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mindingNotificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /minding-notifications} : get all the mindingNotifications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mindingNotifications in body.
     */
    @GetMapping("/minding-notifications")
    public ResponseEntity<List<MindingNotificationDTO>> getAllMindingNotifications(Pageable pageable) {
        log.debug("REST request to get a page of MindingNotifications");
        Page<MindingNotificationDTO> page = mindingNotificationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /minding-notifications/:id} : get the "id" mindingNotification.
     *
     * @param id the id of the mindingNotificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mindingNotificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/minding-notifications/{id}")
    public ResponseEntity<MindingNotificationDTO> getMindingNotification(@PathVariable Long id) {
        log.debug("REST request to get MindingNotification : {}", id);
        Optional<MindingNotificationDTO> mindingNotificationDTO = mindingNotificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mindingNotificationDTO);
    }

    /**
     * {@code DELETE  /minding-notifications/:id} : delete the "id" mindingNotification.
     *
     * @param id the id of the mindingNotificationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/minding-notifications/{id}")
    public ResponseEntity<Void> deleteMindingNotification(@PathVariable Long id) {
        log.debug("REST request to delete MindingNotification : {}", id);
        mindingNotificationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/minding-notifications?query=:query} : search for the mindingNotification corresponding
     * to the query.
     *
     * @param query the query of the mindingNotification search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/minding-notifications")
    public ResponseEntity<List<MindingNotificationDTO>> searchMindingNotifications(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MindingNotifications for query {}", query);
        Page<MindingNotificationDTO> page = mindingNotificationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
