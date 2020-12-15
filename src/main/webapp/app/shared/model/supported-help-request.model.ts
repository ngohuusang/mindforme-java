import { Moment } from 'moment';
import { ISupported } from 'app/shared/model/supported.model';
import { TimeType } from 'app/shared/model/enumerations/time-type.model';
import { SupportedHelpType } from 'app/shared/model/enumerations/supported-help-type.model';

export interface ISupportedHelpRequest {
  id?: number;
  totalHelpTime?: number;
  dateFrom?: string;
  dateTo?: string;
  timeFrom?: string;
  timeTo?: string;
  timeType?: TimeType;
  supportedHelpType?: SupportedHelpType;
  content?: string;
  otherTask?: string;
  supporteds?: ISupported[];
  requestId?: number;
}

export const defaultValue: Readonly<ISupportedHelpRequest> = {};
