package com.mindforme.app.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mindforme.app.domain.RuleData} entity.
 */
public class RuleDataDTO implements Serializable {
    private Long id;

    @Size(max = 255)
    private String content;

    private Long langId;

    private String langName;

    private Long ruleId;

    private String ruleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(Long languageId) {
        this.langId = languageId;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String languageName) {
        this.langName = languageName;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RuleDataDTO)) {
            return false;
        }

        return id != null && id.equals(((RuleDataDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RuleDataDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", langId=" + getLangId() +
            ", langName='" + getLangName() + "'" +
            ", ruleId=" + getRuleId() +
            ", ruleName='" + getRuleName() + "'" +
            "}";
    }
}
