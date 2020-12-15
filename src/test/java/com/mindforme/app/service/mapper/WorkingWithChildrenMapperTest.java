package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorkingWithChildrenMapperTest {
    private WorkingWithChildrenMapper workingWithChildrenMapper;

    @BeforeEach
    public void setUp() {
        workingWithChildrenMapper = new WorkingWithChildrenMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(workingWithChildrenMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(workingWithChildrenMapper.fromId(null)).isNull();
    }
}
