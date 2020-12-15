export interface ILanguage {
  id?: number;
  code?: string;
  name?: string;
  isDefault?: boolean;
  active?: boolean;
}

export const defaultValue: Readonly<ILanguage> = {
  isDefault: false,
  active: false,
};
