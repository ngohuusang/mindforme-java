export interface IFeedingData {
  id?: number;
  content?: string;
  langName?: string;
  langId?: number;
  feedingName?: string;
  feedingId?: number;
}

export const defaultValue: Readonly<IFeedingData> = {};
