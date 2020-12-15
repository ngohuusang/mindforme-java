import { IAllergyData } from 'app/shared/model/allergy-data.model';
import { IPet } from 'app/shared/model/pet.model';
import { IChild } from 'app/shared/model/child.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IAllergy {
  id?: number;
  name?: string;
  status?: Status;
  allergyData?: IAllergyData[];
  pets?: IPet[];
  children?: IChild[];
}

export const defaultValue: Readonly<IAllergy> = {};
