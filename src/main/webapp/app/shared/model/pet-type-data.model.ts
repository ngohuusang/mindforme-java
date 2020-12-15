export interface IPetTypeData {
  id?: number;
  content?: string;
  langName?: string;
  langId?: number;
  petTypeName?: string;
  petTypeId?: number;
}

export const defaultValue: Readonly<IPetTypeData> = {};
