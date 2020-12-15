package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChildHelpRequestMapperTest {
    private ChildHelpRequestMapper childHelpRequestMapper;

    @BeforeEach
    public void setUp() {
        childHelpRequestMapper = new ChildHelpRequestMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(childHelpRequestMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(childHelpRequestMapper.fromId(null)).isNull();
    }
}
