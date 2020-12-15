package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.PetBreedDataService;
import com.mindforme.app.service.dto.PetBreedDataDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.PetBreedData}.
 */
@RestController
@RequestMapping("/api")
public class PetBreedDataResource {
    private final Logger log = LoggerFactory.getLogger(PetBreedDataResource.class);

    private static final String ENTITY_NAME = "petBreedData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PetBreedDataService petBreedDataService;

    public PetBreedDataResource(PetBreedDataService petBreedDataService) {
        this.petBreedDataService = petBreedDataService;
    }

    /**
     * {@code POST  /pet-breed-data} : Create a new petBreedData.
     *
     * @param petBreedDataDTO the petBreedDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new petBreedDataDTO, or with status {@code 400 (Bad Request)} if the petBreedData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pet-breed-data")
    public ResponseEntity<PetBreedDataDTO> createPetBreedData(@Valid @RequestBody PetBreedDataDTO petBreedDataDTO)
        throws URISyntaxException {
        log.debug("REST request to save PetBreedData : {}", petBreedDataDTO);
        if (petBreedDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new petBreedData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PetBreedDataDTO result = petBreedDataService.save(petBreedDataDTO);
        return ResponseEntity
            .created(new URI("/api/pet-breed-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pet-breed-data} : Updates an existing petBreedData.
     *
     * @param petBreedDataDTO the petBreedDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated petBreedDataDTO,
     * or with status {@code 400 (Bad Request)} if the petBreedDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the petBreedDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pet-breed-data")
    public ResponseEntity<PetBreedDataDTO> updatePetBreedData(@Valid @RequestBody PetBreedDataDTO petBreedDataDTO)
        throws URISyntaxException {
        log.debug("REST request to update PetBreedData : {}", petBreedDataDTO);
        if (petBreedDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PetBreedDataDTO result = petBreedDataService.save(petBreedDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, petBreedDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pet-breed-data} : get all the petBreedData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of petBreedData in body.
     */
    @GetMapping("/pet-breed-data")
    public ResponseEntity<List<PetBreedDataDTO>> getAllPetBreedData(Pageable pageable) {
        log.debug("REST request to get a page of PetBreedData");
        Page<PetBreedDataDTO> page = petBreedDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pet-breed-data/:id} : get the "id" petBreedData.
     *
     * @param id the id of the petBreedDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the petBreedDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pet-breed-data/{id}")
    public ResponseEntity<PetBreedDataDTO> getPetBreedData(@PathVariable Long id) {
        log.debug("REST request to get PetBreedData : {}", id);
        Optional<PetBreedDataDTO> petBreedDataDTO = petBreedDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(petBreedDataDTO);
    }

    /**
     * {@code DELETE  /pet-breed-data/:id} : delete the "id" petBreedData.
     *
     * @param id the id of the petBreedDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pet-breed-data/{id}")
    public ResponseEntity<Void> deletePetBreedData(@PathVariable Long id) {
        log.debug("REST request to delete PetBreedData : {}", id);
        petBreedDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/pet-breed-data?query=:query} : search for the petBreedData corresponding
     * to the query.
     *
     * @param query the query of the petBreedData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/pet-breed-data")
    public ResponseEntity<List<PetBreedDataDTO>> searchPetBreedData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PetBreedData for query {}", query);
        Page<PetBreedDataDTO> page = petBreedDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
