package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.EmergencyContactService;
import com.mindforme.app.service.dto.EmergencyContactDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.EmergencyContact}.
 */
@RestController
@RequestMapping("/api")
public class EmergencyContactResource {
    private final Logger log = LoggerFactory.getLogger(EmergencyContactResource.class);

    private static final String ENTITY_NAME = "emergencyContact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmergencyContactService emergencyContactService;

    public EmergencyContactResource(EmergencyContactService emergencyContactService) {
        this.emergencyContactService = emergencyContactService;
    }

    /**
     * {@code POST  /emergency-contacts} : Create a new emergencyContact.
     *
     * @param emergencyContactDTO the emergencyContactDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emergencyContactDTO, or with status {@code 400 (Bad Request)} if the emergencyContact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emergency-contacts")
    public ResponseEntity<EmergencyContactDTO> createEmergencyContact(@Valid @RequestBody EmergencyContactDTO emergencyContactDTO)
        throws URISyntaxException {
        log.debug("REST request to save EmergencyContact : {}", emergencyContactDTO);
        if (emergencyContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new emergencyContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmergencyContactDTO result = emergencyContactService.save(emergencyContactDTO);
        return ResponseEntity
            .created(new URI("/api/emergency-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emergency-contacts} : Updates an existing emergencyContact.
     *
     * @param emergencyContactDTO the emergencyContactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emergencyContactDTO,
     * or with status {@code 400 (Bad Request)} if the emergencyContactDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emergencyContactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emergency-contacts")
    public ResponseEntity<EmergencyContactDTO> updateEmergencyContact(@Valid @RequestBody EmergencyContactDTO emergencyContactDTO)
        throws URISyntaxException {
        log.debug("REST request to update EmergencyContact : {}", emergencyContactDTO);
        if (emergencyContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmergencyContactDTO result = emergencyContactService.save(emergencyContactDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, emergencyContactDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /emergency-contacts} : get all the emergencyContacts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emergencyContacts in body.
     */
    @GetMapping("/emergency-contacts")
    public ResponseEntity<List<EmergencyContactDTO>> getAllEmergencyContacts(Pageable pageable) {
        log.debug("REST request to get a page of EmergencyContacts");
        Page<EmergencyContactDTO> page = emergencyContactService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emergency-contacts/:id} : get the "id" emergencyContact.
     *
     * @param id the id of the emergencyContactDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emergencyContactDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emergency-contacts/{id}")
    public ResponseEntity<EmergencyContactDTO> getEmergencyContact(@PathVariable Long id) {
        log.debug("REST request to get EmergencyContact : {}", id);
        Optional<EmergencyContactDTO> emergencyContactDTO = emergencyContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emergencyContactDTO);
    }

    /**
     * {@code DELETE  /emergency-contacts/:id} : delete the "id" emergencyContact.
     *
     * @param id the id of the emergencyContactDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emergency-contacts/{id}")
    public ResponseEntity<Void> deleteEmergencyContact(@PathVariable Long id) {
        log.debug("REST request to delete EmergencyContact : {}", id);
        emergencyContactService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/emergency-contacts?query=:query} : search for the emergencyContact corresponding
     * to the query.
     *
     * @param query the query of the emergencyContact search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/emergency-contacts")
    public ResponseEntity<List<EmergencyContactDTO>> searchEmergencyContacts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EmergencyContacts for query {}", query);
        Page<EmergencyContactDTO> page = emergencyContactService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
