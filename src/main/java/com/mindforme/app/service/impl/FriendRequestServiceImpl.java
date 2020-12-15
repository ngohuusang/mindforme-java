package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.FriendRequest;
import com.mindforme.app.repository.FriendRequestRepository;
import com.mindforme.app.repository.search.FriendRequestSearchRepository;
import com.mindforme.app.service.FriendRequestService;
import com.mindforme.app.service.dto.FriendRequestDTO;
import com.mindforme.app.service.mapper.FriendRequestMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FriendRequest}.
 */
@Service
@Transactional
public class FriendRequestServiceImpl implements FriendRequestService {
    private final Logger log = LoggerFactory.getLogger(FriendRequestServiceImpl.class);

    private final FriendRequestRepository friendRequestRepository;

    private final FriendRequestMapper friendRequestMapper;

    private final FriendRequestSearchRepository friendRequestSearchRepository;

    public FriendRequestServiceImpl(
        FriendRequestRepository friendRequestRepository,
        FriendRequestMapper friendRequestMapper,
        FriendRequestSearchRepository friendRequestSearchRepository
    ) {
        this.friendRequestRepository = friendRequestRepository;
        this.friendRequestMapper = friendRequestMapper;
        this.friendRequestSearchRepository = friendRequestSearchRepository;
    }

    @Override
    public FriendRequestDTO save(FriendRequestDTO friendRequestDTO) {
        log.debug("Request to save FriendRequest : {}", friendRequestDTO);
        FriendRequest friendRequest = friendRequestMapper.toEntity(friendRequestDTO);
        friendRequest = friendRequestRepository.save(friendRequest);
        FriendRequestDTO result = friendRequestMapper.toDto(friendRequest);
        friendRequestSearchRepository.save(friendRequest);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FriendRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FriendRequests");
        return friendRequestRepository.findAll(pageable).map(friendRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FriendRequestDTO> findOne(Long id) {
        log.debug("Request to get FriendRequest : {}", id);
        return friendRequestRepository.findById(id).map(friendRequestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FriendRequest : {}", id);
        friendRequestRepository.deleteById(id);
        friendRequestSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FriendRequestDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FriendRequests for query {}", query);
        return friendRequestSearchRepository.search(queryStringQuery(query), pageable).map(friendRequestMapper::toDto);
    }
}
