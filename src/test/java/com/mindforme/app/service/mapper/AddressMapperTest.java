package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddressMapperTest {
    private AddressMapper addressMapper;

    @BeforeEach
    public void setUp() {
        addressMapper = new AddressMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(addressMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(addressMapper.fromId(null)).isNull();
    }
}
