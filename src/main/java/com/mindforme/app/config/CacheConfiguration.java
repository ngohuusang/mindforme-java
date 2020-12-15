package com.mindforme.app.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mindforme.app.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.mindforme.app.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.mindforme.app.domain.User.class.getName());
            createCache(cm, com.mindforme.app.domain.Authority.class.getName());
            createCache(cm, com.mindforme.app.domain.User.class.getName() + ".authorities");
            createCache(cm, com.mindforme.app.domain.Country.class.getName());
            createCache(cm, com.mindforme.app.domain.Country.class.getName() + ".publicHolidays");
            createCache(cm, com.mindforme.app.domain.CountryData.class.getName());
            createCache(cm, com.mindforme.app.domain.City.class.getName());
            createCache(cm, com.mindforme.app.domain.City.class.getName() + ".cityData");
            createCache(cm, com.mindforme.app.domain.CityData.class.getName());
            createCache(cm, com.mindforme.app.domain.State.class.getName());
            createCache(cm, com.mindforme.app.domain.State.class.getName() + ".cities");
            createCache(cm, com.mindforme.app.domain.State.class.getName() + ".stateData");
            createCache(cm, com.mindforme.app.domain.StateData.class.getName());
            createCache(cm, com.mindforme.app.domain.Address.class.getName());
            createCache(cm, com.mindforme.app.domain.EmergencyContact.class.getName());
            createCache(cm, com.mindforme.app.domain.Doctor.class.getName());
            createCache(cm, com.mindforme.app.domain.Plan.class.getName());
            createCache(cm, com.mindforme.app.domain.Plan.class.getName() + ".plansData");
            createCache(cm, com.mindforme.app.domain.PlanData.class.getName());
            createCache(cm, com.mindforme.app.domain.Language.class.getName());
            createCache(cm, com.mindforme.app.domain.Coupon.class.getName());
            createCache(cm, com.mindforme.app.domain.CouponType.class.getName());
            createCache(cm, com.mindforme.app.domain.CouponType.class.getName() + ".coupons");
            createCache(cm, com.mindforme.app.domain.Allergy.class.getName());
            createCache(cm, com.mindforme.app.domain.Allergy.class.getName() + ".allergyData");
            createCache(cm, com.mindforme.app.domain.Allergy.class.getName() + ".pets");
            createCache(cm, com.mindforme.app.domain.Allergy.class.getName() + ".children");
            createCache(cm, com.mindforme.app.domain.AllergyData.class.getName());
            createCache(cm, com.mindforme.app.domain.FavouriteFood.class.getName());
            createCache(cm, com.mindforme.app.domain.FavouriteFood.class.getName() + ".favouriteFoodData");
            createCache(cm, com.mindforme.app.domain.FavouriteFood.class.getName() + ".children");
            createCache(cm, com.mindforme.app.domain.FavouriteFoodData.class.getName());
            createCache(cm, com.mindforme.app.domain.Feeding.class.getName());
            createCache(cm, com.mindforme.app.domain.Feeding.class.getName() + ".feedingData");
            createCache(cm, com.mindforme.app.domain.Feeding.class.getName() + ".pets");
            createCache(cm, com.mindforme.app.domain.FeedingData.class.getName());
            createCache(cm, com.mindforme.app.domain.Interest.class.getName());
            createCache(cm, com.mindforme.app.domain.Interest.class.getName() + ".interestData");
            createCache(cm, com.mindforme.app.domain.Interest.class.getName() + ".children");
            createCache(cm, com.mindforme.app.domain.InterestData.class.getName());
            createCache(cm, com.mindforme.app.domain.MedicalCondition.class.getName());
            createCache(cm, com.mindforme.app.domain.MedicalCondition.class.getName() + ".medicalConditionData");
            createCache(cm, com.mindforme.app.domain.MedicalCondition.class.getName() + ".children");
            createCache(cm, com.mindforme.app.domain.MedicalConditionData.class.getName());
            createCache(cm, com.mindforme.app.domain.PetType.class.getName());
            createCache(cm, com.mindforme.app.domain.PetType.class.getName() + ".petBreeds");
            createCache(cm, com.mindforme.app.domain.PetType.class.getName() + ".petTypeData");
            createCache(cm, com.mindforme.app.domain.PetTypeData.class.getName());
            createCache(cm, com.mindforme.app.domain.PetBreed.class.getName());
            createCache(cm, com.mindforme.app.domain.PetBreed.class.getName() + ".petBreedData");
            createCache(cm, com.mindforme.app.domain.PetBreedData.class.getName());
            createCache(cm, com.mindforme.app.domain.WalkingOther.class.getName());
            createCache(cm, com.mindforme.app.domain.WalkingOther.class.getName() + ".walkingOtherData");
            createCache(cm, com.mindforme.app.domain.WalkingOther.class.getName() + ".pets");
            createCache(cm, com.mindforme.app.domain.WalkingOtherData.class.getName());
            createCache(cm, com.mindforme.app.domain.Rule.class.getName());
            createCache(cm, com.mindforme.app.domain.Rule.class.getName() + ".ruleData");
            createCache(cm, com.mindforme.app.domain.Rule.class.getName() + ".pets");
            createCache(cm, com.mindforme.app.domain.RuleData.class.getName());
            createCache(cm, com.mindforme.app.domain.ChildRelation.class.getName());
            createCache(cm, com.mindforme.app.domain.ChildRelation.class.getName() + ".relationData");
            createCache(cm, com.mindforme.app.domain.ChildRelationData.class.getName());
            createCache(cm, com.mindforme.app.domain.SupportedRelation.class.getName());
            createCache(cm, com.mindforme.app.domain.SupportedRelation.class.getName() + ".relationData");
            createCache(cm, com.mindforme.app.domain.SupportedRelationData.class.getName());
            createCache(cm, com.mindforme.app.domain.FamilyGallery.class.getName());
            createCache(cm, com.mindforme.app.domain.Family.class.getName());
            createCache(cm, com.mindforme.app.domain.Family.class.getName() + ".members");
            createCache(cm, com.mindforme.app.domain.Family.class.getName() + ".doctors");
            createCache(cm, com.mindforme.app.domain.Family.class.getName() + ".emergencyContacts");
            createCache(cm, com.mindforme.app.domain.Family.class.getName() + ".galleries");
            createCache(cm, com.mindforme.app.domain.Family.class.getName() + ".pets");
            createCache(cm, com.mindforme.app.domain.Family.class.getName() + ".children");
            createCache(cm, com.mindforme.app.domain.Family.class.getName() + ".supporteds");
            createCache(cm, com.mindforme.app.domain.Family.class.getName() + ".mindingNotifications");
            createCache(cm, com.mindforme.app.domain.Pet.class.getName());
            createCache(cm, com.mindforme.app.domain.Pet.class.getName() + ".feedings");
            createCache(cm, com.mindforme.app.domain.Pet.class.getName() + ".rules");
            createCache(cm, com.mindforme.app.domain.Pet.class.getName() + ".allergies");
            createCache(cm, com.mindforme.app.domain.Pet.class.getName() + ".walkings");
            createCache(cm, com.mindforme.app.domain.Pet.class.getName() + ".helpRequests");
            createCache(cm, com.mindforme.app.domain.Child.class.getName());
            createCache(cm, com.mindforme.app.domain.Child.class.getName() + ".interests");
            createCache(cm, com.mindforme.app.domain.Child.class.getName() + ".allergies");
            createCache(cm, com.mindforme.app.domain.Child.class.getName() + ".favouriteFoods");
            createCache(cm, com.mindforme.app.domain.Child.class.getName() + ".medicalConditions");
            createCache(cm, com.mindforme.app.domain.Child.class.getName() + ".helpRequests");
            createCache(cm, com.mindforme.app.domain.Supported.class.getName());
            createCache(cm, com.mindforme.app.domain.Supported.class.getName() + ".helpRequests");
            createCache(cm, com.mindforme.app.domain.FriendRequest.class.getName());
            createCache(cm, com.mindforme.app.domain.Friendship.class.getName());
            createCache(cm, com.mindforme.app.domain.FriendshipGroup.class.getName());
            createCache(cm, com.mindforme.app.domain.FriendshipGroup.class.getName() + ".friendships");
            createCache(cm, com.mindforme.app.domain.HelpRequest.class.getName());
            createCache(cm, com.mindforme.app.domain.HelpRequest.class.getName() + ".gardenRequests");
            createCache(cm, com.mindforme.app.domain.HelpRequest.class.getName() + ".houseRequests");
            createCache(cm, com.mindforme.app.domain.HelpRequest.class.getName() + ".petRequests");
            createCache(cm, com.mindforme.app.domain.HelpRequest.class.getName() + ".supportedRequests");
            createCache(cm, com.mindforme.app.domain.HelpRequest.class.getName() + ".childRequests");
            createCache(cm, com.mindforme.app.domain.PetHelpRequest.class.getName());
            createCache(cm, com.mindforme.app.domain.PetHelpRequest.class.getName() + ".pets");
            createCache(cm, com.mindforme.app.domain.HouseHelpRequest.class.getName());
            createCache(cm, com.mindforme.app.domain.GardenHelpRequest.class.getName());
            createCache(cm, com.mindforme.app.domain.SupportedHelpRequest.class.getName());
            createCache(cm, com.mindforme.app.domain.SupportedHelpRequest.class.getName() + ".supporteds");
            createCache(cm, com.mindforme.app.domain.ChildHelpRequest.class.getName());
            createCache(cm, com.mindforme.app.domain.ChildHelpRequest.class.getName() + ".children");
            createCache(cm, com.mindforme.app.domain.Invitation.class.getName());
            createCache(cm, com.mindforme.app.domain.Message.class.getName());
            createCache(cm, com.mindforme.app.domain.Message.class.getName() + ".items");
            createCache(cm, com.mindforme.app.domain.MessageItem.class.getName());
            createCache(cm, com.mindforme.app.domain.Notification.class.getName());
            createCache(cm, com.mindforme.app.domain.Payment.class.getName());
            createCache(cm, com.mindforme.app.domain.PublicHoliday.class.getName());
            createCache(cm, com.mindforme.app.domain.MindingNotification.class.getName());
            createCache(cm, com.mindforme.app.domain.UserIdentification.class.getName());
            createCache(cm, com.mindforme.app.domain.WorkingWithChildren.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
