package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RuleDataMapperTest {
    private RuleDataMapper ruleDataMapper;

    @BeforeEach
    public void setUp() {
        ruleDataMapper = new RuleDataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ruleDataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ruleDataMapper.fromId(null)).isNull();
    }
}
