package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A RuleData.
 */
@Entity
@Table(name = "rule_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ruledata")
public class RuleData implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @Column(name = "content", length = 255)
    private String content;

    @ManyToOne
    @JsonIgnoreProperties(value = "ruleData", allowSetters = true)
    private Language lang;

    @ManyToOne
    @JsonIgnoreProperties(value = "ruleData", allowSetters = true)
    private Rule rule;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public RuleData content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Language getLang() {
        return lang;
    }

    public RuleData lang(Language language) {
        this.lang = language;
        return this;
    }

    public void setLang(Language language) {
        this.lang = language;
    }

    public Rule getRule() {
        return rule;
    }

    public RuleData rule(Rule rule) {
        this.rule = rule;
        return this;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RuleData)) {
            return false;
        }
        return id != null && id.equals(((RuleData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RuleData{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            "}";
    }
}
