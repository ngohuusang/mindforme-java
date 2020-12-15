export interface IPlanData {
  id?: number;
  content?: string;
  langName?: string;
  langId?: number;
  planName?: string;
  planId?: number;
}

export const defaultValue: Readonly<IPlanData> = {};
