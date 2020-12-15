package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class FeedingDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedingDataDTO.class);
        FeedingDataDTO feedingDataDTO1 = new FeedingDataDTO();
        feedingDataDTO1.setId(1L);
        FeedingDataDTO feedingDataDTO2 = new FeedingDataDTO();
        assertThat(feedingDataDTO1).isNotEqualTo(feedingDataDTO2);
        feedingDataDTO2.setId(feedingDataDTO1.getId());
        assertThat(feedingDataDTO1).isEqualTo(feedingDataDTO2);
        feedingDataDTO2.setId(2L);
        assertThat(feedingDataDTO1).isNotEqualTo(feedingDataDTO2);
        feedingDataDTO1.setId(null);
        assertThat(feedingDataDTO1).isNotEqualTo(feedingDataDTO2);
    }
}
