import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import country, {
  CountryState
} from 'app/entities/country/country.reducer';
// prettier-ignore
import countryData, {
  CountryDataState
} from 'app/entities/country-data/country-data.reducer';
// prettier-ignore
import city, {
  CityState
} from 'app/entities/city/city.reducer';
// prettier-ignore
import cityData, {
  CityDataState
} from 'app/entities/city-data/city-data.reducer';
// prettier-ignore
import state, {
  StateState
} from 'app/entities/state/state.reducer';
// prettier-ignore
import stateData, {
  StateDataState
} from 'app/entities/state-data/state-data.reducer';
// prettier-ignore
import address, {
  AddressState
} from 'app/entities/address/address.reducer';
// prettier-ignore
import emergencyContact, {
  EmergencyContactState
} from 'app/entities/emergency-contact/emergency-contact.reducer';
// prettier-ignore
import doctor, {
  DoctorState
} from 'app/entities/doctor/doctor.reducer';
// prettier-ignore
import plan, {
  PlanState
} from 'app/entities/plan/plan.reducer';
// prettier-ignore
import planData, {
  PlanDataState
} from 'app/entities/plan-data/plan-data.reducer';
// prettier-ignore
import language, {
  LanguageState
} from 'app/entities/language/language.reducer';
// prettier-ignore
import coupon, {
  CouponState
} from 'app/entities/coupon/coupon.reducer';
// prettier-ignore
import couponType, {
  CouponTypeState
} from 'app/entities/coupon-type/coupon-type.reducer';
// prettier-ignore
import allergy, {
  AllergyState
} from 'app/entities/allergy/allergy.reducer';
// prettier-ignore
import allergyData, {
  AllergyDataState
} from 'app/entities/allergy-data/allergy-data.reducer';
// prettier-ignore
import favouriteFood, {
  FavouriteFoodState
} from 'app/entities/favourite-food/favourite-food.reducer';
// prettier-ignore
import favouriteFoodData, {
  FavouriteFoodDataState
} from 'app/entities/favourite-food-data/favourite-food-data.reducer';
// prettier-ignore
import feeding, {
  FeedingState
} from 'app/entities/feeding/feeding.reducer';
// prettier-ignore
import feedingData, {
  FeedingDataState
} from 'app/entities/feeding-data/feeding-data.reducer';
// prettier-ignore
import interest, {
  InterestState
} from 'app/entities/interest/interest.reducer';
// prettier-ignore
import interestData, {
  InterestDataState
} from 'app/entities/interest-data/interest-data.reducer';
// prettier-ignore
import medicalCondition, {
  MedicalConditionState
} from 'app/entities/medical-condition/medical-condition.reducer';
// prettier-ignore
import medicalConditionData, {
  MedicalConditionDataState
} from 'app/entities/medical-condition-data/medical-condition-data.reducer';
// prettier-ignore
import petType, {
  PetTypeState
} from 'app/entities/pet-type/pet-type.reducer';
// prettier-ignore
import petTypeData, {
  PetTypeDataState
} from 'app/entities/pet-type-data/pet-type-data.reducer';
// prettier-ignore
import petBreed, {
  PetBreedState
} from 'app/entities/pet-breed/pet-breed.reducer';
// prettier-ignore
import petBreedData, {
  PetBreedDataState
} from 'app/entities/pet-breed-data/pet-breed-data.reducer';
// prettier-ignore
import walkingOther, {
  WalkingOtherState
} from 'app/entities/walking-other/walking-other.reducer';
// prettier-ignore
import walkingOtherData, {
  WalkingOtherDataState
} from 'app/entities/walking-other-data/walking-other-data.reducer';
// prettier-ignore
import rule, {
  RuleState
} from 'app/entities/rule/rule.reducer';
// prettier-ignore
import ruleData, {
  RuleDataState
} from 'app/entities/rule-data/rule-data.reducer';
// prettier-ignore
import childRelation, {
  ChildRelationState
} from 'app/entities/child-relation/child-relation.reducer';
// prettier-ignore
import childRelationData, {
  ChildRelationDataState
} from 'app/entities/child-relation-data/child-relation-data.reducer';
// prettier-ignore
import supportedRelation, {
  SupportedRelationState
} from 'app/entities/supported-relation/supported-relation.reducer';
// prettier-ignore
import supportedRelationData, {
  SupportedRelationDataState
} from 'app/entities/supported-relation-data/supported-relation-data.reducer';
// prettier-ignore
import familyGallery, {
  FamilyGalleryState
} from 'app/entities/family-gallery/family-gallery.reducer';
// prettier-ignore
import family, {
  FamilyState
} from 'app/entities/family/family.reducer';
// prettier-ignore
import pet, {
  PetState
} from 'app/entities/pet/pet.reducer';
// prettier-ignore
import child, {
  ChildState
} from 'app/entities/child/child.reducer';
// prettier-ignore
import supported, {
  SupportedState
} from 'app/entities/supported/supported.reducer';
// prettier-ignore
import friendRequest, {
  FriendRequestState
} from 'app/entities/friend-request/friend-request.reducer';
// prettier-ignore
import friendship, {
  FriendshipState
} from 'app/entities/friendship/friendship.reducer';
// prettier-ignore
import friendshipGroup, {
  FriendshipGroupState
} from 'app/entities/friendship-group/friendship-group.reducer';
// prettier-ignore
import helpRequest, {
  HelpRequestState
} from 'app/entities/help-request/help-request.reducer';
// prettier-ignore
import petHelpRequest, {
  PetHelpRequestState
} from 'app/entities/pet-help-request/pet-help-request.reducer';
// prettier-ignore
import houseHelpRequest, {
  HouseHelpRequestState
} from 'app/entities/house-help-request/house-help-request.reducer';
// prettier-ignore
import gardenHelpRequest, {
  GardenHelpRequestState
} from 'app/entities/garden-help-request/garden-help-request.reducer';
// prettier-ignore
import supportedHelpRequest, {
  SupportedHelpRequestState
} from 'app/entities/supported-help-request/supported-help-request.reducer';
// prettier-ignore
import childHelpRequest, {
  ChildHelpRequestState
} from 'app/entities/child-help-request/child-help-request.reducer';
// prettier-ignore
import invitation, {
  InvitationState
} from 'app/entities/invitation/invitation.reducer';
// prettier-ignore
import message, {
  MessageState
} from 'app/entities/message/message.reducer';
// prettier-ignore
import messageItem, {
  MessageItemState
} from 'app/entities/message-item/message-item.reducer';
// prettier-ignore
import notification, {
  NotificationState
} from 'app/entities/notification/notification.reducer';
// prettier-ignore
import payment, {
  PaymentState
} from 'app/entities/payment/payment.reducer';
// prettier-ignore
import publicHoliday, {
  PublicHolidayState
} from 'app/entities/public-holiday/public-holiday.reducer';
// prettier-ignore
import mindingNotification, {
  MindingNotificationState
} from 'app/entities/minding-notification/minding-notification.reducer';
// prettier-ignore
import userIdentification, {
  UserIdentificationState
} from 'app/entities/user-identification/user-identification.reducer';
// prettier-ignore
import workingWithChildren, {
  WorkingWithChildrenState
} from 'app/entities/working-with-children/working-with-children.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly country: CountryState;
  readonly countryData: CountryDataState;
  readonly city: CityState;
  readonly cityData: CityDataState;
  readonly state: StateState;
  readonly stateData: StateDataState;
  readonly address: AddressState;
  readonly emergencyContact: EmergencyContactState;
  readonly doctor: DoctorState;
  readonly plan: PlanState;
  readonly planData: PlanDataState;
  readonly language: LanguageState;
  readonly coupon: CouponState;
  readonly couponType: CouponTypeState;
  readonly allergy: AllergyState;
  readonly allergyData: AllergyDataState;
  readonly favouriteFood: FavouriteFoodState;
  readonly favouriteFoodData: FavouriteFoodDataState;
  readonly feeding: FeedingState;
  readonly feedingData: FeedingDataState;
  readonly interest: InterestState;
  readonly interestData: InterestDataState;
  readonly medicalCondition: MedicalConditionState;
  readonly medicalConditionData: MedicalConditionDataState;
  readonly petType: PetTypeState;
  readonly petTypeData: PetTypeDataState;
  readonly petBreed: PetBreedState;
  readonly petBreedData: PetBreedDataState;
  readonly walkingOther: WalkingOtherState;
  readonly walkingOtherData: WalkingOtherDataState;
  readonly rule: RuleState;
  readonly ruleData: RuleDataState;
  readonly childRelation: ChildRelationState;
  readonly childRelationData: ChildRelationDataState;
  readonly supportedRelation: SupportedRelationState;
  readonly supportedRelationData: SupportedRelationDataState;
  readonly familyGallery: FamilyGalleryState;
  readonly family: FamilyState;
  readonly pet: PetState;
  readonly child: ChildState;
  readonly supported: SupportedState;
  readonly friendRequest: FriendRequestState;
  readonly friendship: FriendshipState;
  readonly friendshipGroup: FriendshipGroupState;
  readonly helpRequest: HelpRequestState;
  readonly petHelpRequest: PetHelpRequestState;
  readonly houseHelpRequest: HouseHelpRequestState;
  readonly gardenHelpRequest: GardenHelpRequestState;
  readonly supportedHelpRequest: SupportedHelpRequestState;
  readonly childHelpRequest: ChildHelpRequestState;
  readonly invitation: InvitationState;
  readonly message: MessageState;
  readonly messageItem: MessageItemState;
  readonly notification: NotificationState;
  readonly payment: PaymentState;
  readonly publicHoliday: PublicHolidayState;
  readonly mindingNotification: MindingNotificationState;
  readonly userIdentification: UserIdentificationState;
  readonly workingWithChildren: WorkingWithChildrenState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  country,
  countryData,
  city,
  cityData,
  state,
  stateData,
  address,
  emergencyContact,
  doctor,
  plan,
  planData,
  language,
  coupon,
  couponType,
  allergy,
  allergyData,
  favouriteFood,
  favouriteFoodData,
  feeding,
  feedingData,
  interest,
  interestData,
  medicalCondition,
  medicalConditionData,
  petType,
  petTypeData,
  petBreed,
  petBreedData,
  walkingOther,
  walkingOtherData,
  rule,
  ruleData,
  childRelation,
  childRelationData,
  supportedRelation,
  supportedRelationData,
  familyGallery,
  family,
  pet,
  child,
  supported,
  friendRequest,
  friendship,
  friendshipGroup,
  helpRequest,
  petHelpRequest,
  houseHelpRequest,
  gardenHelpRequest,
  supportedHelpRequest,
  childHelpRequest,
  invitation,
  message,
  messageItem,
  notification,
  payment,
  publicHoliday,
  mindingNotification,
  userIdentification,
  workingWithChildren,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
