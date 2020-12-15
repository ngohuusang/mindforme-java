package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class FamilyGalleryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FamilyGallery.class);
        FamilyGallery familyGallery1 = new FamilyGallery();
        familyGallery1.setId(1L);
        FamilyGallery familyGallery2 = new FamilyGallery();
        familyGallery2.setId(familyGallery1.getId());
        assertThat(familyGallery1).isEqualTo(familyGallery2);
        familyGallery2.setId(2L);
        assertThat(familyGallery1).isNotEqualTo(familyGallery2);
        familyGallery1.setId(null);
        assertThat(familyGallery1).isNotEqualTo(familyGallery2);
    }
}
