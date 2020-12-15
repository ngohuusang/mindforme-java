export interface IPetBreedData {
  id?: number;
  content?: string;
  langName?: string;
  langId?: number;
  petBreedName?: string;
  petBreedId?: number;
}

export const defaultValue: Readonly<IPetBreedData> = {};
