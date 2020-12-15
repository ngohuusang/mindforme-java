export interface IFriendship {
  id?: number;
  friendLogin?: string;
  friendId?: number;
  userLogin?: string;
  userId?: number;
  groupName?: string;
  groupId?: number;
}

export const defaultValue: Readonly<IFriendship> = {};
