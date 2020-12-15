import { Moment } from 'moment';
import { ISupportedHelpRequest } from 'app/shared/model/supported-help-request.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface ISupported {
  id?: number;
  firstName?: string;
  lastName?: string;
  imageUrl?: string;
  birthday?: string;
  status?: Status;
  relationName?: string;
  relationId?: number;
  familyName?: string;
  familyId?: number;
  helpRequests?: ISupportedHelpRequest[];
}

export const defaultValue: Readonly<ISupported> = {};
