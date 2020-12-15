import { IPlanData } from 'app/shared/model/plan-data.model';
import { Status } from 'app/shared/model/enumerations/status.model';
import { PlanType } from 'app/shared/model/enumerations/plan-type.model';

export interface IPlan {
  id?: number;
  name?: string;
  amount?: string;
  months?: number;
  status?: Status;
  type?: PlanType;
  plansData?: IPlanData[];
}

export const defaultValue: Readonly<IPlan> = {};
