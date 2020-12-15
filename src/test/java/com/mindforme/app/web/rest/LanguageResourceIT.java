package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.Language;
import com.mindforme.app.repository.LanguageRepository;
import com.mindforme.app.repository.search.LanguageSearchRepository;
import com.mindforme.app.service.LanguageService;
import com.mindforme.app.service.dto.LanguageDTO;
import com.mindforme.app.service.mapper.LanguageMapper;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LanguageResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class LanguageResourceIT {
    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DEFAULT = false;
    private static final Boolean UPDATED_IS_DEFAULT = true;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private LanguageMapper languageMapper;

    @Autowired
    private LanguageService languageService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.LanguageSearchRepositoryMockConfiguration
     */
    @Autowired
    private LanguageSearchRepository mockLanguageSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLanguageMockMvc;

    private Language language;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Language createEntity(EntityManager em) {
        Language language = new Language().code(DEFAULT_CODE).name(DEFAULT_NAME).isDefault(DEFAULT_IS_DEFAULT).active(DEFAULT_ACTIVE);
        return language;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Language createUpdatedEntity(EntityManager em) {
        Language language = new Language().code(UPDATED_CODE).name(UPDATED_NAME).isDefault(UPDATED_IS_DEFAULT).active(UPDATED_ACTIVE);
        return language;
    }

    @BeforeEach
    public void initTest() {
        language = createEntity(em);
    }

    @Test
    @Transactional
    public void createLanguage() throws Exception {
        int databaseSizeBeforeCreate = languageRepository.findAll().size();
        // Create the Language
        LanguageDTO languageDTO = languageMapper.toDto(language);
        restLanguageMockMvc
            .perform(post("/api/languages").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(languageDTO)))
            .andExpect(status().isCreated());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeCreate + 1);
        Language testLanguage = languageList.get(languageList.size() - 1);
        assertThat(testLanguage.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLanguage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLanguage.isIsDefault()).isEqualTo(DEFAULT_IS_DEFAULT);
        assertThat(testLanguage.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the Language in Elasticsearch
        verify(mockLanguageSearchRepository, times(1)).save(testLanguage);
    }

    @Test
    @Transactional
    public void createLanguageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = languageRepository.findAll().size();

        // Create the Language with an existing ID
        language.setId(1L);
        LanguageDTO languageDTO = languageMapper.toDto(language);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLanguageMockMvc
            .perform(post("/api/languages").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(languageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeCreate);

        // Validate the Language in Elasticsearch
        verify(mockLanguageSearchRepository, times(0)).save(language);
    }

    @Test
    @Transactional
    public void getAllLanguages() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList
        restLanguageMockMvc
            .perform(get("/api/languages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(language.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isDefault").value(hasItem(DEFAULT_IS_DEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getLanguage() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get the language
        restLanguageMockMvc
            .perform(get("/api/languages/{id}", language.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(language.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.isDefault").value(DEFAULT_IS_DEFAULT.booleanValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLanguage() throws Exception {
        // Get the language
        restLanguageMockMvc.perform(get("/api/languages/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLanguage() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        int databaseSizeBeforeUpdate = languageRepository.findAll().size();

        // Update the language
        Language updatedLanguage = languageRepository.findById(language.getId()).get();
        // Disconnect from session so that the updates on updatedLanguage are not directly saved in db
        em.detach(updatedLanguage);
        updatedLanguage.code(UPDATED_CODE).name(UPDATED_NAME).isDefault(UPDATED_IS_DEFAULT).active(UPDATED_ACTIVE);
        LanguageDTO languageDTO = languageMapper.toDto(updatedLanguage);

        restLanguageMockMvc
            .perform(put("/api/languages").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(languageDTO)))
            .andExpect(status().isOk());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
        Language testLanguage = languageList.get(languageList.size() - 1);
        assertThat(testLanguage.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLanguage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLanguage.isIsDefault()).isEqualTo(UPDATED_IS_DEFAULT);
        assertThat(testLanguage.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the Language in Elasticsearch
        verify(mockLanguageSearchRepository, times(1)).save(testLanguage);
    }

    @Test
    @Transactional
    public void updateNonExistingLanguage() throws Exception {
        int databaseSizeBeforeUpdate = languageRepository.findAll().size();

        // Create the Language
        LanguageDTO languageDTO = languageMapper.toDto(language);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLanguageMockMvc
            .perform(put("/api/languages").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(languageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Language in Elasticsearch
        verify(mockLanguageSearchRepository, times(0)).save(language);
    }

    @Test
    @Transactional
    public void deleteLanguage() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        int databaseSizeBeforeDelete = languageRepository.findAll().size();

        // Delete the language
        restLanguageMockMvc
            .perform(delete("/api/languages/{id}", language.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Language in Elasticsearch
        verify(mockLanguageSearchRepository, times(1)).deleteById(language.getId());
    }

    @Test
    @Transactional
    public void searchLanguage() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        languageRepository.saveAndFlush(language);
        when(mockLanguageSearchRepository.search(queryStringQuery("id:" + language.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(language), PageRequest.of(0, 1), 1));

        // Search the language
        restLanguageMockMvc
            .perform(get("/api/_search/languages?query=id:" + language.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(language.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isDefault").value(hasItem(DEFAULT_IS_DEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
}
