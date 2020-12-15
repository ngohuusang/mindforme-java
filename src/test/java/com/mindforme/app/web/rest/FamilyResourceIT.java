package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.Family;
import com.mindforme.app.domain.enumeration.DistanceUnit;
import com.mindforme.app.domain.enumeration.Frequency;
import com.mindforme.app.domain.enumeration.Frequency;
import com.mindforme.app.domain.enumeration.Frequency;
import com.mindforme.app.domain.enumeration.Privacy;
import com.mindforme.app.domain.enumeration.Privacy;
import com.mindforme.app.repository.FamilyRepository;
import com.mindforme.app.repository.search.FamilySearchRepository;
import com.mindforme.app.service.FamilyService;
import com.mindforme.app.service.dto.FamilyDTO;
import com.mindforme.app.service.mapper.FamilyMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link FamilyResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class FamilyResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_KARMA_POINTS = 1;
    private static final Integer UPDATED_KARMA_POINTS = 2;

    private static final String DEFAULT_OVERVIEW = "AAAAAAAAAA";
    private static final String UPDATED_OVERVIEW = "BBBBBBBBBB";

    private static final String DEFAULT_RATING = "AAAAAAAAAA";
    private static final String UPDATED_RATING = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Instant DEFAULT_PLAN_EXPIRE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PLAN_EXPIRE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RULE = "AAAAAAAAAA";
    private static final String UPDATED_RULE = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_MONTHS = "AAAAAAAAAA";
    private static final String UPDATED_FREE_MONTHS = "BBBBBBBBBB";

    private static final Integer DEFAULT_OTHER_VERIFY = 1;
    private static final Integer UPDATED_OTHER_VERIFY = 2;

    private static final Boolean DEFAULT_KC_25_PAID = false;
    private static final Boolean UPDATED_KC_25_PAID = true;

    private static final Boolean DEFAULT_KC_75_PAID = false;
    private static final Boolean UPDATED_KC_75_PAID = true;

    private static final Privacy DEFAULT_PRIVACY_FAMILY = Privacy.PUBLIC;
    private static final Privacy UPDATED_PRIVACY_FAMILY = Privacy.FRIENDS;

    private static final Boolean DEFAULT_SHARE_TO_FACEBOOK = false;
    private static final Boolean UPDATED_SHARE_TO_FACEBOOK = true;

    private static final Privacy DEFAULT_PRIVACY_PERSONAL = Privacy.PUBLIC;
    private static final Privacy UPDATED_PRIVACY_PERSONAL = Privacy.FRIENDS;

    private static final Boolean DEFAULT_ADD_TO_CALENDAR = false;
    private static final Boolean UPDATED_ADD_TO_CALENDAR = true;

    private static final Boolean DEFAULT_REMIND_EVENTS = false;
    private static final Boolean UPDATED_REMIND_EVENTS = true;

    private static final Boolean DEFAULT_NOTIFY_FACEBOOK = false;
    private static final Boolean UPDATED_NOTIFY_FACEBOOK = true;

    private static final Float DEFAULT_DISTANCE_REQUEST = 1F;
    private static final Float UPDATED_DISTANCE_REQUEST = 2F;

    private static final DistanceUnit DEFAULT_DISTANCE_UNIT = DistanceUnit.KM;
    private static final DistanceUnit UPDATED_DISTANCE_UNIT = DistanceUnit.MILE;

    private static final Frequency DEFAULT_MAIL_REQUEST_FRIEND = Frequency.IMMEDIATELY;
    private static final Frequency UPDATED_MAIL_REQUEST_FRIEND = Frequency.DAILY;

    private static final Frequency DEFAULT_MAIL_REQUEST_FRIEND_OF_FRIEND = Frequency.IMMEDIATELY;
    private static final Frequency UPDATED_MAIL_REQUEST_FRIEND_OF_FRIEND = Frequency.DAILY;

    private static final Frequency DEFAULT_MAIL_REQUEST = Frequency.IMMEDIATELY;
    private static final Frequency UPDATED_MAIL_REQUEST = Frequency.DAILY;

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private FamilyMapper familyMapper;

    @Autowired
    private FamilyService familyService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.FamilySearchRepositoryMockConfiguration
     */
    @Autowired
    private FamilySearchRepository mockFamilySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFamilyMockMvc;

    private Family family;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Family createEntity(EntityManager em) {
        Family family = new Family()
            .name(DEFAULT_NAME)
            .karmaPoints(DEFAULT_KARMA_POINTS)
            .overview(DEFAULT_OVERVIEW)
            .rating(DEFAULT_RATING)
            .imageUrl(DEFAULT_IMAGE_URL)
            .planExpire(DEFAULT_PLAN_EXPIRE)
            .rule(DEFAULT_RULE)
            .freeMonths(DEFAULT_FREE_MONTHS)
            .otherVerify(DEFAULT_OTHER_VERIFY)
            .kc25Paid(DEFAULT_KC_25_PAID)
            .kc75Paid(DEFAULT_KC_75_PAID)
            .privacyFamily(DEFAULT_PRIVACY_FAMILY)
            .shareToFacebook(DEFAULT_SHARE_TO_FACEBOOK)
            .privacyPersonal(DEFAULT_PRIVACY_PERSONAL)
            .addToCalendar(DEFAULT_ADD_TO_CALENDAR)
            .remindEvents(DEFAULT_REMIND_EVENTS)
            .notifyFacebook(DEFAULT_NOTIFY_FACEBOOK)
            .distanceRequest(DEFAULT_DISTANCE_REQUEST)
            .distanceUnit(DEFAULT_DISTANCE_UNIT)
            .mailRequestFriend(DEFAULT_MAIL_REQUEST_FRIEND)
            .mailRequestFriendOfFriend(DEFAULT_MAIL_REQUEST_FRIEND_OF_FRIEND)
            .mailRequest(DEFAULT_MAIL_REQUEST);
        return family;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Family createUpdatedEntity(EntityManager em) {
        Family family = new Family()
            .name(UPDATED_NAME)
            .karmaPoints(UPDATED_KARMA_POINTS)
            .overview(UPDATED_OVERVIEW)
            .rating(UPDATED_RATING)
            .imageUrl(UPDATED_IMAGE_URL)
            .planExpire(UPDATED_PLAN_EXPIRE)
            .rule(UPDATED_RULE)
            .freeMonths(UPDATED_FREE_MONTHS)
            .otherVerify(UPDATED_OTHER_VERIFY)
            .kc25Paid(UPDATED_KC_25_PAID)
            .kc75Paid(UPDATED_KC_75_PAID)
            .privacyFamily(UPDATED_PRIVACY_FAMILY)
            .shareToFacebook(UPDATED_SHARE_TO_FACEBOOK)
            .privacyPersonal(UPDATED_PRIVACY_PERSONAL)
            .addToCalendar(UPDATED_ADD_TO_CALENDAR)
            .remindEvents(UPDATED_REMIND_EVENTS)
            .notifyFacebook(UPDATED_NOTIFY_FACEBOOK)
            .distanceRequest(UPDATED_DISTANCE_REQUEST)
            .distanceUnit(UPDATED_DISTANCE_UNIT)
            .mailRequestFriend(UPDATED_MAIL_REQUEST_FRIEND)
            .mailRequestFriendOfFriend(UPDATED_MAIL_REQUEST_FRIEND_OF_FRIEND)
            .mailRequest(UPDATED_MAIL_REQUEST);
        return family;
    }

    @BeforeEach
    public void initTest() {
        family = createEntity(em);
    }

    @Test
    @Transactional
    public void createFamily() throws Exception {
        int databaseSizeBeforeCreate = familyRepository.findAll().size();
        // Create the Family
        FamilyDTO familyDTO = familyMapper.toDto(family);
        restFamilyMockMvc
            .perform(post("/api/families").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(familyDTO)))
            .andExpect(status().isCreated());

        // Validate the Family in the database
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeCreate + 1);
        Family testFamily = familyList.get(familyList.size() - 1);
        assertThat(testFamily.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFamily.getKarmaPoints()).isEqualTo(DEFAULT_KARMA_POINTS);
        assertThat(testFamily.getOverview()).isEqualTo(DEFAULT_OVERVIEW);
        assertThat(testFamily.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testFamily.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testFamily.getPlanExpire()).isEqualTo(DEFAULT_PLAN_EXPIRE);
        assertThat(testFamily.getRule()).isEqualTo(DEFAULT_RULE);
        assertThat(testFamily.getFreeMonths()).isEqualTo(DEFAULT_FREE_MONTHS);
        assertThat(testFamily.getOtherVerify()).isEqualTo(DEFAULT_OTHER_VERIFY);
        assertThat(testFamily.isKc25Paid()).isEqualTo(DEFAULT_KC_25_PAID);
        assertThat(testFamily.isKc75Paid()).isEqualTo(DEFAULT_KC_75_PAID);
        assertThat(testFamily.getPrivacyFamily()).isEqualTo(DEFAULT_PRIVACY_FAMILY);
        assertThat(testFamily.isShareToFacebook()).isEqualTo(DEFAULT_SHARE_TO_FACEBOOK);
        assertThat(testFamily.getPrivacyPersonal()).isEqualTo(DEFAULT_PRIVACY_PERSONAL);
        assertThat(testFamily.isAddToCalendar()).isEqualTo(DEFAULT_ADD_TO_CALENDAR);
        assertThat(testFamily.isRemindEvents()).isEqualTo(DEFAULT_REMIND_EVENTS);
        assertThat(testFamily.isNotifyFacebook()).isEqualTo(DEFAULT_NOTIFY_FACEBOOK);
        assertThat(testFamily.getDistanceRequest()).isEqualTo(DEFAULT_DISTANCE_REQUEST);
        assertThat(testFamily.getDistanceUnit()).isEqualTo(DEFAULT_DISTANCE_UNIT);
        assertThat(testFamily.getMailRequestFriend()).isEqualTo(DEFAULT_MAIL_REQUEST_FRIEND);
        assertThat(testFamily.getMailRequestFriendOfFriend()).isEqualTo(DEFAULT_MAIL_REQUEST_FRIEND_OF_FRIEND);
        assertThat(testFamily.getMailRequest()).isEqualTo(DEFAULT_MAIL_REQUEST);

        // Validate the Family in Elasticsearch
        verify(mockFamilySearchRepository, times(1)).save(testFamily);
    }

    @Test
    @Transactional
    public void createFamilyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = familyRepository.findAll().size();

        // Create the Family with an existing ID
        family.setId(1L);
        FamilyDTO familyDTO = familyMapper.toDto(family);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamilyMockMvc
            .perform(post("/api/families").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(familyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Family in the database
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeCreate);

        // Validate the Family in Elasticsearch
        verify(mockFamilySearchRepository, times(0)).save(family);
    }

    @Test
    @Transactional
    public void getAllFamilies() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        // Get all the familyList
        restFamilyMockMvc
            .perform(get("/api/families?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(family.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].karmaPoints").value(hasItem(DEFAULT_KARMA_POINTS)))
            .andExpect(jsonPath("$.[*].overview").value(hasItem(DEFAULT_OVERVIEW)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].planExpire").value(hasItem(DEFAULT_PLAN_EXPIRE.toString())))
            .andExpect(jsonPath("$.[*].rule").value(hasItem(DEFAULT_RULE.toString())))
            .andExpect(jsonPath("$.[*].freeMonths").value(hasItem(DEFAULT_FREE_MONTHS)))
            .andExpect(jsonPath("$.[*].otherVerify").value(hasItem(DEFAULT_OTHER_VERIFY)))
            .andExpect(jsonPath("$.[*].kc25Paid").value(hasItem(DEFAULT_KC_25_PAID.booleanValue())))
            .andExpect(jsonPath("$.[*].kc75Paid").value(hasItem(DEFAULT_KC_75_PAID.booleanValue())))
            .andExpect(jsonPath("$.[*].privacyFamily").value(hasItem(DEFAULT_PRIVACY_FAMILY.toString())))
            .andExpect(jsonPath("$.[*].shareToFacebook").value(hasItem(DEFAULT_SHARE_TO_FACEBOOK.booleanValue())))
            .andExpect(jsonPath("$.[*].privacyPersonal").value(hasItem(DEFAULT_PRIVACY_PERSONAL.toString())))
            .andExpect(jsonPath("$.[*].addToCalendar").value(hasItem(DEFAULT_ADD_TO_CALENDAR.booleanValue())))
            .andExpect(jsonPath("$.[*].remindEvents").value(hasItem(DEFAULT_REMIND_EVENTS.booleanValue())))
            .andExpect(jsonPath("$.[*].notifyFacebook").value(hasItem(DEFAULT_NOTIFY_FACEBOOK.booleanValue())))
            .andExpect(jsonPath("$.[*].distanceRequest").value(hasItem(DEFAULT_DISTANCE_REQUEST.doubleValue())))
            .andExpect(jsonPath("$.[*].distanceUnit").value(hasItem(DEFAULT_DISTANCE_UNIT.toString())))
            .andExpect(jsonPath("$.[*].mailRequestFriend").value(hasItem(DEFAULT_MAIL_REQUEST_FRIEND.toString())))
            .andExpect(jsonPath("$.[*].mailRequestFriendOfFriend").value(hasItem(DEFAULT_MAIL_REQUEST_FRIEND_OF_FRIEND.toString())))
            .andExpect(jsonPath("$.[*].mailRequest").value(hasItem(DEFAULT_MAIL_REQUEST.toString())));
    }

    @Test
    @Transactional
    public void getFamily() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        // Get the family
        restFamilyMockMvc
            .perform(get("/api/families/{id}", family.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(family.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.karmaPoints").value(DEFAULT_KARMA_POINTS))
            .andExpect(jsonPath("$.overview").value(DEFAULT_OVERVIEW))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.planExpire").value(DEFAULT_PLAN_EXPIRE.toString()))
            .andExpect(jsonPath("$.rule").value(DEFAULT_RULE.toString()))
            .andExpect(jsonPath("$.freeMonths").value(DEFAULT_FREE_MONTHS))
            .andExpect(jsonPath("$.otherVerify").value(DEFAULT_OTHER_VERIFY))
            .andExpect(jsonPath("$.kc25Paid").value(DEFAULT_KC_25_PAID.booleanValue()))
            .andExpect(jsonPath("$.kc75Paid").value(DEFAULT_KC_75_PAID.booleanValue()))
            .andExpect(jsonPath("$.privacyFamily").value(DEFAULT_PRIVACY_FAMILY.toString()))
            .andExpect(jsonPath("$.shareToFacebook").value(DEFAULT_SHARE_TO_FACEBOOK.booleanValue()))
            .andExpect(jsonPath("$.privacyPersonal").value(DEFAULT_PRIVACY_PERSONAL.toString()))
            .andExpect(jsonPath("$.addToCalendar").value(DEFAULT_ADD_TO_CALENDAR.booleanValue()))
            .andExpect(jsonPath("$.remindEvents").value(DEFAULT_REMIND_EVENTS.booleanValue()))
            .andExpect(jsonPath("$.notifyFacebook").value(DEFAULT_NOTIFY_FACEBOOK.booleanValue()))
            .andExpect(jsonPath("$.distanceRequest").value(DEFAULT_DISTANCE_REQUEST.doubleValue()))
            .andExpect(jsonPath("$.distanceUnit").value(DEFAULT_DISTANCE_UNIT.toString()))
            .andExpect(jsonPath("$.mailRequestFriend").value(DEFAULT_MAIL_REQUEST_FRIEND.toString()))
            .andExpect(jsonPath("$.mailRequestFriendOfFriend").value(DEFAULT_MAIL_REQUEST_FRIEND_OF_FRIEND.toString()))
            .andExpect(jsonPath("$.mailRequest").value(DEFAULT_MAIL_REQUEST.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFamily() throws Exception {
        // Get the family
        restFamilyMockMvc.perform(get("/api/families/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFamily() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        int databaseSizeBeforeUpdate = familyRepository.findAll().size();

        // Update the family
        Family updatedFamily = familyRepository.findById(family.getId()).get();
        // Disconnect from session so that the updates on updatedFamily are not directly saved in db
        em.detach(updatedFamily);
        updatedFamily
            .name(UPDATED_NAME)
            .karmaPoints(UPDATED_KARMA_POINTS)
            .overview(UPDATED_OVERVIEW)
            .rating(UPDATED_RATING)
            .imageUrl(UPDATED_IMAGE_URL)
            .planExpire(UPDATED_PLAN_EXPIRE)
            .rule(UPDATED_RULE)
            .freeMonths(UPDATED_FREE_MONTHS)
            .otherVerify(UPDATED_OTHER_VERIFY)
            .kc25Paid(UPDATED_KC_25_PAID)
            .kc75Paid(UPDATED_KC_75_PAID)
            .privacyFamily(UPDATED_PRIVACY_FAMILY)
            .shareToFacebook(UPDATED_SHARE_TO_FACEBOOK)
            .privacyPersonal(UPDATED_PRIVACY_PERSONAL)
            .addToCalendar(UPDATED_ADD_TO_CALENDAR)
            .remindEvents(UPDATED_REMIND_EVENTS)
            .notifyFacebook(UPDATED_NOTIFY_FACEBOOK)
            .distanceRequest(UPDATED_DISTANCE_REQUEST)
            .distanceUnit(UPDATED_DISTANCE_UNIT)
            .mailRequestFriend(UPDATED_MAIL_REQUEST_FRIEND)
            .mailRequestFriendOfFriend(UPDATED_MAIL_REQUEST_FRIEND_OF_FRIEND)
            .mailRequest(UPDATED_MAIL_REQUEST);
        FamilyDTO familyDTO = familyMapper.toDto(updatedFamily);

        restFamilyMockMvc
            .perform(put("/api/families").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(familyDTO)))
            .andExpect(status().isOk());

        // Validate the Family in the database
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeUpdate);
        Family testFamily = familyList.get(familyList.size() - 1);
        assertThat(testFamily.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFamily.getKarmaPoints()).isEqualTo(UPDATED_KARMA_POINTS);
        assertThat(testFamily.getOverview()).isEqualTo(UPDATED_OVERVIEW);
        assertThat(testFamily.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testFamily.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testFamily.getPlanExpire()).isEqualTo(UPDATED_PLAN_EXPIRE);
        assertThat(testFamily.getRule()).isEqualTo(UPDATED_RULE);
        assertThat(testFamily.getFreeMonths()).isEqualTo(UPDATED_FREE_MONTHS);
        assertThat(testFamily.getOtherVerify()).isEqualTo(UPDATED_OTHER_VERIFY);
        assertThat(testFamily.isKc25Paid()).isEqualTo(UPDATED_KC_25_PAID);
        assertThat(testFamily.isKc75Paid()).isEqualTo(UPDATED_KC_75_PAID);
        assertThat(testFamily.getPrivacyFamily()).isEqualTo(UPDATED_PRIVACY_FAMILY);
        assertThat(testFamily.isShareToFacebook()).isEqualTo(UPDATED_SHARE_TO_FACEBOOK);
        assertThat(testFamily.getPrivacyPersonal()).isEqualTo(UPDATED_PRIVACY_PERSONAL);
        assertThat(testFamily.isAddToCalendar()).isEqualTo(UPDATED_ADD_TO_CALENDAR);
        assertThat(testFamily.isRemindEvents()).isEqualTo(UPDATED_REMIND_EVENTS);
        assertThat(testFamily.isNotifyFacebook()).isEqualTo(UPDATED_NOTIFY_FACEBOOK);
        assertThat(testFamily.getDistanceRequest()).isEqualTo(UPDATED_DISTANCE_REQUEST);
        assertThat(testFamily.getDistanceUnit()).isEqualTo(UPDATED_DISTANCE_UNIT);
        assertThat(testFamily.getMailRequestFriend()).isEqualTo(UPDATED_MAIL_REQUEST_FRIEND);
        assertThat(testFamily.getMailRequestFriendOfFriend()).isEqualTo(UPDATED_MAIL_REQUEST_FRIEND_OF_FRIEND);
        assertThat(testFamily.getMailRequest()).isEqualTo(UPDATED_MAIL_REQUEST);

        // Validate the Family in Elasticsearch
        verify(mockFamilySearchRepository, times(1)).save(testFamily);
    }

    @Test
    @Transactional
    public void updateNonExistingFamily() throws Exception {
        int databaseSizeBeforeUpdate = familyRepository.findAll().size();

        // Create the Family
        FamilyDTO familyDTO = familyMapper.toDto(family);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilyMockMvc
            .perform(put("/api/families").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(familyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Family in the database
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Family in Elasticsearch
        verify(mockFamilySearchRepository, times(0)).save(family);
    }

    @Test
    @Transactional
    public void deleteFamily() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        int databaseSizeBeforeDelete = familyRepository.findAll().size();

        // Delete the family
        restFamilyMockMvc
            .perform(delete("/api/families/{id}", family.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Family in Elasticsearch
        verify(mockFamilySearchRepository, times(1)).deleteById(family.getId());
    }

    @Test
    @Transactional
    public void searchFamily() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        familyRepository.saveAndFlush(family);
        when(mockFamilySearchRepository.search(queryStringQuery("id:" + family.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(family), PageRequest.of(0, 1), 1));

        // Search the family
        restFamilyMockMvc
            .perform(get("/api/_search/families?query=id:" + family.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(family.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].karmaPoints").value(hasItem(DEFAULT_KARMA_POINTS)))
            .andExpect(jsonPath("$.[*].overview").value(hasItem(DEFAULT_OVERVIEW)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].planExpire").value(hasItem(DEFAULT_PLAN_EXPIRE.toString())))
            .andExpect(jsonPath("$.[*].rule").value(hasItem(DEFAULT_RULE.toString())))
            .andExpect(jsonPath("$.[*].freeMonths").value(hasItem(DEFAULT_FREE_MONTHS)))
            .andExpect(jsonPath("$.[*].otherVerify").value(hasItem(DEFAULT_OTHER_VERIFY)))
            .andExpect(jsonPath("$.[*].kc25Paid").value(hasItem(DEFAULT_KC_25_PAID.booleanValue())))
            .andExpect(jsonPath("$.[*].kc75Paid").value(hasItem(DEFAULT_KC_75_PAID.booleanValue())))
            .andExpect(jsonPath("$.[*].privacyFamily").value(hasItem(DEFAULT_PRIVACY_FAMILY.toString())))
            .andExpect(jsonPath("$.[*].shareToFacebook").value(hasItem(DEFAULT_SHARE_TO_FACEBOOK.booleanValue())))
            .andExpect(jsonPath("$.[*].privacyPersonal").value(hasItem(DEFAULT_PRIVACY_PERSONAL.toString())))
            .andExpect(jsonPath("$.[*].addToCalendar").value(hasItem(DEFAULT_ADD_TO_CALENDAR.booleanValue())))
            .andExpect(jsonPath("$.[*].remindEvents").value(hasItem(DEFAULT_REMIND_EVENTS.booleanValue())))
            .andExpect(jsonPath("$.[*].notifyFacebook").value(hasItem(DEFAULT_NOTIFY_FACEBOOK.booleanValue())))
            .andExpect(jsonPath("$.[*].distanceRequest").value(hasItem(DEFAULT_DISTANCE_REQUEST.doubleValue())))
            .andExpect(jsonPath("$.[*].distanceUnit").value(hasItem(DEFAULT_DISTANCE_UNIT.toString())))
            .andExpect(jsonPath("$.[*].mailRequestFriend").value(hasItem(DEFAULT_MAIL_REQUEST_FRIEND.toString())))
            .andExpect(jsonPath("$.[*].mailRequestFriendOfFriend").value(hasItem(DEFAULT_MAIL_REQUEST_FRIEND_OF_FRIEND.toString())))
            .andExpect(jsonPath("$.[*].mailRequest").value(hasItem(DEFAULT_MAIL_REQUEST.toString())));
    }
}
