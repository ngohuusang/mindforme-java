package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.CouponTypeService;
import com.mindforme.app.service.dto.CouponTypeDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.CouponType}.
 */
@RestController
@RequestMapping("/api")
public class CouponTypeResource {
    private final Logger log = LoggerFactory.getLogger(CouponTypeResource.class);

    private static final String ENTITY_NAME = "couponType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CouponTypeService couponTypeService;

    public CouponTypeResource(CouponTypeService couponTypeService) {
        this.couponTypeService = couponTypeService;
    }

    /**
     * {@code POST  /coupon-types} : Create a new couponType.
     *
     * @param couponTypeDTO the couponTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new couponTypeDTO, or with status {@code 400 (Bad Request)} if the couponType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/coupon-types")
    public ResponseEntity<CouponTypeDTO> createCouponType(@RequestBody CouponTypeDTO couponTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CouponType : {}", couponTypeDTO);
        if (couponTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new couponType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CouponTypeDTO result = couponTypeService.save(couponTypeDTO);
        return ResponseEntity
            .created(new URI("/api/coupon-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /coupon-types} : Updates an existing couponType.
     *
     * @param couponTypeDTO the couponTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated couponTypeDTO,
     * or with status {@code 400 (Bad Request)} if the couponTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the couponTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/coupon-types")
    public ResponseEntity<CouponTypeDTO> updateCouponType(@RequestBody CouponTypeDTO couponTypeDTO) throws URISyntaxException {
        log.debug("REST request to update CouponType : {}", couponTypeDTO);
        if (couponTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CouponTypeDTO result = couponTypeService.save(couponTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, couponTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /coupon-types} : get all the couponTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of couponTypes in body.
     */
    @GetMapping("/coupon-types")
    public ResponseEntity<List<CouponTypeDTO>> getAllCouponTypes(Pageable pageable) {
        log.debug("REST request to get a page of CouponTypes");
        Page<CouponTypeDTO> page = couponTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /coupon-types/:id} : get the "id" couponType.
     *
     * @param id the id of the couponTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the couponTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/coupon-types/{id}")
    public ResponseEntity<CouponTypeDTO> getCouponType(@PathVariable Long id) {
        log.debug("REST request to get CouponType : {}", id);
        Optional<CouponTypeDTO> couponTypeDTO = couponTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(couponTypeDTO);
    }

    /**
     * {@code DELETE  /coupon-types/:id} : delete the "id" couponType.
     *
     * @param id the id of the couponTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/coupon-types/{id}")
    public ResponseEntity<Void> deleteCouponType(@PathVariable Long id) {
        log.debug("REST request to delete CouponType : {}", id);
        couponTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/coupon-types?query=:query} : search for the couponType corresponding
     * to the query.
     *
     * @param query the query of the couponType search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/coupon-types")
    public ResponseEntity<List<CouponTypeDTO>> searchCouponTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CouponTypes for query {}", query);
        Page<CouponTypeDTO> page = couponTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
