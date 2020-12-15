import { ISupportedRelationData } from 'app/shared/model/supported-relation-data.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface ISupportedRelation {
  id?: number;
  name?: string;
  status?: Status;
  relationData?: ISupportedRelationData[];
}

export const defaultValue: Readonly<ISupportedRelation> = {};
