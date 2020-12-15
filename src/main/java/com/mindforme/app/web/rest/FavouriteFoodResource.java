package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.FavouriteFoodService;
import com.mindforme.app.service.dto.FavouriteFoodDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.FavouriteFood}.
 */
@RestController
@RequestMapping("/api")
public class FavouriteFoodResource {
    private final Logger log = LoggerFactory.getLogger(FavouriteFoodResource.class);

    private static final String ENTITY_NAME = "favouriteFood";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FavouriteFoodService favouriteFoodService;

    public FavouriteFoodResource(FavouriteFoodService favouriteFoodService) {
        this.favouriteFoodService = favouriteFoodService;
    }

    /**
     * {@code POST  /favourite-foods} : Create a new favouriteFood.
     *
     * @param favouriteFoodDTO the favouriteFoodDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new favouriteFoodDTO, or with status {@code 400 (Bad Request)} if the favouriteFood has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/favourite-foods")
    public ResponseEntity<FavouriteFoodDTO> createFavouriteFood(@RequestBody FavouriteFoodDTO favouriteFoodDTO) throws URISyntaxException {
        log.debug("REST request to save FavouriteFood : {}", favouriteFoodDTO);
        if (favouriteFoodDTO.getId() != null) {
            throw new BadRequestAlertException("A new favouriteFood cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FavouriteFoodDTO result = favouriteFoodService.save(favouriteFoodDTO);
        return ResponseEntity
            .created(new URI("/api/favourite-foods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /favourite-foods} : Updates an existing favouriteFood.
     *
     * @param favouriteFoodDTO the favouriteFoodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated favouriteFoodDTO,
     * or with status {@code 400 (Bad Request)} if the favouriteFoodDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the favouriteFoodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/favourite-foods")
    public ResponseEntity<FavouriteFoodDTO> updateFavouriteFood(@RequestBody FavouriteFoodDTO favouriteFoodDTO) throws URISyntaxException {
        log.debug("REST request to update FavouriteFood : {}", favouriteFoodDTO);
        if (favouriteFoodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FavouriteFoodDTO result = favouriteFoodService.save(favouriteFoodDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, favouriteFoodDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /favourite-foods} : get all the favouriteFoods.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of favouriteFoods in body.
     */
    @GetMapping("/favourite-foods")
    public ResponseEntity<List<FavouriteFoodDTO>> getAllFavouriteFoods(Pageable pageable) {
        log.debug("REST request to get a page of FavouriteFoods");
        Page<FavouriteFoodDTO> page = favouriteFoodService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /favourite-foods/:id} : get the "id" favouriteFood.
     *
     * @param id the id of the favouriteFoodDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the favouriteFoodDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/favourite-foods/{id}")
    public ResponseEntity<FavouriteFoodDTO> getFavouriteFood(@PathVariable Long id) {
        log.debug("REST request to get FavouriteFood : {}", id);
        Optional<FavouriteFoodDTO> favouriteFoodDTO = favouriteFoodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(favouriteFoodDTO);
    }

    /**
     * {@code DELETE  /favourite-foods/:id} : delete the "id" favouriteFood.
     *
     * @param id the id of the favouriteFoodDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/favourite-foods/{id}")
    public ResponseEntity<Void> deleteFavouriteFood(@PathVariable Long id) {
        log.debug("REST request to delete FavouriteFood : {}", id);
        favouriteFoodService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/favourite-foods?query=:query} : search for the favouriteFood corresponding
     * to the query.
     *
     * @param query the query of the favouriteFood search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/favourite-foods")
    public ResponseEntity<List<FavouriteFoodDTO>> searchFavouriteFoods(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FavouriteFoods for query {}", query);
        Page<FavouriteFoodDTO> page = favouriteFoodService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
