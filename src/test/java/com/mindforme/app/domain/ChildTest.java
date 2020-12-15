package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class ChildTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Child.class);
        Child child1 = new Child();
        child1.setId(1L);
        Child child2 = new Child();
        child2.setId(child1.getId());
        assertThat(child1).isEqualTo(child2);
        child2.setId(2L);
        assertThat(child1).isNotEqualTo(child2);
        child1.setId(null);
        assertThat(child1).isNotEqualTo(child2);
    }
}
