package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.Language;
import com.mindforme.app.repository.LanguageRepository;
import com.mindforme.app.repository.search.LanguageSearchRepository;
import com.mindforme.app.service.LanguageService;
import com.mindforme.app.service.dto.LanguageDTO;
import com.mindforme.app.service.mapper.LanguageMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Language}.
 */
@Service
@Transactional
public class LanguageServiceImpl implements LanguageService {
    private final Logger log = LoggerFactory.getLogger(LanguageServiceImpl.class);

    private final LanguageRepository languageRepository;

    private final LanguageMapper languageMapper;

    private final LanguageSearchRepository languageSearchRepository;

    public LanguageServiceImpl(
        LanguageRepository languageRepository,
        LanguageMapper languageMapper,
        LanguageSearchRepository languageSearchRepository
    ) {
        this.languageRepository = languageRepository;
        this.languageMapper = languageMapper;
        this.languageSearchRepository = languageSearchRepository;
    }

    @Override
    public LanguageDTO save(LanguageDTO languageDTO) {
        log.debug("Request to save Language : {}", languageDTO);
        Language language = languageMapper.toEntity(languageDTO);
        language = languageRepository.save(language);
        LanguageDTO result = languageMapper.toDto(language);
        languageSearchRepository.save(language);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LanguageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Languages");
        return languageRepository.findAll(pageable).map(languageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LanguageDTO> findOne(Long id) {
        log.debug("Request to get Language : {}", id);
        return languageRepository.findById(id).map(languageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Language : {}", id);
        languageRepository.deleteById(id);
        languageSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LanguageDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Languages for query {}", query);
        return languageSearchRepository.search(queryStringQuery(query), pageable).map(languageMapper::toDto);
    }
}
