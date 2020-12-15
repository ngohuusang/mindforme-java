package com.mindforme.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindforme.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class RuleDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RuleDataDTO.class);
        RuleDataDTO ruleDataDTO1 = new RuleDataDTO();
        ruleDataDTO1.setId(1L);
        RuleDataDTO ruleDataDTO2 = new RuleDataDTO();
        assertThat(ruleDataDTO1).isNotEqualTo(ruleDataDTO2);
        ruleDataDTO2.setId(ruleDataDTO1.getId());
        assertThat(ruleDataDTO1).isEqualTo(ruleDataDTO2);
        ruleDataDTO2.setId(2L);
        assertThat(ruleDataDTO1).isNotEqualTo(ruleDataDTO2);
        ruleDataDTO1.setId(null);
        assertThat(ruleDataDTO1).isNotEqualTo(ruleDataDTO2);
    }
}
