export interface IMessageItem {
  id?: number;
  content?: string;
  read?: boolean;
  senderLogin?: string;
  senderId?: number;
  messageId?: number;
}

export const defaultValue: Readonly<IMessageItem> = {
  read: false,
};
