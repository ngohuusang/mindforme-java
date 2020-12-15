import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFriendship, defaultValue } from 'app/shared/model/friendship.model';

export const ACTION_TYPES = {
  SEARCH_FRIENDSHIPS: 'friendship/SEARCH_FRIENDSHIPS',
  FETCH_FRIENDSHIP_LIST: 'friendship/FETCH_FRIENDSHIP_LIST',
  FETCH_FRIENDSHIP: 'friendship/FETCH_FRIENDSHIP',
  CREATE_FRIENDSHIP: 'friendship/CREATE_FRIENDSHIP',
  UPDATE_FRIENDSHIP: 'friendship/UPDATE_FRIENDSHIP',
  DELETE_FRIENDSHIP: 'friendship/DELETE_FRIENDSHIP',
  RESET: 'friendship/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFriendship>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type FriendshipState = Readonly<typeof initialState>;

// Reducer

export default (state: FriendshipState = initialState, action): FriendshipState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_FRIENDSHIPS):
    case REQUEST(ACTION_TYPES.FETCH_FRIENDSHIP_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FRIENDSHIP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_FRIENDSHIP):
    case REQUEST(ACTION_TYPES.UPDATE_FRIENDSHIP):
    case REQUEST(ACTION_TYPES.DELETE_FRIENDSHIP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_FRIENDSHIPS):
    case FAILURE(ACTION_TYPES.FETCH_FRIENDSHIP_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FRIENDSHIP):
    case FAILURE(ACTION_TYPES.CREATE_FRIENDSHIP):
    case FAILURE(ACTION_TYPES.UPDATE_FRIENDSHIP):
    case FAILURE(ACTION_TYPES.DELETE_FRIENDSHIP):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_FRIENDSHIPS):
    case SUCCESS(ACTION_TYPES.FETCH_FRIENDSHIP_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_FRIENDSHIP):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_FRIENDSHIP):
    case SUCCESS(ACTION_TYPES.UPDATE_FRIENDSHIP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_FRIENDSHIP):
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

const apiUrl = 'api/friendships';
const apiSearchUrl = 'api/_search/friendships';

// Actions

export const getSearchEntities: ICrudSearchAction<IFriendship> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_FRIENDSHIPS,
  payload: axios.get<IFriendship>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IFriendship> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_FRIENDSHIP_LIST,
    payload: axios.get<IFriendship>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IFriendship> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FRIENDSHIP,
    payload: axios.get<IFriendship>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IFriendship> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FRIENDSHIP,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFriendship> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FRIENDSHIP,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFriendship> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FRIENDSHIP,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
