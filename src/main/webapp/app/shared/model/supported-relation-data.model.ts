export interface ISupportedRelationData {
  id?: number;
  content?: string;
  langName?: string;
  langId?: number;
  relationName?: string;
  relationId?: number;
}

export const defaultValue: Readonly<ISupportedRelationData> = {};
