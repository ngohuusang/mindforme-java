export interface IInterestData {
  id?: number;
  content?: string;
  langName?: string;
  langId?: number;
  interestName?: string;
  interestId?: number;
}

export const defaultValue: Readonly<IInterestData> = {};
