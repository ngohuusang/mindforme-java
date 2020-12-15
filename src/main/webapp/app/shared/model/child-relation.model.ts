import { IChildRelationData } from 'app/shared/model/child-relation-data.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IChildRelation {
  id?: number;
  name?: string;
  status?: Status;
  relationData?: IChildRelationData[];
}

export const defaultValue: Readonly<IChildRelation> = {};
