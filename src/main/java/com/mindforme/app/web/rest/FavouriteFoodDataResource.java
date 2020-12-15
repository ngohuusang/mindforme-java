package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.FavouriteFoodDataService;
import com.mindforme.app.service.dto.FavouriteFoodDataDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.FavouriteFoodData}.
 */
@RestController
@RequestMapping("/api")
public class FavouriteFoodDataResource {
    private final Logger log = LoggerFactory.getLogger(FavouriteFoodDataResource.class);

    private static final String ENTITY_NAME = "favouriteFoodData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FavouriteFoodDataService favouriteFoodDataService;

    public FavouriteFoodDataResource(FavouriteFoodDataService favouriteFoodDataService) {
        this.favouriteFoodDataService = favouriteFoodDataService;
    }

    /**
     * {@code POST  /favourite-food-data} : Create a new favouriteFoodData.
     *
     * @param favouriteFoodDataDTO the favouriteFoodDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new favouriteFoodDataDTO, or with status {@code 400 (Bad Request)} if the favouriteFoodData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/favourite-food-data")
    public ResponseEntity<FavouriteFoodDataDTO> createFavouriteFoodData(@Valid @RequestBody FavouriteFoodDataDTO favouriteFoodDataDTO)
        throws URISyntaxException {
        log.debug("REST request to save FavouriteFoodData : {}", favouriteFoodDataDTO);
        if (favouriteFoodDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new favouriteFoodData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FavouriteFoodDataDTO result = favouriteFoodDataService.save(favouriteFoodDataDTO);
        return ResponseEntity
            .created(new URI("/api/favourite-food-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /favourite-food-data} : Updates an existing favouriteFoodData.
     *
     * @param favouriteFoodDataDTO the favouriteFoodDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated favouriteFoodDataDTO,
     * or with status {@code 400 (Bad Request)} if the favouriteFoodDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the favouriteFoodDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/favourite-food-data")
    public ResponseEntity<FavouriteFoodDataDTO> updateFavouriteFoodData(@Valid @RequestBody FavouriteFoodDataDTO favouriteFoodDataDTO)
        throws URISyntaxException {
        log.debug("REST request to update FavouriteFoodData : {}", favouriteFoodDataDTO);
        if (favouriteFoodDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FavouriteFoodDataDTO result = favouriteFoodDataService.save(favouriteFoodDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, favouriteFoodDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /favourite-food-data} : get all the favouriteFoodData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of favouriteFoodData in body.
     */
    @GetMapping("/favourite-food-data")
    public ResponseEntity<List<FavouriteFoodDataDTO>> getAllFavouriteFoodData(Pageable pageable) {
        log.debug("REST request to get a page of FavouriteFoodData");
        Page<FavouriteFoodDataDTO> page = favouriteFoodDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /favourite-food-data/:id} : get the "id" favouriteFoodData.
     *
     * @param id the id of the favouriteFoodDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the favouriteFoodDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/favourite-food-data/{id}")
    public ResponseEntity<FavouriteFoodDataDTO> getFavouriteFoodData(@PathVariable Long id) {
        log.debug("REST request to get FavouriteFoodData : {}", id);
        Optional<FavouriteFoodDataDTO> favouriteFoodDataDTO = favouriteFoodDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(favouriteFoodDataDTO);
    }

    /**
     * {@code DELETE  /favourite-food-data/:id} : delete the "id" favouriteFoodData.
     *
     * @param id the id of the favouriteFoodDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/favourite-food-data/{id}")
    public ResponseEntity<Void> deleteFavouriteFoodData(@PathVariable Long id) {
        log.debug("REST request to delete FavouriteFoodData : {}", id);
        favouriteFoodDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/favourite-food-data?query=:query} : search for the favouriteFoodData corresponding
     * to the query.
     *
     * @param query the query of the favouriteFoodData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/favourite-food-data")
    public ResponseEntity<List<FavouriteFoodDataDTO>> searchFavouriteFoodData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FavouriteFoodData for query {}", query);
        Page<FavouriteFoodDataDTO> page = favouriteFoodDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
