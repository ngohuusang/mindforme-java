package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.Friendship;
import com.mindforme.app.repository.FriendshipRepository;
import com.mindforme.app.repository.search.FriendshipSearchRepository;
import com.mindforme.app.service.FriendshipService;
import com.mindforme.app.service.dto.FriendshipDTO;
import com.mindforme.app.service.mapper.FriendshipMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Friendship}.
 */
@Service
@Transactional
public class FriendshipServiceImpl implements FriendshipService {
    private final Logger log = LoggerFactory.getLogger(FriendshipServiceImpl.class);

    private final FriendshipRepository friendshipRepository;

    private final FriendshipMapper friendshipMapper;

    private final FriendshipSearchRepository friendshipSearchRepository;

    public FriendshipServiceImpl(
        FriendshipRepository friendshipRepository,
        FriendshipMapper friendshipMapper,
        FriendshipSearchRepository friendshipSearchRepository
    ) {
        this.friendshipRepository = friendshipRepository;
        this.friendshipMapper = friendshipMapper;
        this.friendshipSearchRepository = friendshipSearchRepository;
    }

    @Override
    public FriendshipDTO save(FriendshipDTO friendshipDTO) {
        log.debug("Request to save Friendship : {}", friendshipDTO);
        Friendship friendship = friendshipMapper.toEntity(friendshipDTO);
        friendship = friendshipRepository.save(friendship);
        FriendshipDTO result = friendshipMapper.toDto(friendship);
        friendshipSearchRepository.save(friendship);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FriendshipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Friendships");
        return friendshipRepository.findAll(pageable).map(friendshipMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FriendshipDTO> findOne(Long id) {
        log.debug("Request to get Friendship : {}", id);
        return friendshipRepository.findById(id).map(friendshipMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Friendship : {}", id);
        friendshipRepository.deleteById(id);
        friendshipSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FriendshipDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Friendships for query {}", query);
        return friendshipSearchRepository.search(queryStringQuery(query), pageable).map(friendshipMapper::toDto);
    }
}
