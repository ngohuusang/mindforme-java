import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFriendRequest, defaultValue } from 'app/shared/model/friend-request.model';

export const ACTION_TYPES = {
  SEARCH_FRIENDREQUESTS: 'friendRequest/SEARCH_FRIENDREQUESTS',
  FETCH_FRIENDREQUEST_LIST: 'friendRequest/FETCH_FRIENDREQUEST_LIST',
  FETCH_FRIENDREQUEST: 'friendRequest/FETCH_FRIENDREQUEST',
  CREATE_FRIENDREQUEST: 'friendRequest/CREATE_FRIENDREQUEST',
  UPDATE_FRIENDREQUEST: 'friendRequest/UPDATE_FRIENDREQUEST',
  DELETE_FRIENDREQUEST: 'friendRequest/DELETE_FRIENDREQUEST',
  RESET: 'friendRequest/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFriendRequest>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type FriendRequestState = Readonly<typeof initialState>;

// Reducer

export default (state: FriendRequestState = initialState, action): FriendRequestState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_FRIENDREQUESTS):
    case REQUEST(ACTION_TYPES.FETCH_FRIENDREQUEST_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FRIENDREQUEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_FRIENDREQUEST):
    case REQUEST(ACTION_TYPES.UPDATE_FRIENDREQUEST):
    case REQUEST(ACTION_TYPES.DELETE_FRIENDREQUEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_FRIENDREQUESTS):
    case FAILURE(ACTION_TYPES.FETCH_FRIENDREQUEST_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FRIENDREQUEST):
    case FAILURE(ACTION_TYPES.CREATE_FRIENDREQUEST):
    case FAILURE(ACTION_TYPES.UPDATE_FRIENDREQUEST):
    case FAILURE(ACTION_TYPES.DELETE_FRIENDREQUEST):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_FRIENDREQUESTS):
    case SUCCESS(ACTION_TYPES.FETCH_FRIENDREQUEST_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_FRIENDREQUEST):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_FRIENDREQUEST):
    case SUCCESS(ACTION_TYPES.UPDATE_FRIENDREQUEST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_FRIENDREQUEST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/friend-requests';
const apiSearchUrl = 'api/_search/friend-requests';

// Actions

export const getSearchEntities: ICrudSearchAction<IFriendRequest> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_FRIENDREQUESTS,
  payload: axios.get<IFriendRequest>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IFriendRequest> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_FRIENDREQUEST_LIST,
    payload: axios.get<IFriendRequest>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IFriendRequest> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FRIENDREQUEST,
    payload: axios.get<IFriendRequest>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IFriendRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FRIENDREQUEST,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFriendRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FRIENDREQUEST,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFriendRequest> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FRIENDREQUEST,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
