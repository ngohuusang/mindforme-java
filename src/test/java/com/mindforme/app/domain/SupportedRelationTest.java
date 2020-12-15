package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class SupportedRelationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupportedRelation.class);
        SupportedRelation supportedRelation1 = new SupportedRelation();
        supportedRelation1.setId(1L);
        SupportedRelation supportedRelation2 = new SupportedRelation();
        supportedRelation2.setId(supportedRelation1.getId());
        assertThat(supportedRelation1).isEqualTo(supportedRelation2);
        supportedRelation2.setId(2L);
        assertThat(supportedRelation1).isNotEqualTo(supportedRelation2);
        supportedRelation1.setId(null);
        assertThat(supportedRelation1).isNotEqualTo(supportedRelation2);
    }
}
