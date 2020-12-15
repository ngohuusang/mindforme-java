package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.PetBreedService;
import com.mindforme.app.service.dto.PetBreedDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.PetBreed}.
 */
@RestController
@RequestMapping("/api")
public class PetBreedResource {
    private final Logger log = LoggerFactory.getLogger(PetBreedResource.class);

    private static final String ENTITY_NAME = "petBreed";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PetBreedService petBreedService;

    public PetBreedResource(PetBreedService petBreedService) {
        this.petBreedService = petBreedService;
    }

    /**
     * {@code POST  /pet-breeds} : Create a new petBreed.
     *
     * @param petBreedDTO the petBreedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new petBreedDTO, or with status {@code 400 (Bad Request)} if the petBreed has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pet-breeds")
    public ResponseEntity<PetBreedDTO> createPetBreed(@RequestBody PetBreedDTO petBreedDTO) throws URISyntaxException {
        log.debug("REST request to save PetBreed : {}", petBreedDTO);
        if (petBreedDTO.getId() != null) {
            throw new BadRequestAlertException("A new petBreed cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PetBreedDTO result = petBreedService.save(petBreedDTO);
        return ResponseEntity
            .created(new URI("/api/pet-breeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pet-breeds} : Updates an existing petBreed.
     *
     * @param petBreedDTO the petBreedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated petBreedDTO,
     * or with status {@code 400 (Bad Request)} if the petBreedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the petBreedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pet-breeds")
    public ResponseEntity<PetBreedDTO> updatePetBreed(@RequestBody PetBreedDTO petBreedDTO) throws URISyntaxException {
        log.debug("REST request to update PetBreed : {}", petBreedDTO);
        if (petBreedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PetBreedDTO result = petBreedService.save(petBreedDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, petBreedDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pet-breeds} : get all the petBreeds.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of petBreeds in body.
     */
    @GetMapping("/pet-breeds")
    public ResponseEntity<List<PetBreedDTO>> getAllPetBreeds(Pageable pageable) {
        log.debug("REST request to get a page of PetBreeds");
        Page<PetBreedDTO> page = petBreedService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pet-breeds/:id} : get the "id" petBreed.
     *
     * @param id the id of the petBreedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the petBreedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pet-breeds/{id}")
    public ResponseEntity<PetBreedDTO> getPetBreed(@PathVariable Long id) {
        log.debug("REST request to get PetBreed : {}", id);
        Optional<PetBreedDTO> petBreedDTO = petBreedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(petBreedDTO);
    }

    /**
     * {@code DELETE  /pet-breeds/:id} : delete the "id" petBreed.
     *
     * @param id the id of the petBreedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pet-breeds/{id}")
    public ResponseEntity<Void> deletePetBreed(@PathVariable Long id) {
        log.debug("REST request to delete PetBreed : {}", id);
        petBreedService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/pet-breeds?query=:query} : search for the petBreed corresponding
     * to the query.
     *
     * @param query the query of the petBreed search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/pet-breeds")
    public ResponseEntity<List<PetBreedDTO>> searchPetBreeds(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PetBreeds for query {}", query);
        Page<PetBreedDTO> page = petBreedService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
