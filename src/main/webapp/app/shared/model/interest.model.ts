import { IInterestData } from 'app/shared/model/interest-data.model';
import { IChild } from 'app/shared/model/child.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IInterest {
  id?: number;
  name?: string;
  status?: Status;
  interestData?: IInterestData[];
  children?: IChild[];
}

export const defaultValue: Readonly<IInterest> = {};
