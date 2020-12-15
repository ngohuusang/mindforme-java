package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RuleMapperTest {
    private RuleMapper ruleMapper;

    @BeforeEach
    public void setUp() {
        ruleMapper = new RuleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ruleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ruleMapper.fromId(null)).isNull();
    }
}
