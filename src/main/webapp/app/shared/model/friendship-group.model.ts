import { IFriendship } from 'app/shared/model/friendship.model';

export interface IFriendshipGroup {
  id?: number;
  name?: string;
  description?: string;
  order?: number;
  numberOfMembers?: number;
  friendships?: IFriendship[];
}

export const defaultValue: Readonly<IFriendshipGroup> = {};
