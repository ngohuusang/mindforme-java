import { Moment } from 'moment';
import { Equipment } from 'app/shared/model/enumerations/equipment.model';
import { HouseProvided } from 'app/shared/model/enumerations/house-provided.model';

export interface IHouseHelpRequest {
  id?: number;
  services?: any;
  cleaningTime?: number;
  cleaningFromTime?: string;
  cleaningToTime?: string;
  cleaningEquipment?: Equipment;
  cleaningDescription?: string;
  cookingFromTime?: string;
  cookingToTime?: string;
  cookingServes?: number;
  cookingData?: string;
  pickupType?: number;
  houseMindingDetail?: any;
  mailFromDate?: string;
  mailToDate?: string;
  mailAfter?: string;
  mailCollectionDays?: string;
  otherDescription?: string;
  otherHours?: number;
  otherFromTime?: string;
  otherToTime?: string;
  otherEquipment?: Equipment;
  provideFor?: number;
  provideType?: HouseProvided;
  requestId?: number;
}

export const defaultValue: Readonly<IHouseHelpRequest> = {};
