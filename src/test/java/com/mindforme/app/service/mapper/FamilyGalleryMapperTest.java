package com.mindforme.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FamilyGalleryMapperTest {
    private FamilyGalleryMapper familyGalleryMapper;

    @BeforeEach
    public void setUp() {
        familyGalleryMapper = new FamilyGalleryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(familyGalleryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(familyGalleryMapper.fromId(null)).isNull();
    }
}
