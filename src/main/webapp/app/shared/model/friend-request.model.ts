import { FriendRequestStatus } from 'app/shared/model/enumerations/friend-request-status.model';

export interface IFriendRequest {
  id?: number;
  status?: FriendRequestStatus;
  friendLogin?: string;
  friendId?: number;
  userLogin?: string;
  userId?: number;
}

export const defaultValue: Readonly<IFriendRequest> = {};
