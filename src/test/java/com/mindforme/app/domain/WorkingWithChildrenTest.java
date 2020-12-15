package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class WorkingWithChildrenTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkingWithChildren.class);
        WorkingWithChildren workingWithChildren1 = new WorkingWithChildren();
        workingWithChildren1.setId(1L);
        WorkingWithChildren workingWithChildren2 = new WorkingWithChildren();
        workingWithChildren2.setId(workingWithChildren1.getId());
        assertThat(workingWithChildren1).isEqualTo(workingWithChildren2);
        workingWithChildren2.setId(2L);
        assertThat(workingWithChildren1).isNotEqualTo(workingWithChildren2);
        workingWithChildren1.setId(null);
        assertThat(workingWithChildren1).isNotEqualTo(workingWithChildren2);
    }
}
