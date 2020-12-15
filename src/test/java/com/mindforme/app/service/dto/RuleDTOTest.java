package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class RuleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RuleDTO.class);
        RuleDTO ruleDTO1 = new RuleDTO();
        ruleDTO1.setId(1L);
        RuleDTO ruleDTO2 = new RuleDTO();
        assertThat(ruleDTO1).isNotEqualTo(ruleDTO2);
        ruleDTO2.setId(ruleDTO1.getId());
        assertThat(ruleDTO1).isEqualTo(ruleDTO2);
        ruleDTO2.setId(2L);
        assertThat(ruleDTO1).isNotEqualTo(ruleDTO2);
        ruleDTO1.setId(null);
        assertThat(ruleDTO1).isNotEqualTo(ruleDTO2);
    }
}
