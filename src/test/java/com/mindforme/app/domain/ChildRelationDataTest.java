package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class ChildRelationDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChildRelationData.class);
        ChildRelationData childRelationData1 = new ChildRelationData();
        childRelationData1.setId(1L);
        ChildRelationData childRelationData2 = new ChildRelationData();
        childRelationData2.setId(childRelationData1.getId());
        assertThat(childRelationData1).isEqualTo(childRelationData2);
        childRelationData2.setId(2L);
        assertThat(childRelationData1).isNotEqualTo(childRelationData2);
        childRelationData1.setId(null);
        assertThat(childRelationData1).isNotEqualTo(childRelationData2);
    }
}
