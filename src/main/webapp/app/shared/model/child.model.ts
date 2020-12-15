import { Moment } from 'moment';
import { IInterest } from 'app/shared/model/interest.model';
import { IAllergy } from 'app/shared/model/allergy.model';
import { IFavouriteFood } from 'app/shared/model/favourite-food.model';
import { IMedicalCondition } from 'app/shared/model/medical-condition.model';
import { IChildHelpRequest } from 'app/shared/model/child-help-request.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IChild {
  id?: number;
  firstName?: string;
  lastName?: string;
  imageUrl?: string;
  birthday?: string;
  status?: Status;
  relationName?: string;
  relationId?: number;
  interests?: IInterest[];
  allergies?: IAllergy[];
  favouriteFoods?: IFavouriteFood[];
  medicalConditions?: IMedicalCondition[];
  familyName?: string;
  familyId?: number;
  requestId?: number;
  helpRequests?: IChildHelpRequest[];
}

export const defaultValue: Readonly<IChild> = {};
