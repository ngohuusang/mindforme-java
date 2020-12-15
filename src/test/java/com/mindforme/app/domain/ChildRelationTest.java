package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class ChildRelationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChildRelation.class);
        ChildRelation childRelation1 = new ChildRelation();
        childRelation1.setId(1L);
        ChildRelation childRelation2 = new ChildRelation();
        childRelation2.setId(childRelation1.getId());
        assertThat(childRelation1).isEqualTo(childRelation2);
        childRelation2.setId(2L);
        assertThat(childRelation1).isNotEqualTo(childRelation2);
        childRelation1.setId(null);
        assertThat(childRelation1).isNotEqualTo(childRelation2);
    }
}
