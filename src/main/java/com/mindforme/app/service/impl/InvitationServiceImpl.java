package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.Invitation;
import com.mindforme.app.repository.InvitationRepository;
import com.mindforme.app.repository.search.InvitationSearchRepository;
import com.mindforme.app.service.InvitationService;
import com.mindforme.app.service.dto.InvitationDTO;
import com.mindforme.app.service.mapper.InvitationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Invitation}.
 */
@Service
@Transactional
public class InvitationServiceImpl implements InvitationService {
    private final Logger log = LoggerFactory.getLogger(InvitationServiceImpl.class);

    private final InvitationRepository invitationRepository;

    private final InvitationMapper invitationMapper;

    private final InvitationSearchRepository invitationSearchRepository;

    public InvitationServiceImpl(
        InvitationRepository invitationRepository,
        InvitationMapper invitationMapper,
        InvitationSearchRepository invitationSearchRepository
    ) {
        this.invitationRepository = invitationRepository;
        this.invitationMapper = invitationMapper;
        this.invitationSearchRepository = invitationSearchRepository;
    }

    @Override
    public InvitationDTO save(InvitationDTO invitationDTO) {
        log.debug("Request to save Invitation : {}", invitationDTO);
        Invitation invitation = invitationMapper.toEntity(invitationDTO);
        invitation = invitationRepository.save(invitation);
        InvitationDTO result = invitationMapper.toDto(invitation);
        invitationSearchRepository.save(invitation);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InvitationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Invitations");
        return invitationRepository.findAll(pageable).map(invitationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InvitationDTO> findOne(Long id) {
        log.debug("Request to get Invitation : {}", id);
        return invitationRepository.findById(id).map(invitationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Invitation : {}", id);
        invitationRepository.deleteById(id);
        invitationSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InvitationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Invitations for query {}", query);
        return invitationSearchRepository.search(queryStringQuery(query), pageable).map(invitationMapper::toDto);
    }
}
