export interface IRuleData {
  id?: number;
  content?: string;
  langName?: string;
  langId?: number;
  ruleName?: string;
  ruleId?: number;
}

export const defaultValue: Readonly<IRuleData> = {};
