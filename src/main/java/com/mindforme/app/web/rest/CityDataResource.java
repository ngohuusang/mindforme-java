package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.CityDataService;
import com.mindforme.app.service.dto.CityDataDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.CityData}.
 */
@RestController
@RequestMapping("/api")
public class CityDataResource {
    private final Logger log = LoggerFactory.getLogger(CityDataResource.class);

    private static final String ENTITY_NAME = "cityData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CityDataService cityDataService;

    public CityDataResource(CityDataService cityDataService) {
        this.cityDataService = cityDataService;
    }

    /**
     * {@code POST  /city-data} : Create a new cityData.
     *
     * @param cityDataDTO the cityDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cityDataDTO, or with status {@code 400 (Bad Request)} if the cityData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/city-data")
    public ResponseEntity<CityDataDTO> createCityData(@Valid @RequestBody CityDataDTO cityDataDTO) throws URISyntaxException {
        log.debug("REST request to save CityData : {}", cityDataDTO);
        if (cityDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new cityData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CityDataDTO result = cityDataService.save(cityDataDTO);
        return ResponseEntity
            .created(new URI("/api/city-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /city-data} : Updates an existing cityData.
     *
     * @param cityDataDTO the cityDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cityDataDTO,
     * or with status {@code 400 (Bad Request)} if the cityDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cityDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/city-data")
    public ResponseEntity<CityDataDTO> updateCityData(@Valid @RequestBody CityDataDTO cityDataDTO) throws URISyntaxException {
        log.debug("REST request to update CityData : {}", cityDataDTO);
        if (cityDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CityDataDTO result = cityDataService.save(cityDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cityDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /city-data} : get all the cityData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cityData in body.
     */
    @GetMapping("/city-data")
    public ResponseEntity<List<CityDataDTO>> getAllCityData(Pageable pageable) {
        log.debug("REST request to get a page of CityData");
        Page<CityDataDTO> page = cityDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /city-data/:id} : get the "id" cityData.
     *
     * @param id the id of the cityDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cityDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/city-data/{id}")
    public ResponseEntity<CityDataDTO> getCityData(@PathVariable Long id) {
        log.debug("REST request to get CityData : {}", id);
        Optional<CityDataDTO> cityDataDTO = cityDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cityDataDTO);
    }

    /**
     * {@code DELETE  /city-data/:id} : delete the "id" cityData.
     *
     * @param id the id of the cityDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/city-data/{id}")
    public ResponseEntity<Void> deleteCityData(@PathVariable Long id) {
        log.debug("REST request to delete CityData : {}", id);
        cityDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/city-data?query=:query} : search for the cityData corresponding
     * to the query.
     *
     * @param query the query of the cityData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/city-data")
    public ResponseEntity<List<CityDataDTO>> searchCityData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CityData for query {}", query);
        Page<CityDataDTO> page = cityDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
