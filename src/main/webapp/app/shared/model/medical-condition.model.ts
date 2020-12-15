import { IMedicalConditionData } from 'app/shared/model/medical-condition-data.model';
import { IChild } from 'app/shared/model/child.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IMedicalCondition {
  id?: number;
  name?: string;
  status?: Status;
  medicalConditionData?: IMedicalConditionData[];
  children?: IChild[];
}

export const defaultValue: Readonly<IMedicalCondition> = {};
