import { IRuleData } from 'app/shared/model/rule-data.model';
import { IPet } from 'app/shared/model/pet.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IRule {
  id?: number;
  name?: string;
  status?: Status;
  ruleData?: IRuleData[];
  pets?: IPet[];
}

export const defaultValue: Readonly<IRule> = {};
