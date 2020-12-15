package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class FamilyGalleryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FamilyGalleryDTO.class);
        FamilyGalleryDTO familyGalleryDTO1 = new FamilyGalleryDTO();
        familyGalleryDTO1.setId(1L);
        FamilyGalleryDTO familyGalleryDTO2 = new FamilyGalleryDTO();
        assertThat(familyGalleryDTO1).isNotEqualTo(familyGalleryDTO2);
        familyGalleryDTO2.setId(familyGalleryDTO1.getId());
        assertThat(familyGalleryDTO1).isEqualTo(familyGalleryDTO2);
        familyGalleryDTO2.setId(2L);
        assertThat(familyGalleryDTO1).isNotEqualTo(familyGalleryDTO2);
        familyGalleryDTO1.setId(null);
        assertThat(familyGalleryDTO1).isNotEqualTo(familyGalleryDTO2);
    }
}
