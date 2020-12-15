import { NotificationType } from 'app/shared/model/enumerations/notification-type.model';

export interface INotification {
  id?: number;
  type?: NotificationType;
  content?: string;
  link?: string;
  metaData?: any;
  read?: boolean;
  senderLogin?: string;
  senderId?: number;
  receiverLogin?: string;
  receiverId?: number;
}

export const defaultValue: Readonly<INotification> = {
  read: false,
};
