package com.mindforme.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mindforme.app.domain.enumeration.DistanceUnit;
import com.mindforme.app.domain.enumeration.Frequency;
import com.mindforme.app.domain.enumeration.Privacy;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Family.
 */
@Entity
@Table(name = "family")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "family")
public class Family implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 45)
    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "karma_points")
    private Integer karmaPoints;

    @Column(name = "overview")
    private String overview;

    @Column(name = "rating")
    private String rating;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "plan_expire")
    private Instant planExpire;

    @Lob
    @Column(name = "rule")
    private String rule;

    @Column(name = "free_months")
    private String freeMonths;

    @Column(name = "other_verify")
    private Integer otherVerify;

    @Column(name = "kc_25_paid")
    private Boolean kc25Paid;

    @Column(name = "kc_75_paid")
    private Boolean kc75Paid;

    @Enumerated(EnumType.STRING)
    @Column(name = "privacy_family")
    private Privacy privacyFamily;

    @Column(name = "share_to_facebook")
    private Boolean shareToFacebook;

    @Enumerated(EnumType.STRING)
    @Column(name = "privacy_personal")
    private Privacy privacyPersonal;

    @Column(name = "add_to_calendar")
    private Boolean addToCalendar;

    @Column(name = "remind_events")
    private Boolean remindEvents;

    @Column(name = "notify_facebook")
    private Boolean notifyFacebook;

    @Column(name = "distance_request")
    private Float distanceRequest;

    @Enumerated(EnumType.STRING)
    @Column(name = "distance_unit")
    private DistanceUnit distanceUnit;

    @Enumerated(EnumType.STRING)
    @Column(name = "mail_request_friend")
    private Frequency mailRequestFriend;

    @Enumerated(EnumType.STRING)
    @Column(name = "mail_request_friend_of_friend")
    private Frequency mailRequestFriendOfFriend;

    @Enumerated(EnumType.STRING)
    @Column(name = "mail_request")
    private Frequency mailRequest;

    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(mappedBy = "family")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<User> members = new HashSet<>();

    @OneToMany(mappedBy = "family")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Doctor> doctors = new HashSet<>();

    @OneToMany(mappedBy = "family")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EmergencyContact> emergencyContacts = new HashSet<>();

    @OneToMany(mappedBy = "family")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<FamilyGallery> galleries = new HashSet<>();

    @OneToMany(mappedBy = "family")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Pet> pets = new HashSet<>();

    @OneToMany(mappedBy = "family")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Child> children = new HashSet<>();

    @OneToMany(mappedBy = "family")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Supported> supporteds = new HashSet<>();

    @OneToMany(mappedBy = "family")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MindingNotification> mindingNotifications = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "families", allowSetters = true)
    private Plan plan;

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

    public Family name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKarmaPoints() {
        return karmaPoints;
    }

    public Family karmaPoints(Integer karmaPoints) {
        this.karmaPoints = karmaPoints;
        return this;
    }

    public void setKarmaPoints(Integer karmaPoints) {
        this.karmaPoints = karmaPoints;
    }

    public String getOverview() {
        return overview;
    }

    public Family overview(String overview) {
        this.overview = overview;
        return this;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRating() {
        return rating;
    }

    public Family rating(String rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Family imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Instant getPlanExpire() {
        return planExpire;
    }

    public Family planExpire(Instant planExpire) {
        this.planExpire = planExpire;
        return this;
    }

    public void setPlanExpire(Instant planExpire) {
        this.planExpire = planExpire;
    }

    public String getRule() {
        return rule;
    }

    public Family rule(String rule) {
        this.rule = rule;
        return this;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getFreeMonths() {
        return freeMonths;
    }

    public Family freeMonths(String freeMonths) {
        this.freeMonths = freeMonths;
        return this;
    }

    public void setFreeMonths(String freeMonths) {
        this.freeMonths = freeMonths;
    }

    public Integer getOtherVerify() {
        return otherVerify;
    }

    public Family otherVerify(Integer otherVerify) {
        this.otherVerify = otherVerify;
        return this;
    }

    public void setOtherVerify(Integer otherVerify) {
        this.otherVerify = otherVerify;
    }

    public Boolean isKc25Paid() {
        return kc25Paid;
    }

    public Family kc25Paid(Boolean kc25Paid) {
        this.kc25Paid = kc25Paid;
        return this;
    }

    public void setKc25Paid(Boolean kc25Paid) {
        this.kc25Paid = kc25Paid;
    }

    public Boolean isKc75Paid() {
        return kc75Paid;
    }

    public Family kc75Paid(Boolean kc75Paid) {
        this.kc75Paid = kc75Paid;
        return this;
    }

    public void setKc75Paid(Boolean kc75Paid) {
        this.kc75Paid = kc75Paid;
    }

    public Privacy getPrivacyFamily() {
        return privacyFamily;
    }

    public Family privacyFamily(Privacy privacyFamily) {
        this.privacyFamily = privacyFamily;
        return this;
    }

    public void setPrivacyFamily(Privacy privacyFamily) {
        this.privacyFamily = privacyFamily;
    }

    public Boolean isShareToFacebook() {
        return shareToFacebook;
    }

    public Family shareToFacebook(Boolean shareToFacebook) {
        this.shareToFacebook = shareToFacebook;
        return this;
    }

    public void setShareToFacebook(Boolean shareToFacebook) {
        this.shareToFacebook = shareToFacebook;
    }

    public Privacy getPrivacyPersonal() {
        return privacyPersonal;
    }

    public Family privacyPersonal(Privacy privacyPersonal) {
        this.privacyPersonal = privacyPersonal;
        return this;
    }

    public void setPrivacyPersonal(Privacy privacyPersonal) {
        this.privacyPersonal = privacyPersonal;
    }

    public Boolean isAddToCalendar() {
        return addToCalendar;
    }

    public Family addToCalendar(Boolean addToCalendar) {
        this.addToCalendar = addToCalendar;
        return this;
    }

    public void setAddToCalendar(Boolean addToCalendar) {
        this.addToCalendar = addToCalendar;
    }

    public Boolean isRemindEvents() {
        return remindEvents;
    }

    public Family remindEvents(Boolean remindEvents) {
        this.remindEvents = remindEvents;
        return this;
    }

    public void setRemindEvents(Boolean remindEvents) {
        this.remindEvents = remindEvents;
    }

    public Boolean isNotifyFacebook() {
        return notifyFacebook;
    }

    public Family notifyFacebook(Boolean notifyFacebook) {
        this.notifyFacebook = notifyFacebook;
        return this;
    }

    public void setNotifyFacebook(Boolean notifyFacebook) {
        this.notifyFacebook = notifyFacebook;
    }

    public Float getDistanceRequest() {
        return distanceRequest;
    }

    public Family distanceRequest(Float distanceRequest) {
        this.distanceRequest = distanceRequest;
        return this;
    }

    public void setDistanceRequest(Float distanceRequest) {
        this.distanceRequest = distanceRequest;
    }

    public DistanceUnit getDistanceUnit() {
        return distanceUnit;
    }

    public Family distanceUnit(DistanceUnit distanceUnit) {
        this.distanceUnit = distanceUnit;
        return this;
    }

    public void setDistanceUnit(DistanceUnit distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public Frequency getMailRequestFriend() {
        return mailRequestFriend;
    }

    public Family mailRequestFriend(Frequency mailRequestFriend) {
        this.mailRequestFriend = mailRequestFriend;
        return this;
    }

    public void setMailRequestFriend(Frequency mailRequestFriend) {
        this.mailRequestFriend = mailRequestFriend;
    }

    public Frequency getMailRequestFriendOfFriend() {
        return mailRequestFriendOfFriend;
    }

    public Family mailRequestFriendOfFriend(Frequency mailRequestFriendOfFriend) {
        this.mailRequestFriendOfFriend = mailRequestFriendOfFriend;
        return this;
    }

    public void setMailRequestFriendOfFriend(Frequency mailRequestFriendOfFriend) {
        this.mailRequestFriendOfFriend = mailRequestFriendOfFriend;
    }

    public Frequency getMailRequest() {
        return mailRequest;
    }

    public Family mailRequest(Frequency mailRequest) {
        this.mailRequest = mailRequest;
        return this;
    }

    public void setMailRequest(Frequency mailRequest) {
        this.mailRequest = mailRequest;
    }

    public Address getAddress() {
        return address;
    }

    public Family address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<User> getMembers() {
        return members;
    }

    public Family members(Set<User> users) {
        this.members = users;
        return this;
    }

    public Family addMember(User user) {
        this.members.add(user);
        user.setFamily(this);
        return this;
    }

    public Family removeMember(User user) {
        this.members.remove(user);
        user.setFamily(null);
        return this;
    }

    public void setMembers(Set<User> users) {
        this.members = users;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public Family doctors(Set<Doctor> doctors) {
        this.doctors = doctors;
        return this;
    }

    public Family addDoctor(Doctor doctor) {
        this.doctors.add(doctor);
        doctor.setFamily(this);
        return this;
    }

    public Family removeDoctor(Doctor doctor) {
        this.doctors.remove(doctor);
        doctor.setFamily(null);
        return this;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }

    public Set<EmergencyContact> getEmergencyContacts() {
        return emergencyContacts;
    }

    public Family emergencyContacts(Set<EmergencyContact> emergencyContacts) {
        this.emergencyContacts = emergencyContacts;
        return this;
    }

    public Family addEmergencyContact(EmergencyContact emergencyContact) {
        this.emergencyContacts.add(emergencyContact);
        emergencyContact.setFamily(this);
        return this;
    }

    public Family removeEmergencyContact(EmergencyContact emergencyContact) {
        this.emergencyContacts.remove(emergencyContact);
        emergencyContact.setFamily(null);
        return this;
    }

    public void setEmergencyContacts(Set<EmergencyContact> emergencyContacts) {
        this.emergencyContacts = emergencyContacts;
    }

    public Set<FamilyGallery> getGalleries() {
        return galleries;
    }

    public Family galleries(Set<FamilyGallery> familyGalleries) {
        this.galleries = familyGalleries;
        return this;
    }

    public Family addGallery(FamilyGallery familyGallery) {
        this.galleries.add(familyGallery);
        familyGallery.setFamily(this);
        return this;
    }

    public Family removeGallery(FamilyGallery familyGallery) {
        this.galleries.remove(familyGallery);
        familyGallery.setFamily(null);
        return this;
    }

    public void setGalleries(Set<FamilyGallery> familyGalleries) {
        this.galleries = familyGalleries;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public Family pets(Set<Pet> pets) {
        this.pets = pets;
        return this;
    }

    public Family addPet(Pet pet) {
        this.pets.add(pet);
        pet.setFamily(this);
        return this;
    }

    public Family removePet(Pet pet) {
        this.pets.remove(pet);
        pet.setFamily(null);
        return this;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

    public Set<Child> getChildren() {
        return children;
    }

    public Family children(Set<Child> children) {
        this.children = children;
        return this;
    }

    public Family addChild(Child child) {
        this.children.add(child);
        child.setFamily(this);
        return this;
    }

    public Family removeChild(Child child) {
        this.children.remove(child);
        child.setFamily(null);
        return this;
    }

    public void setChildren(Set<Child> children) {
        this.children = children;
    }

    public Set<Supported> getSupporteds() {
        return supporteds;
    }

    public Family supporteds(Set<Supported> supporteds) {
        this.supporteds = supporteds;
        return this;
    }

    public Family addSupported(Supported supported) {
        this.supporteds.add(supported);
        supported.setFamily(this);
        return this;
    }

    public Family removeSupported(Supported supported) {
        this.supporteds.remove(supported);
        supported.setFamily(null);
        return this;
    }

    public void setSupporteds(Set<Supported> supporteds) {
        this.supporteds = supporteds;
    }

    public Set<MindingNotification> getMindingNotifications() {
        return mindingNotifications;
    }

    public Family mindingNotifications(Set<MindingNotification> mindingNotifications) {
        this.mindingNotifications = mindingNotifications;
        return this;
    }

    public Family addMindingNotification(MindingNotification mindingNotification) {
        this.mindingNotifications.add(mindingNotification);
        mindingNotification.setFamily(this);
        return this;
    }

    public Family removeMindingNotification(MindingNotification mindingNotification) {
        this.mindingNotifications.remove(mindingNotification);
        mindingNotification.setFamily(null);
        return this;
    }

    public void setMindingNotifications(Set<MindingNotification> mindingNotifications) {
        this.mindingNotifications = mindingNotifications;
    }

    public Plan getPlan() {
        return plan;
    }

    public Family plan(Plan plan) {
        this.plan = plan;
        return this;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Family)) {
            return false;
        }
        return id != null && id.equals(((Family) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Family{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", karmaPoints=" + getKarmaPoints() +
            ", overview='" + getOverview() + "'" +
            ", rating='" + getRating() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", planExpire='" + getPlanExpire() + "'" +
            ", rule='" + getRule() + "'" +
            ", freeMonths='" + getFreeMonths() + "'" +
            ", otherVerify=" + getOtherVerify() +
            ", kc25Paid='" + isKc25Paid() + "'" +
            ", kc75Paid='" + isKc75Paid() + "'" +
            ", privacyFamily='" + getPrivacyFamily() + "'" +
            ", shareToFacebook='" + isShareToFacebook() + "'" +
            ", privacyPersonal='" + getPrivacyPersonal() + "'" +
            ", addToCalendar='" + isAddToCalendar() + "'" +
            ", remindEvents='" + isRemindEvents() + "'" +
            ", notifyFacebook='" + isNotifyFacebook() + "'" +
            ", distanceRequest=" + getDistanceRequest() +
            ", distanceUnit='" + getDistanceUnit() + "'" +
            ", mailRequestFriend='" + getMailRequestFriend() + "'" +
            ", mailRequestFriendOfFriend='" + getMailRequestFriendOfFriend() + "'" +
            ", mailRequest='" + getMailRequest() + "'" +
            "}";
    }
}
