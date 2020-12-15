import { IFeedingData } from 'app/shared/model/feeding-data.model';
import { IPet } from 'app/shared/model/pet.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IFeeding {
  id?: number;
  name?: string;
  status?: Status;
  feedingData?: IFeedingData[];
  pets?: IPet[];
}

export const defaultValue: Readonly<IFeeding> = {};
