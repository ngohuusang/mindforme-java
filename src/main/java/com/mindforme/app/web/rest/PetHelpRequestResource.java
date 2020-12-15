package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.PetHelpRequestService;
import com.mindforme.app.service.dto.PetHelpRequestDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.PetHelpRequest}.
 */
@RestController
@RequestMapping("/api")
public class PetHelpRequestResource {
    private final Logger log = LoggerFactory.getLogger(PetHelpRequestResource.class);

    private static final String ENTITY_NAME = "petHelpRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PetHelpRequestService petHelpRequestService;

    public PetHelpRequestResource(PetHelpRequestService petHelpRequestService) {
        this.petHelpRequestService = petHelpRequestService;
    }

    /**
     * {@code POST  /pet-help-requests} : Create a new petHelpRequest.
     *
     * @param petHelpRequestDTO the petHelpRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new petHelpRequestDTO, or with status {@code 400 (Bad Request)} if the petHelpRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pet-help-requests")
    public ResponseEntity<PetHelpRequestDTO> createPetHelpRequest(@Valid @RequestBody PetHelpRequestDTO petHelpRequestDTO)
        throws URISyntaxException {
        log.debug("REST request to save PetHelpRequest : {}", petHelpRequestDTO);
        if (petHelpRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new petHelpRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PetHelpRequestDTO result = petHelpRequestService.save(petHelpRequestDTO);
        return ResponseEntity
            .created(new URI("/api/pet-help-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pet-help-requests} : Updates an existing petHelpRequest.
     *
     * @param petHelpRequestDTO the petHelpRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated petHelpRequestDTO,
     * or with status {@code 400 (Bad Request)} if the petHelpRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the petHelpRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pet-help-requests")
    public ResponseEntity<PetHelpRequestDTO> updatePetHelpRequest(@Valid @RequestBody PetHelpRequestDTO petHelpRequestDTO)
        throws URISyntaxException {
        log.debug("REST request to update PetHelpRequest : {}", petHelpRequestDTO);
        if (petHelpRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PetHelpRequestDTO result = petHelpRequestService.save(petHelpRequestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, petHelpRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pet-help-requests} : get all the petHelpRequests.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of petHelpRequests in body.
     */
    @GetMapping("/pet-help-requests")
    public ResponseEntity<List<PetHelpRequestDTO>> getAllPetHelpRequests(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of PetHelpRequests");
        Page<PetHelpRequestDTO> page;
        if (eagerload) {
            page = petHelpRequestService.findAllWithEagerRelationships(pageable);
        } else {
            page = petHelpRequestService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pet-help-requests/:id} : get the "id" petHelpRequest.
     *
     * @param id the id of the petHelpRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the petHelpRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pet-help-requests/{id}")
    public ResponseEntity<PetHelpRequestDTO> getPetHelpRequest(@PathVariable Long id) {
        log.debug("REST request to get PetHelpRequest : {}", id);
        Optional<PetHelpRequestDTO> petHelpRequestDTO = petHelpRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(petHelpRequestDTO);
    }

    /**
     * {@code DELETE  /pet-help-requests/:id} : delete the "id" petHelpRequest.
     *
     * @param id the id of the petHelpRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pet-help-requests/{id}")
    public ResponseEntity<Void> deletePetHelpRequest(@PathVariable Long id) {
        log.debug("REST request to delete PetHelpRequest : {}", id);
        petHelpRequestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/pet-help-requests?query=:query} : search for the petHelpRequest corresponding
     * to the query.
     *
     * @param query the query of the petHelpRequest search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/pet-help-requests")
    public ResponseEntity<List<PetHelpRequestDTO>> searchPetHelpRequests(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PetHelpRequests for query {}", query);
        Page<PetHelpRequestDTO> page = petHelpRequestService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
