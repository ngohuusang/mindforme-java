import { IPetBreedData } from 'app/shared/model/pet-breed-data.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IPetBreed {
  id?: number;
  name?: string;
  status?: Status;
  petBreedData?: IPetBreedData[];
  petTypeName?: string;
  petTypeId?: number;
}

export const defaultValue: Readonly<IPetBreed> = {};
