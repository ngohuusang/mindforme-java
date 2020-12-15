import { IMessageItem } from 'app/shared/model/message-item.model';

export interface IMessage {
  id?: number;
  items?: IMessageItem[];
  senderLogin?: string;
  senderId?: number;
  receiverLogin?: string;
  receiverId?: number;
}

export const defaultValue: Readonly<IMessage> = {};
