import { Moment } from 'moment';
import { IPet } from 'app/shared/model/pet.model';

export interface IPetHelpRequest {
  id?: number;
  totalHelpTime?: number;
  dateFrom?: string;
  dateTo?: string;
  timeFrom?: string;
  timeTo?: string;
  content?: string;
  pets?: IPet[];
  requestId?: number;
}

export const defaultValue: Readonly<IPetHelpRequest> = {};
