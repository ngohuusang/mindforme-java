package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.PetService;
import com.mindforme.app.service.dto.PetDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.Pet}.
 */
@RestController
@RequestMapping("/api")
public class PetResource {
    private final Logger log = LoggerFactory.getLogger(PetResource.class);

    private static final String ENTITY_NAME = "pet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PetService petService;

    public PetResource(PetService petService) {
        this.petService = petService;
    }

    /**
     * {@code POST  /pets} : Create a new pet.
     *
     * @param petDTO the petDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new petDTO, or with status {@code 400 (Bad Request)} if the pet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pets")
    public ResponseEntity<PetDTO> createPet(@RequestBody PetDTO petDTO) throws URISyntaxException {
        log.debug("REST request to save Pet : {}", petDTO);
        if (petDTO.getId() != null) {
            throw new BadRequestAlertException("A new pet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PetDTO result = petService.save(petDTO);
        return ResponseEntity
            .created(new URI("/api/pets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pets} : Updates an existing pet.
     *
     * @param petDTO the petDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated petDTO,
     * or with status {@code 400 (Bad Request)} if the petDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the petDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pets")
    public ResponseEntity<PetDTO> updatePet(@RequestBody PetDTO petDTO) throws URISyntaxException {
        log.debug("REST request to update Pet : {}", petDTO);
        if (petDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PetDTO result = petService.save(petDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, petDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pets} : get all the pets.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pets in body.
     */
    @GetMapping("/pets")
    public ResponseEntity<List<PetDTO>> getAllPets(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Pets");
        Page<PetDTO> page;
        if (eagerload) {
            page = petService.findAllWithEagerRelationships(pageable);
        } else {
            page = petService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pets/:id} : get the "id" pet.
     *
     * @param id the id of the petDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the petDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pets/{id}")
    public ResponseEntity<PetDTO> getPet(@PathVariable Long id) {
        log.debug("REST request to get Pet : {}", id);
        Optional<PetDTO> petDTO = petService.findOne(id);
        return ResponseUtil.wrapOrNotFound(petDTO);
    }

    /**
     * {@code DELETE  /pets/:id} : delete the "id" pet.
     *
     * @param id the id of the petDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pets/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        log.debug("REST request to delete Pet : {}", id);
        petService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/pets?query=:query} : search for the pet corresponding
     * to the query.
     *
     * @param query the query of the pet search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/pets")
    public ResponseEntity<List<PetDTO>> searchPets(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Pets for query {}", query);
        Page<PetDTO> page = petService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
