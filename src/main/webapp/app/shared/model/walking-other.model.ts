import { IWalkingOtherData } from 'app/shared/model/walking-other-data.model';
import { IPet } from 'app/shared/model/pet.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IWalkingOther {
  id?: number;
  name?: string;
  status?: Status;
  walkingOtherData?: IWalkingOtherData[];
  pets?: IPet[];
}

export const defaultValue: Readonly<IWalkingOther> = {};
