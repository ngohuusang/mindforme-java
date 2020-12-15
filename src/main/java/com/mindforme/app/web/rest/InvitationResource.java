package com.mindforme.app.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.service.InvitationService;
import com.mindforme.app.service.dto.InvitationDTO;
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
 * REST controller for managing {@link com.mindforme.app.domain.Invitation}.
 */
@RestController
@RequestMapping("/api")
public class InvitationResource {
    private final Logger log = LoggerFactory.getLogger(InvitationResource.class);

    private static final String ENTITY_NAME = "invitation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvitationService invitationService;

    public InvitationResource(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    /**
     * {@code POST  /invitations} : Create a new invitation.
     *
     * @param invitationDTO the invitationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invitationDTO, or with status {@code 400 (Bad Request)} if the invitation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invitations")
    public ResponseEntity<InvitationDTO> createInvitation(@RequestBody InvitationDTO invitationDTO) throws URISyntaxException {
        log.debug("REST request to save Invitation : {}", invitationDTO);
        if (invitationDTO.getId() != null) {
            throw new BadRequestAlertException("A new invitation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvitationDTO result = invitationService.save(invitationDTO);
        return ResponseEntity
            .created(new URI("/api/invitations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invitations} : Updates an existing invitation.
     *
     * @param invitationDTO the invitationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invitationDTO,
     * or with status {@code 400 (Bad Request)} if the invitationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invitationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invitations")
    public ResponseEntity<InvitationDTO> updateInvitation(@RequestBody InvitationDTO invitationDTO) throws URISyntaxException {
        log.debug("REST request to update Invitation : {}", invitationDTO);
        if (invitationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvitationDTO result = invitationService.save(invitationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, invitationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /invitations} : get all the invitations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invitations in body.
     */
    @GetMapping("/invitations")
    public ResponseEntity<List<InvitationDTO>> getAllInvitations(Pageable pageable) {
        log.debug("REST request to get a page of Invitations");
        Page<InvitationDTO> page = invitationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /invitations/:id} : get the "id" invitation.
     *
     * @param id the id of the invitationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invitationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invitations/{id}")
    public ResponseEntity<InvitationDTO> getInvitation(@PathVariable Long id) {
        log.debug("REST request to get Invitation : {}", id);
        Optional<InvitationDTO> invitationDTO = invitationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invitationDTO);
    }

    /**
     * {@code DELETE  /invitations/:id} : delete the "id" invitation.
     *
     * @param id the id of the invitationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invitations/{id}")
    public ResponseEntity<Void> deleteInvitation(@PathVariable Long id) {
        log.debug("REST request to delete Invitation : {}", id);
        invitationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/invitations?query=:query} : search for the invitation corresponding
     * to the query.
     *
     * @param query the query of the invitation search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/invitations")
    public ResponseEntity<List<InvitationDTO>> searchInvitations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Invitations for query {}", query);
        Page<InvitationDTO> page = invitationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
