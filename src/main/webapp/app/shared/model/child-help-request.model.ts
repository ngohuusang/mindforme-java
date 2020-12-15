import { Moment } from 'moment';
import { IChild } from 'app/shared/model/child.model';
import { TimeType } from 'app/shared/model/enumerations/time-type.model';

export interface IChildHelpRequest {
  id?: number;
  totalHelpTime?: number;
  dateFrom?: string;
  dateTo?: string;
  timeFrom?: string;
  timeTo?: string;
  timeType?: TimeType;
  content?: string;
  otherTask?: string;
  children?: IChild[];
}

export const defaultValue: Readonly<IChildHelpRequest> = {};
