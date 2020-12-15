import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Country from './country';
import CountryData from './country-data';
import City from './city';
import CityData from './city-data';
import State from './state';
import StateData from './state-data';
import Address from './address';
import EmergencyContact from './emergency-contact';
import Doctor from './doctor';
import Plan from './plan';
import PlanData from './plan-data';
import Language from './language';
import Coupon from './coupon';
import CouponType from './coupon-type';
import Allergy from './allergy';
import AllergyData from './allergy-data';
import FavouriteFood from './favourite-food';
import FavouriteFoodData from './favourite-food-data';
import Feeding from './feeding';
import FeedingData from './feeding-data';
import Interest from './interest';
import InterestData from './interest-data';
import MedicalCondition from './medical-condition';
import MedicalConditionData from './medical-condition-data';
import PetType from './pet-type';
import PetTypeData from './pet-type-data';
import PetBreed from './pet-breed';
import PetBreedData from './pet-breed-data';
import WalkingOther from './walking-other';
import WalkingOtherData from './walking-other-data';
import Rule from './rule';
import RuleData from './rule-data';
import ChildRelation from './child-relation';
import ChildRelationData from './child-relation-data';
import SupportedRelation from './supported-relation';
import SupportedRelationData from './supported-relation-data';
import FamilyGallery from './family-gallery';
import Family from './family';
import Pet from './pet';
import Child from './child';
import Supported from './supported';
import FriendRequest from './friend-request';
import Friendship from './friendship';
import FriendshipGroup from './friendship-group';
import HelpRequest from './help-request';
import PetHelpRequest from './pet-help-request';
import HouseHelpRequest from './house-help-request';
import GardenHelpRequest from './garden-help-request';
import SupportedHelpRequest from './supported-help-request';
import ChildHelpRequest from './child-help-request';
import Invitation from './invitation';
import Message from './message';
import MessageItem from './message-item';
import Notification from './notification';
import Payment from './payment';
import PublicHoliday from './public-holiday';
import MindingNotification from './minding-notification';
import UserIdentification from './user-identification';
import WorkingWithChildren from './working-with-children';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}country`} component={Country} />
      <ErrorBoundaryRoute path={`${match.url}country-data`} component={CountryData} />
      <ErrorBoundaryRoute path={`${match.url}city`} component={City} />
      <ErrorBoundaryRoute path={`${match.url}city-data`} component={CityData} />
      <ErrorBoundaryRoute path={`${match.url}state`} component={State} />
      <ErrorBoundaryRoute path={`${match.url}state-data`} component={StateData} />
      <ErrorBoundaryRoute path={`${match.url}address`} component={Address} />
      <ErrorBoundaryRoute path={`${match.url}emergency-contact`} component={EmergencyContact} />
      <ErrorBoundaryRoute path={`${match.url}doctor`} component={Doctor} />
      <ErrorBoundaryRoute path={`${match.url}plan`} component={Plan} />
      <ErrorBoundaryRoute path={`${match.url}plan-data`} component={PlanData} />
      <ErrorBoundaryRoute path={`${match.url}language`} component={Language} />
      <ErrorBoundaryRoute path={`${match.url}coupon`} component={Coupon} />
      <ErrorBoundaryRoute path={`${match.url}coupon-type`} component={CouponType} />
      <ErrorBoundaryRoute path={`${match.url}allergy`} component={Allergy} />
      <ErrorBoundaryRoute path={`${match.url}allergy-data`} component={AllergyData} />
      <ErrorBoundaryRoute path={`${match.url}favourite-food`} component={FavouriteFood} />
      <ErrorBoundaryRoute path={`${match.url}favourite-food-data`} component={FavouriteFoodData} />
      <ErrorBoundaryRoute path={`${match.url}feeding`} component={Feeding} />
      <ErrorBoundaryRoute path={`${match.url}feeding-data`} component={FeedingData} />
      <ErrorBoundaryRoute path={`${match.url}interest`} component={Interest} />
      <ErrorBoundaryRoute path={`${match.url}interest-data`} component={InterestData} />
      <ErrorBoundaryRoute path={`${match.url}medical-condition`} component={MedicalCondition} />
      <ErrorBoundaryRoute path={`${match.url}medical-condition-data`} component={MedicalConditionData} />
      <ErrorBoundaryRoute path={`${match.url}pet-type`} component={PetType} />
      <ErrorBoundaryRoute path={`${match.url}pet-type-data`} component={PetTypeData} />
      <ErrorBoundaryRoute path={`${match.url}pet-breed`} component={PetBreed} />
      <ErrorBoundaryRoute path={`${match.url}pet-breed-data`} component={PetBreedData} />
      <ErrorBoundaryRoute path={`${match.url}walking-other`} component={WalkingOther} />
      <ErrorBoundaryRoute path={`${match.url}walking-other-data`} component={WalkingOtherData} />
      <ErrorBoundaryRoute path={`${match.url}rule`} component={Rule} />
      <ErrorBoundaryRoute path={`${match.url}rule-data`} component={RuleData} />
      <ErrorBoundaryRoute path={`${match.url}child-relation`} component={ChildRelation} />
      <ErrorBoundaryRoute path={`${match.url}child-relation-data`} component={ChildRelationData} />
      <ErrorBoundaryRoute path={`${match.url}supported-relation`} component={SupportedRelation} />
      <ErrorBoundaryRoute path={`${match.url}supported-relation-data`} component={SupportedRelationData} />
      <ErrorBoundaryRoute path={`${match.url}family-gallery`} component={FamilyGallery} />
      <ErrorBoundaryRoute path={`${match.url}family`} component={Family} />
      <ErrorBoundaryRoute path={`${match.url}pet`} component={Pet} />
      <ErrorBoundaryRoute path={`${match.url}child`} component={Child} />
      <ErrorBoundaryRoute path={`${match.url}supported`} component={Supported} />
      <ErrorBoundaryRoute path={`${match.url}friend-request`} component={FriendRequest} />
      <ErrorBoundaryRoute path={`${match.url}friendship`} component={Friendship} />
      <ErrorBoundaryRoute path={`${match.url}friendship-group`} component={FriendshipGroup} />
      <ErrorBoundaryRoute path={`${match.url}help-request`} component={HelpRequest} />
      <ErrorBoundaryRoute path={`${match.url}pet-help-request`} component={PetHelpRequest} />
      <ErrorBoundaryRoute path={`${match.url}house-help-request`} component={HouseHelpRequest} />
      <ErrorBoundaryRoute path={`${match.url}garden-help-request`} component={GardenHelpRequest} />
      <ErrorBoundaryRoute path={`${match.url}supported-help-request`} component={SupportedHelpRequest} />
      <ErrorBoundaryRoute path={`${match.url}child-help-request`} component={ChildHelpRequest} />
      <ErrorBoundaryRoute path={`${match.url}invitation`} component={Invitation} />
      <ErrorBoundaryRoute path={`${match.url}message`} component={Message} />
      <ErrorBoundaryRoute path={`${match.url}message-item`} component={MessageItem} />
      <ErrorBoundaryRoute path={`${match.url}notification`} component={Notification} />
      <ErrorBoundaryRoute path={`${match.url}payment`} component={Payment} />
      <ErrorBoundaryRoute path={`${match.url}public-holiday`} component={PublicHoliday} />
      <ErrorBoundaryRoute path={`${match.url}minding-notification`} component={MindingNotification} />
      <ErrorBoundaryRoute path={`${match.url}user-identification`} component={UserIdentification} />
      <ErrorBoundaryRoute path={`${match.url}working-with-children`} component={WorkingWithChildren} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
