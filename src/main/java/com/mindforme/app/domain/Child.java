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
 * A Child.
 */
@Entity
@Table(name = "child")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "child")
public class Child implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "birthday")
    private Instant birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties(value = "children", allowSetters = true)
    private ChildRelation relation;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "child_interest",
        joinColumns = @JoinColumn(name = "child_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "interest_id", referencedColumnName = "id")
    )
    private Set<Interest> interests = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "child_allergy",
        joinColumns = @JoinColumn(name = "child_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "allergy_id", referencedColumnName = "id")
    )
    private Set<Allergy> allergies = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "child_favourite_food",
        joinColumns = @JoinColumn(name = "child_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "favourite_food_id", referencedColumnName = "id")
    )
    private Set<FavouriteFood> favouriteFoods = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "child_medical_condition",
        joinColumns = @JoinColumn(name = "child_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "medical_condition_id", referencedColumnName = "id")
    )
    private Set<MedicalCondition> medicalConditions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "children", allowSetters = true)
    private Family family;

    @ManyToOne
    @JsonIgnoreProperties(value = "childRequests", allowSetters = true)
    private HelpRequest request;

    @ManyToMany(mappedBy = "children")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<ChildHelpRequest> helpRequests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Child firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Child lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Child imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public Child birthday(Instant birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public Status getStatus() {
        return status;
    }

    public Child status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ChildRelation getRelation() {
        return relation;
    }

    public Child relation(ChildRelation childRelation) {
        this.relation = childRelation;
        return this;
    }

    public void setRelation(ChildRelation childRelation) {
        this.relation = childRelation;
    }

    public Set<Interest> getInterests() {
        return interests;
    }

    public Child interests(Set<Interest> interests) {
        this.interests = interests;
        return this;
    }

    public Child addInterest(Interest interest) {
        this.interests.add(interest);
        interest.getChildren().add(this);
        return this;
    }

    public Child removeInterest(Interest interest) {
        this.interests.remove(interest);
        interest.getChildren().remove(this);
        return this;
    }

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }

    public Set<Allergy> getAllergies() {
        return allergies;
    }

    public Child allergies(Set<Allergy> allergies) {
        this.allergies = allergies;
        return this;
    }

    public Child addAllergy(Allergy allergy) {
        this.allergies.add(allergy);
        allergy.getChildren().add(this);
        return this;
    }

    public Child removeAllergy(Allergy allergy) {
        this.allergies.remove(allergy);
        allergy.getChildren().remove(this);
        return this;
    }

    public void setAllergies(Set<Allergy> allergies) {
        this.allergies = allergies;
    }

    public Set<FavouriteFood> getFavouriteFoods() {
        return favouriteFoods;
    }

    public Child favouriteFoods(Set<FavouriteFood> favouriteFoods) {
        this.favouriteFoods = favouriteFoods;
        return this;
    }

    public Child addFavouriteFood(FavouriteFood favouriteFood) {
        this.favouriteFoods.add(favouriteFood);
        favouriteFood.getChildren().add(this);
        return this;
    }

    public Child removeFavouriteFood(FavouriteFood favouriteFood) {
        this.favouriteFoods.remove(favouriteFood);
        favouriteFood.getChildren().remove(this);
        return this;
    }

    public void setFavouriteFoods(Set<FavouriteFood> favouriteFoods) {
        this.favouriteFoods = favouriteFoods;
    }

    public Set<MedicalCondition> getMedicalConditions() {
        return medicalConditions;
    }

    public Child medicalConditions(Set<MedicalCondition> medicalConditions) {
        this.medicalConditions = medicalConditions;
        return this;
    }

    public Child addMedicalCondition(MedicalCondition medicalCondition) {
        this.medicalConditions.add(medicalCondition);
        medicalCondition.getChildren().add(this);
        return this;
    }

    public Child removeMedicalCondition(MedicalCondition medicalCondition) {
        this.medicalConditions.remove(medicalCondition);
        medicalCondition.getChildren().remove(this);
        return this;
    }

    public void setMedicalConditions(Set<MedicalCondition> medicalConditions) {
        this.medicalConditions = medicalConditions;
    }

    public Family getFamily() {
        return family;
    }

    public Child family(Family family) {
        this.family = family;
        return this;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public HelpRequest getRequest() {
        return request;
    }

    public Child request(HelpRequest helpRequest) {
        this.request = helpRequest;
        return this;
    }

    public void setRequest(HelpRequest helpRequest) {
        this.request = helpRequest;
    }

    public Set<ChildHelpRequest> getHelpRequests() {
        return helpRequests;
    }

    public Child helpRequests(Set<ChildHelpRequest> childHelpRequests) {
        this.helpRequests = childHelpRequests;
        return this;
    }

    public Child addHelpRequest(ChildHelpRequest childHelpRequest) {
        this.helpRequests.add(childHelpRequest);
        childHelpRequest.getChildren().add(this);
        return this;
    }

    public Child removeHelpRequest(ChildHelpRequest childHelpRequest) {
        this.helpRequests.remove(childHelpRequest);
        childHelpRequest.getChildren().remove(this);
        return this;
    }

    public void setHelpRequests(Set<ChildHelpRequest> childHelpRequests) {
        this.helpRequests = childHelpRequests;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Child)) {
            return false;
        }
        return id != null && id.equals(((Child) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Child{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
