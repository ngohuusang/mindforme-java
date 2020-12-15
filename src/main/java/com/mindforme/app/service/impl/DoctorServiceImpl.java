package com.mindforme.app.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mindforme.app.domain.Doctor;
import com.mindforme.app.repository.DoctorRepository;
import com.mindforme.app.repository.search.DoctorSearchRepository;
import com.mindforme.app.service.DoctorService;
import com.mindforme.app.service.dto.DoctorDTO;
import com.mindforme.app.service.mapper.DoctorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Doctor}.
 */
@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {
    private final Logger log = LoggerFactory.getLogger(DoctorServiceImpl.class);

    private final DoctorRepository doctorRepository;

    private final DoctorMapper doctorMapper;

    private final DoctorSearchRepository doctorSearchRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository, DoctorMapper doctorMapper, DoctorSearchRepository doctorSearchRepository) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
        this.doctorSearchRepository = doctorSearchRepository;
    }

    @Override
    public DoctorDTO save(DoctorDTO doctorDTO) {
        log.debug("Request to save Doctor : {}", doctorDTO);
        Doctor doctor = doctorMapper.toEntity(doctorDTO);
        doctor = doctorRepository.save(doctor);
        DoctorDTO result = doctorMapper.toDto(doctor);
        doctorSearchRepository.save(doctor);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Doctors");
        return doctorRepository.findAll(pageable).map(doctorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DoctorDTO> findOne(Long id) {
        log.debug("Request to get Doctor : {}", id);
        return doctorRepository.findById(id).map(doctorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Doctor : {}", id);
        doctorRepository.deleteById(id);
        doctorSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Doctors for query {}", query);
        return doctorSearchRepository.search(queryStringQuery(query), pageable).map(doctorMapper::toDto);
    }
}
