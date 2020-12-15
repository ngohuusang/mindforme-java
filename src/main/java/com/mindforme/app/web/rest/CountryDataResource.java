package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.CountryDataService;
import com.mindforme.app.service.dto.CountryDataDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.CountryData}.
 */
@RestController
@RequestMapping("/api")
public class CountryDataResource {
    private final Logger log = LoggerFactory.getLogger(CountryDataResource.class);

    private static final String ENTITY_NAME = "countryData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CountryDataService countryDataService;

    public CountryDataResource(CountryDataService countryDataService) {
        this.countryDataService = countryDataService;
    }

    /**
     * {@code POST  /country-data} : Create a new countryData.
     *
     * @param countryDataDTO the countryDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new countryDataDTO, or with status {@code 400 (Bad Request)} if the countryData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/country-data")
    public ResponseEntity<CountryDataDTO> createCountryData(@Valid @RequestBody CountryDataDTO countryDataDTO) throws URISyntaxException {
        log.debug("REST request to save CountryData : {}", countryDataDTO);
        if (countryDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new countryData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CountryDataDTO result = countryDataService.save(countryDataDTO);
        return ResponseEntity
            .created(new URI("/api/country-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /country-data} : Updates an existing countryData.
     *
     * @param countryDataDTO the countryDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated countryDataDTO,
     * or with status {@code 400 (Bad Request)} if the countryDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the countryDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/country-data")
    public ResponseEntity<CountryDataDTO> updateCountryData(@Valid @RequestBody CountryDataDTO countryDataDTO) throws URISyntaxException {
        log.debug("REST request to update CountryData : {}", countryDataDTO);
        if (countryDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CountryDataDTO result = countryDataService.save(countryDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, countryDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /country-data} : get all the countryData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of countryData in body.
     */
    @GetMapping("/country-data")
    public ResponseEntity<List<CountryDataDTO>> getAllCountryData(Pageable pageable) {
        log.debug("REST request to get a page of CountryData");
        Page<CountryDataDTO> page = countryDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /country-data/:id} : get the "id" countryData.
     *
     * @param id the id of the countryDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the countryDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/country-data/{id}")
    public ResponseEntity<CountryDataDTO> getCountryData(@PathVariable Long id) {
        log.debug("REST request to get CountryData : {}", id);
        Optional<CountryDataDTO> countryDataDTO = countryDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(countryDataDTO);
    }

    /**
     * {@code DELETE  /country-data/:id} : delete the "id" countryData.
     *
     * @param id the id of the countryDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/country-data/{id}")
    public ResponseEntity<Void> deleteCountryData(@PathVariable Long id) {
        log.debug("REST request to delete CountryData : {}", id);
        countryDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/country-data?query=:query} : search for the countryData corresponding
     * to the query.
     *
     * @param query the query of the countryData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/country-data")
    public ResponseEntity<List<CountryDataDTO>> searchCountryData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CountryData for query {}", query);
        Page<CountryDataDTO> page = countryDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
