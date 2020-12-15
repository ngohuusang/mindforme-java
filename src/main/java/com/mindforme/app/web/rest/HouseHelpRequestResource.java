package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.HouseHelpRequestService;
import com.mindforme.app.service.dto.HouseHelpRequestDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.HouseHelpRequest}.
 */
@RestController
@RequestMapping("/api")
public class HouseHelpRequestResource {
    private final Logger log = LoggerFactory.getLogger(HouseHelpRequestResource.class);

    private static final String ENTITY_NAME = "houseHelpRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HouseHelpRequestService houseHelpRequestService;

    public HouseHelpRequestResource(HouseHelpRequestService houseHelpRequestService) {
        this.houseHelpRequestService = houseHelpRequestService;
    }

    /**
     * {@code POST  /house-help-requests} : Create a new houseHelpRequest.
     *
     * @param houseHelpRequestDTO the houseHelpRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new houseHelpRequestDTO, or with status {@code 400 (Bad Request)} if the houseHelpRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/house-help-requests")
    public ResponseEntity<HouseHelpRequestDTO> createHouseHelpRequest(@Valid @RequestBody HouseHelpRequestDTO houseHelpRequestDTO)
        throws URISyntaxException {
        log.debug("REST request to save HouseHelpRequest : {}", houseHelpRequestDTO);
        if (houseHelpRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new houseHelpRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HouseHelpRequestDTO result = houseHelpRequestService.save(houseHelpRequestDTO);
        return ResponseEntity
            .created(new URI("/api/house-help-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /house-help-requests} : Updates an existing houseHelpRequest.
     *
     * @param houseHelpRequestDTO the houseHelpRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated houseHelpRequestDTO,
     * or with status {@code 400 (Bad Request)} if the houseHelpRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the houseHelpRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/house-help-requests")
    public ResponseEntity<HouseHelpRequestDTO> updateHouseHelpRequest(@Valid @RequestBody HouseHelpRequestDTO houseHelpRequestDTO)
        throws URISyntaxException {
        log.debug("REST request to update HouseHelpRequest : {}", houseHelpRequestDTO);
        if (houseHelpRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HouseHelpRequestDTO result = houseHelpRequestService.save(houseHelpRequestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, houseHelpRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /house-help-requests} : get all the houseHelpRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of houseHelpRequests in body.
     */
    @GetMapping("/house-help-requests")
    public ResponseEntity<List<HouseHelpRequestDTO>> getAllHouseHelpRequests(Pageable pageable) {
        log.debug("REST request to get a page of HouseHelpRequests");
        Page<HouseHelpRequestDTO> page = houseHelpRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /house-help-requests/:id} : get the "id" houseHelpRequest.
     *
     * @param id the id of the houseHelpRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the houseHelpRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/house-help-requests/{id}")
    public ResponseEntity<HouseHelpRequestDTO> getHouseHelpRequest(@PathVariable Long id) {
        log.debug("REST request to get HouseHelpRequest : {}", id);
        Optional<HouseHelpRequestDTO> houseHelpRequestDTO = houseHelpRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(houseHelpRequestDTO);
    }

    /**
     * {@code DELETE  /house-help-requests/:id} : delete the "id" houseHelpRequest.
     *
     * @param id the id of the houseHelpRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/house-help-requests/{id}")
    public ResponseEntity<Void> deleteHouseHelpRequest(@PathVariable Long id) {
        log.debug("REST request to delete HouseHelpRequest : {}", id);
        houseHelpRequestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/house-help-requests?query=:query} : search for the houseHelpRequest corresponding
     * to the query.
     *
     * @param query the query of the houseHelpRequest search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/house-help-requests")
    public ResponseEntity<List<HouseHelpRequestDTO>> searchHouseHelpRequests(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of HouseHelpRequests for query {}", query);
        Page<HouseHelpRequestDTO> page = houseHelpRequestService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
