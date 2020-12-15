package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class SupportedRelationDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupportedRelationData.class);
        SupportedRelationData supportedRelationData1 = new SupportedRelationData();
        supportedRelationData1.setId(1L);
        SupportedRelationData supportedRelationData2 = new SupportedRelationData();
        supportedRelationData2.setId(supportedRelationData1.getId());
        assertThat(supportedRelationData1).isEqualTo(supportedRelationData2);
        supportedRelationData2.setId(2L);
        assertThat(supportedRelationData1).isNotEqualTo(supportedRelationData2);
        supportedRelationData1.setId(null);
        assertThat(supportedRelationData1).isNotEqualTo(supportedRelationData2);
    }
}
