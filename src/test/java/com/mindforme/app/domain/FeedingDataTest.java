package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class FeedingDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedingData.class);
        FeedingData feedingData1 = new FeedingData();
        feedingData1.setId(1L);
        FeedingData feedingData2 = new FeedingData();
        feedingData2.setId(feedingData1.getId());
        assertThat(feedingData1).isEqualTo(feedingData2);
        feedingData2.setId(2L);
        assertThat(feedingData1).isNotEqualTo(feedingData2);
        feedingData1.setId(null);
        assertThat(feedingData1).isNotEqualTo(feedingData2);
    }
}
