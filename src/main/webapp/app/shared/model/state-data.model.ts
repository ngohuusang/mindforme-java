export interface IStateData {
  id?: number;
  content?: string;
  langName?: string;
  langId?: number;
  stateName?: string;
  stateId?: number;
}

export const defaultValue: Readonly<IStateData> = {};
