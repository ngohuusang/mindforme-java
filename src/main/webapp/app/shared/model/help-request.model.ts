import { Moment } from 'moment';
import { IGardenHelpRequest } from 'app/shared/model/garden-help-request.model';
import { IHouseHelpRequest } from 'app/shared/model/house-help-request.model';
import { IPetHelpRequest } from 'app/shared/model/pet-help-request.model';
import { ISupportedHelpRequest } from 'app/shared/model/supported-help-request.model';
import { IChild } from 'app/shared/model/child.model';
import { HelpType } from 'app/shared/model/enumerations/help-type.model';
import { HelpRequestStatus } from 'app/shared/model/enumerations/help-request-status.model';
import { Privacy } from 'app/shared/model/enumerations/privacy.model';
import { Status } from 'app/shared/model/enumerations/status.model';
import { HelpLocation } from 'app/shared/model/enumerations/help-location.model';

export interface IHelpRequest {
  id?: number;
  title?: string;
  type?: HelpType;
  acceptance?: string;
  requestStatus?: HelpRequestStatus;
  isOffer?: boolean;
  offerTo?: Privacy;
  message?: any;
  instruction?: any;
  status?: Status;
  locationType?: HelpLocation;
  realStart?: string;
  realEnd?: string;
  rating?: number;
  requesterComment?: string;
  helperComment?: string;
  flagged?: number;
  coins?: number;
  bonus?: number;
  gardenRequests?: IGardenHelpRequest[];
  houseRequests?: IHouseHelpRequest[];
  petRequests?: IPetHelpRequest[];
  supportedRequests?: ISupportedHelpRequest[];
  childRequests?: IChild[];
  helpLocationId?: number;
}

export const defaultValue: Readonly<IHelpRequest> = {
  isOffer: false,
};
