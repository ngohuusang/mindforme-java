package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class FeedingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedingDTO.class);
        FeedingDTO feedingDTO1 = new FeedingDTO();
        feedingDTO1.setId(1L);
        FeedingDTO feedingDTO2 = new FeedingDTO();
        assertThat(feedingDTO1).isNotEqualTo(feedingDTO2);
        feedingDTO2.setId(feedingDTO1.getId());
        assertThat(feedingDTO1).isEqualTo(feedingDTO2);
        feedingDTO2.setId(2L);
        assertThat(feedingDTO1).isNotEqualTo(feedingDTO2);
        feedingDTO1.setId(null);
        assertThat(feedingDTO1).isNotEqualTo(feedingDTO2);
    }
}
