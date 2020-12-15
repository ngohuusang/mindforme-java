package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.PetTypeDataService;
import com.mindforme.app.service.dto.PetTypeDataDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.PetTypeData}.
 */
@RestController
@RequestMapping("/api")
public class PetTypeDataResource {
    private final Logger log = LoggerFactory.getLogger(PetTypeDataResource.class);

    private static final String ENTITY_NAME = "petTypeData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PetTypeDataService petTypeDataService;

    public PetTypeDataResource(PetTypeDataService petTypeDataService) {
        this.petTypeDataService = petTypeDataService;
    }

    /**
     * {@code POST  /pet-type-data} : Create a new petTypeData.
     *
     * @param petTypeDataDTO the petTypeDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new petTypeDataDTO, or with status {@code 400 (Bad Request)} if the petTypeData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pet-type-data")
    public ResponseEntity<PetTypeDataDTO> createPetTypeData(@Valid @RequestBody PetTypeDataDTO petTypeDataDTO) throws URISyntaxException {
        log.debug("REST request to save PetTypeData : {}", petTypeDataDTO);
        if (petTypeDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new petTypeData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PetTypeDataDTO result = petTypeDataService.save(petTypeDataDTO);
        return ResponseEntity
            .created(new URI("/api/pet-type-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pet-type-data} : Updates an existing petTypeData.
     *
     * @param petTypeDataDTO the petTypeDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated petTypeDataDTO,
     * or with status {@code 400 (Bad Request)} if the petTypeDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the petTypeDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pet-type-data")
    public ResponseEntity<PetTypeDataDTO> updatePetTypeData(@Valid @RequestBody PetTypeDataDTO petTypeDataDTO) throws URISyntaxException {
        log.debug("REST request to update PetTypeData : {}", petTypeDataDTO);
        if (petTypeDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PetTypeDataDTO result = petTypeDataService.save(petTypeDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, petTypeDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pet-type-data} : get all the petTypeData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of petTypeData in body.
     */
    @GetMapping("/pet-type-data")
    public ResponseEntity<List<PetTypeDataDTO>> getAllPetTypeData(Pageable pageable) {
        log.debug("REST request to get a page of PetTypeData");
        Page<PetTypeDataDTO> page = petTypeDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pet-type-data/:id} : get the "id" petTypeData.
     *
     * @param id the id of the petTypeDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the petTypeDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pet-type-data/{id}")
    public ResponseEntity<PetTypeDataDTO> getPetTypeData(@PathVariable Long id) {
        log.debug("REST request to get PetTypeData : {}", id);
        Optional<PetTypeDataDTO> petTypeDataDTO = petTypeDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(petTypeDataDTO);
    }

    /**
     * {@code DELETE  /pet-type-data/:id} : delete the "id" petTypeData.
     *
     * @param id the id of the petTypeDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pet-type-data/{id}")
    public ResponseEntity<Void> deletePetTypeData(@PathVariable Long id) {
        log.debug("REST request to delete PetTypeData : {}", id);
        petTypeDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/pet-type-data?query=:query} : search for the petTypeData corresponding
     * to the query.
     *
     * @param query the query of the petTypeData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/pet-type-data")
    public ResponseEntity<List<PetTypeDataDTO>> searchPetTypeData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PetTypeData for query {}", query);
        Page<PetTypeDataDTO> page = petTypeDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
