package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mindforme.app.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Pet.
 */
@Entity
@Table(name = "pet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "pet")
public class Pet implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "birthday")
    private Instant birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties(value = "pets", allowSetters = true)
    private PetBreed breed;

    @ManyToOne
    @JsonIgnoreProperties(value = "pets", allowSetters = true)
    private PetType petType;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "pet_feeding",
        joinColumns = @JoinColumn(name = "pet_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "feeding_id", referencedColumnName = "id")
    )
    private Set<Feeding> feedings = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "pet_rule",
        joinColumns = @JoinColumn(name = "pet_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "rule_id", referencedColumnName = "id")
    )
    private Set<Rule> rules = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "pet_allergy",
        joinColumns = @JoinColumn(name = "pet_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "allergy_id", referencedColumnName = "id")
    )
    private Set<Allergy> allergies = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "pet_walking",
        joinColumns = @JoinColumn(name = "pet_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "walking_id", referencedColumnName = "id")
    )
    private Set<WalkingOther> walkings = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "pets", allowSetters = true)
    private Family family;

    @ManyToMany(mappedBy = "pets")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<PetHelpRequest> helpRequests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Pet name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Pet imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public Pet birthday(Instant birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public Status getStatus() {
        return status;
    }

    public Pet status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PetBreed getBreed() {
        return breed;
    }

    public Pet breed(PetBreed petBreed) {
        this.breed = petBreed;
        return this;
    }

    public void setBreed(PetBreed petBreed) {
        this.breed = petBreed;
    }

    public PetType getPetType() {
        return petType;
    }

    public Pet petType(PetType petType) {
        this.petType = petType;
        return this;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public Set<Feeding> getFeedings() {
        return feedings;
    }

    public Pet feedings(Set<Feeding> feedings) {
        this.feedings = feedings;
        return this;
    }

    public Pet addFeeding(Feeding feeding) {
        this.feedings.add(feeding);
        feeding.getPets().add(this);
        return this;
    }

    public Pet removeFeeding(Feeding feeding) {
        this.feedings.remove(feeding);
        feeding.getPets().remove(this);
        return this;
    }

    public void setFeedings(Set<Feeding> feedings) {
        this.feedings = feedings;
    }

    public Set<Rule> getRules() {
        return rules;
    }

    public Pet rules(Set<Rule> rules) {
        this.rules = rules;
        return this;
    }

    public Pet addRule(Rule rule) {
        this.rules.add(rule);
        rule.getPets().add(this);
        return this;
    }

    public Pet removeRule(Rule rule) {
        this.rules.remove(rule);
        rule.getPets().remove(this);
        return this;
    }

    public void setRules(Set<Rule> rules) {
        this.rules = rules;
    }

    public Set<Allergy> getAllergies() {
        return allergies;
    }

    public Pet allergies(Set<Allergy> allergies) {
        this.allergies = allergies;
        return this;
    }

    public Pet addAllergy(Allergy allergy) {
        this.allergies.add(allergy);
        allergy.getPets().add(this);
        return this;
    }

    public Pet removeAllergy(Allergy allergy) {
        this.allergies.remove(allergy);
        allergy.getPets().remove(this);
        return this;
    }

    public void setAllergies(Set<Allergy> allergies) {
        this.allergies = allergies;
    }

    public Set<WalkingOther> getWalkings() {
        return walkings;
    }

    public Pet walkings(Set<WalkingOther> walkingOthers) {
        this.walkings = walkingOthers;
        return this;
    }

    public Pet addWalking(WalkingOther walkingOther) {
        this.walkings.add(walkingOther);
        walkingOther.getPets().add(this);
        return this;
    }

    public Pet removeWalking(WalkingOther walkingOther) {
        this.walkings.remove(walkingOther);
        walkingOther.getPets().remove(this);
        return this;
    }

    public void setWalkings(Set<WalkingOther> walkingOthers) {
        this.walkings = walkingOthers;
    }

    public Family getFamily() {
        return family;
    }

    public Pet family(Family family) {
        this.family = family;
        return this;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public Set<PetHelpRequest> getHelpRequests() {
        return helpRequests;
    }

    public Pet helpRequests(Set<PetHelpRequest> petHelpRequests) {
        this.helpRequests = petHelpRequests;
        return this;
    }

    public Pet addHelpRequest(PetHelpRequest petHelpRequest) {
        this.helpRequests.add(petHelpRequest);
        petHelpRequest.getPets().add(this);
        return this;
    }

    public Pet removeHelpRequest(PetHelpRequest petHelpRequest) {
        this.helpRequests.remove(petHelpRequest);
        petHelpRequest.getPets().remove(this);
        return this;
    }

    public void setHelpRequests(Set<PetHelpRequest> petHelpRequests) {
        this.helpRequests = petHelpRequests;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pet)) {
            return false;
        }
        return id != null && id.equals(((Pet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pet{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
