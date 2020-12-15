export interface IMedicalConditionData {
  id?: number;
  content?: string;
  langName?: string;
  langId?: number;
  medicalConditionName?: string;
  medicalConditionId?: number;
}

export const defaultValue: Readonly<IMedicalConditionData> = {};
