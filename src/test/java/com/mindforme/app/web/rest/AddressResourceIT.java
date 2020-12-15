package com.mindforme.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindforme.app.MindformeApp;
import com.mindforme.app.domain.Address;
import com.mindforme.app.domain.enumeration.Status;
import com.mindforme.app.repository.AddressRepository;
import com.mindforme.app.repository.search.AddressSearchRepository;
import com.mindforme.app.service.AddressService;
import com.mindforme.app.service.dto.AddressDTO;
import com.mindforme.app.service.mapper.AddressMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link AddressResource} REST controller.
 */
@SpringBootTest(classes = MindformeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AddressResourceIT {
    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_LATITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LATITUDE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LONGITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LONGITUDE = new BigDecimal(2);

    private static final String DEFAULT_LINE = "AAAAAAAAAA";
    private static final String UPDATED_LINE = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_POSTCODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_SUBURB = "AAAAAAAAAA";
    private static final String UPDATED_SUBURB = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.Active;
    private static final Status UPDATED_STATUS = Status.Inactive;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private AddressService addressService;

    /**
     * This repository is mocked in the com.mindforme.app.repository.search test package.
     *
     * @see com.mindforme.app.repository.search.AddressSearchRepositoryMockConfiguration
     */
    @Autowired
    private AddressSearchRepository mockAddressSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAddressMockMvc;

    private Address address;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createEntity(EntityManager em) {
        Address address = new Address()
            .address(DEFAULT_ADDRESS)
            .name(DEFAULT_NAME)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .line(DEFAULT_LINE)
            .unit(DEFAULT_UNIT)
            .number(DEFAULT_NUMBER)
            .street(DEFAULT_STREET)
            .postcode(DEFAULT_POSTCODE)
            .suburb(DEFAULT_SUBURB)
            .status(DEFAULT_STATUS);
        return address;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createUpdatedEntity(EntityManager em) {
        Address address = new Address()
            .address(UPDATED_ADDRESS)
            .name(UPDATED_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .line(UPDATED_LINE)
            .unit(UPDATED_UNIT)
            .number(UPDATED_NUMBER)
            .street(UPDATED_STREET)
            .postcode(UPDATED_POSTCODE)
            .suburb(UPDATED_SUBURB)
            .status(UPDATED_STATUS);
        return address;
    }

    @BeforeEach
    public void initTest() {
        address = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddress() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();
        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);
        restAddressMockMvc
            .perform(post("/api/addresses").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isCreated());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate + 1);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testAddress.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAddress.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testAddress.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testAddress.getLine()).isEqualTo(DEFAULT_LINE);
        assertThat(testAddress.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testAddress.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testAddress.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testAddress.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testAddress.getSuburb()).isEqualTo(DEFAULT_SUBURB);
        assertThat(testAddress.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Address in Elasticsearch
        verify(mockAddressSearchRepository, times(1)).save(testAddress);
    }

    @Test
    @Transactional
    public void createAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // Create the Address with an existing ID
        address.setId(1L);
        AddressDTO addressDTO = addressMapper.toDto(address);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressMockMvc
            .perform(post("/api/addresses").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate);

        // Validate the Address in Elasticsearch
        verify(mockAddressSearchRepository, times(0)).save(address);
    }

    @Test
    @Transactional
    public void getAllAddresses() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList
        restAddressMockMvc
            .perform(get("/api/addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.intValue())))
            .andExpect(jsonPath("$.[*].line").value(hasItem(DEFAULT_LINE)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)))
            .andExpect(jsonPath("$.[*].suburb").value(hasItem(DEFAULT_SUBURB)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get the address
        restAddressMockMvc
            .perform(get("/api/addresses/{id}", address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(address.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.intValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.intValue()))
            .andExpect(jsonPath("$.line").value(DEFAULT_LINE))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE))
            .andExpect(jsonPath("$.suburb").value(DEFAULT_SUBURB))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAddress() throws Exception {
        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address
        Address updatedAddress = addressRepository.findById(address.getId()).get();
        // Disconnect from session so that the updates on updatedAddress are not directly saved in db
        em.detach(updatedAddress);
        updatedAddress
            .address(UPDATED_ADDRESS)
            .name(UPDATED_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .line(UPDATED_LINE)
            .unit(UPDATED_UNIT)
            .number(UPDATED_NUMBER)
            .street(UPDATED_STREET)
            .postcode(UPDATED_POSTCODE)
            .suburb(UPDATED_SUBURB)
            .status(UPDATED_STATUS);
        AddressDTO addressDTO = addressMapper.toDto(updatedAddress);

        restAddressMockMvc
            .perform(put("/api/addresses").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testAddress.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAddress.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testAddress.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testAddress.getLine()).isEqualTo(UPDATED_LINE);
        assertThat(testAddress.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testAddress.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testAddress.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testAddress.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testAddress.getSuburb()).isEqualTo(UPDATED_SUBURB);
        assertThat(testAddress.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Address in Elasticsearch
        verify(mockAddressSearchRepository, times(1)).save(testAddress);
    }

    @Test
    @Transactional
    public void updateNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(put("/api/addresses").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Address in Elasticsearch
        verify(mockAddressSearchRepository, times(0)).save(address);
    }

    @Test
    @Transactional
    public void deleteAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeDelete = addressRepository.findAll().size();

        // Delete the address
        restAddressMockMvc
            .perform(delete("/api/addresses/{id}", address.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Address in Elasticsearch
        verify(mockAddressSearchRepository, times(1)).deleteById(address.getId());
    }

    @Test
    @Transactional
    public void searchAddress() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        addressRepository.saveAndFlush(address);
        when(mockAddressSearchRepository.search(queryStringQuery("id:" + address.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(address), PageRequest.of(0, 1), 1));

        // Search the address
        restAddressMockMvc
            .perform(get("/api/_search/addresses?query=id:" + address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.intValue())))
            .andExpect(jsonPath("$.[*].line").value(hasItem(DEFAULT_LINE)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)))
            .andExpect(jsonPath("$.[*].suburb").value(hasItem(DEFAULT_SUBURB)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
