package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.FamilyGallery;
import com.mindforme.app.repository.FamilyGalleryRepository;
import com.mindforme.app.repository.search.FamilyGallerySearchRepository;
import com.mindforme.app.service.FamilyGalleryService;
import com.mindforme.app.service.dto.FamilyGalleryDTO;
import com.mindforme.app.service.mapper.FamilyGalleryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FamilyGallery}.
 */
@Service
@Transactional
public class FamilyGalleryServiceImpl implements FamilyGalleryService {
    private final Logger log = LoggerFactory.getLogger(FamilyGalleryServiceImpl.class);

    private final FamilyGalleryRepository familyGalleryRepository;

    private final FamilyGalleryMapper familyGalleryMapper;

    private final FamilyGallerySearchRepository familyGallerySearchRepository;

    public FamilyGalleryServiceImpl(
        FamilyGalleryRepository familyGalleryRepository,
        FamilyGalleryMapper familyGalleryMapper,
        FamilyGallerySearchRepository familyGallerySearchRepository
    ) {
        this.familyGalleryRepository = familyGalleryRepository;
        this.familyGalleryMapper = familyGalleryMapper;
        this.familyGallerySearchRepository = familyGallerySearchRepository;
    }

    @Override
    public FamilyGalleryDTO save(FamilyGalleryDTO familyGalleryDTO) {
        log.debug("Request to save FamilyGallery : {}", familyGalleryDTO);
        FamilyGallery familyGallery = familyGalleryMapper.toEntity(familyGalleryDTO);
        familyGallery = familyGalleryRepository.save(familyGallery);
        FamilyGalleryDTO result = familyGalleryMapper.toDto(familyGallery);
        familyGallerySearchRepository.save(familyGallery);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FamilyGalleryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FamilyGalleries");
        return familyGalleryRepository.findAll(pageable).map(familyGalleryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FamilyGalleryDTO> findOne(Long id) {
        log.debug("Request to get FamilyGallery : {}", id);
        return familyGalleryRepository.findById(id).map(familyGalleryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FamilyGallery : {}", id);
        familyGalleryRepository.deleteById(id);
        familyGallerySearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FamilyGalleryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FamilyGalleries for query {}", query);
        return familyGallerySearchRepository.search(queryStringQuery(query), pageable).map(familyGalleryMapper::toDto);
    }
}
