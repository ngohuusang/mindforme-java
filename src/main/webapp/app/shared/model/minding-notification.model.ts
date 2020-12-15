import { Privacy } from 'app/shared/model/enumerations/privacy.model';
import { Frequency } from 'app/shared/model/enumerations/frequency.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IMindingNotification {
  id?: number;
  type?: Privacy;
  minding?: any;
  pushNotification?: boolean;
  email?: Frequency;
  status?: Status;
  familyId?: number;
}

export const defaultValue: Readonly<IMindingNotification> = {
  pushNotification: false,
};
