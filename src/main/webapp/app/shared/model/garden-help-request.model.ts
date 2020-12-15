import { Moment } from 'moment';
import { Equipment } from 'app/shared/model/enumerations/equipment.model';
import { RubbishLoadType } from 'app/shared/model/enumerations/rubbish-load-type.model';
import { HouseProvided } from 'app/shared/model/enumerations/house-provided.model';

export interface IGardenHelpRequest {
  id?: number;
  totalHelpTime?: number;
  dateFrom?: string;
  dateTo?: string;
  timeFrom?: string;
  timeTo?: string;
  services?: string;
  edgeTrimming?: boolean;
  mowingTime?: string;
  mowingEquipment?: Equipment;
  lawnTime?: string;
  lawnEquipment?: Equipment;
  rubbishLoad?: number;
  rubbishLoadType?: RubbishLoadType;
  otherDescription?: string;
  otherHours?: string;
  otherEquipment?: Equipment;
  provideType?: HouseProvided;
  provideFor?: number;
  requestId?: number;
}

export const defaultValue: Readonly<IGardenHelpRequest> = {
  edgeTrimming: false,
};
