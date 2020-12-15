import { Moment } from 'moment';
import { IFeeding } from 'app/shared/model/feeding.model';
import { IRule } from 'app/shared/model/rule.model';
import { IAllergy } from 'app/shared/model/allergy.model';
import { IWalkingOther } from 'app/shared/model/walking-other.model';
import { IPetHelpRequest } from 'app/shared/model/pet-help-request.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IPet {
  id?: number;
  name?: string;
  imageUrl?: string;
  birthday?: string;
  status?: Status;
  breedName?: string;
  breedId?: number;
  petTypeName?: string;
  petTypeId?: number;
  feedings?: IFeeding[];
  rules?: IRule[];
  allergies?: IAllergy[];
  walkings?: IWalkingOther[];
  familyName?: string;
  familyId?: number;
  helpRequests?: IPetHelpRequest[];
}

export const defaultValue: Readonly<IPet> = {};
