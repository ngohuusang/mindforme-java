package com.mindforme.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class RuleDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RuleData.class);
        RuleData ruleData1 = new RuleData();
        ruleData1.setId(1L);
        RuleData ruleData2 = new RuleData();
        ruleData2.setId(ruleData1.getId());
        assertThat(ruleData1).isEqualTo(ruleData2);
        ruleData2.setId(2L);
        assertThat(ruleData1).isNotEqualTo(ruleData2);
        ruleData1.setId(null);
        assertThat(ruleData1).isNotEqualTo(ruleData2);
    }
}
