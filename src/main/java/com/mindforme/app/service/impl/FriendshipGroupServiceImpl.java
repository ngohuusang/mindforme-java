package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.FriendshipGroup;
import com.mindforme.app.repository.FriendshipGroupRepository;
import com.mindforme.app.repository.search.FriendshipGroupSearchRepository;
import com.mindforme.app.service.FriendshipGroupService;
import com.mindforme.app.service.dto.FriendshipGroupDTO;
import com.mindforme.app.service.mapper.FriendshipGroupMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FriendshipGroup}.
 */
@Service
@Transactional
public class FriendshipGroupServiceImpl implements FriendshipGroupService {
    private final Logger log = LoggerFactory.getLogger(FriendshipGroupServiceImpl.class);

    private final FriendshipGroupRepository friendshipGroupRepository;

    private final FriendshipGroupMapper friendshipGroupMapper;

    private final FriendshipGroupSearchRepository friendshipGroupSearchRepository;

    public FriendshipGroupServiceImpl(
        FriendshipGroupRepository friendshipGroupRepository,
        FriendshipGroupMapper friendshipGroupMapper,
        FriendshipGroupSearchRepository friendshipGroupSearchRepository
    ) {
        this.friendshipGroupRepository = friendshipGroupRepository;
        this.friendshipGroupMapper = friendshipGroupMapper;
        this.friendshipGroupSearchRepository = friendshipGroupSearchRepository;
    }

    @Override
    public FriendshipGroupDTO save(FriendshipGroupDTO friendshipGroupDTO) {
        log.debug("Request to save FriendshipGroup : {}", friendshipGroupDTO);
        FriendshipGroup friendshipGroup = friendshipGroupMapper.toEntity(friendshipGroupDTO);
        friendshipGroup = friendshipGroupRepository.save(friendshipGroup);
        FriendshipGroupDTO result = friendshipGroupMapper.toDto(friendshipGroup);
        friendshipGroupSearchRepository.save(friendshipGroup);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FriendshipGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FriendshipGroups");
        return friendshipGroupRepository.findAll(pageable).map(friendshipGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FriendshipGroupDTO> findOne(Long id) {
        log.debug("Request to get FriendshipGroup : {}", id);
        return friendshipGroupRepository.findById(id).map(friendshipGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FriendshipGroup : {}", id);
        friendshipGroupRepository.deleteById(id);
        friendshipGroupSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FriendshipGroupDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FriendshipGroups for query {}", query);
        return friendshipGroupSearchRepository.search(queryStringQuery(query), pageable).map(friendshipGroupMapper::toDto);
    }
}
