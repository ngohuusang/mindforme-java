import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFriendshipGroup, defaultValue } from 'app/shared/model/friendship-group.model';

export const ACTION_TYPES = {
  SEARCH_FRIENDSHIPGROUPS: 'friendshipGroup/SEARCH_FRIENDSHIPGROUPS',
  FETCH_FRIENDSHIPGROUP_LIST: 'friendshipGroup/FETCH_FRIENDSHIPGROUP_LIST',
  FETCH_FRIENDSHIPGROUP: 'friendshipGroup/FETCH_FRIENDSHIPGROUP',
  CREATE_FRIENDSHIPGROUP: 'friendshipGroup/CREATE_FRIENDSHIPGROUP',
  UPDATE_FRIENDSHIPGROUP: 'friendshipGroup/UPDATE_FRIENDSHIPGROUP',
  DELETE_FRIENDSHIPGROUP: 'friendshipGroup/DELETE_FRIENDSHIPGROUP',
  RESET: 'friendshipGroup/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFriendshipGroup>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type FriendshipGroupState = Readonly<typeof initialState>;

// Reducer

export default (state: FriendshipGroupState = initialState, action): FriendshipGroupState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_FRIENDSHIPGROUPS):
    case REQUEST(ACTION_TYPES.FETCH_FRIENDSHIPGROUP_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FRIENDSHIPGROUP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_FRIENDSHIPGROUP):
    case REQUEST(ACTION_TYPES.UPDATE_FRIENDSHIPGROUP):
    case REQUEST(ACTION_TYPES.DELETE_FRIENDSHIPGROUP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_FRIENDSHIPGROUPS):
    case FAILURE(ACTION_TYPES.FETCH_FRIENDSHIPGROUP_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FRIENDSHIPGROUP):
    case FAILURE(ACTION_TYPES.CREATE_FRIENDSHIPGROUP):
    case FAILURE(ACTION_TYPES.UPDATE_FRIENDSHIPGROUP):
    case FAILURE(ACTION_TYPES.DELETE_FRIENDSHIPGROUP):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_FRIENDSHIPGROUPS):
    case SUCCESS(ACTION_TYPES.FETCH_FRIENDSHIPGROUP_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_FRIENDSHIPGROUP):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_FRIENDSHIPGROUP):
    case SUCCESS(ACTION_TYPES.UPDATE_FRIENDSHIPGROUP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_FRIENDSHIPGROUP):
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

const apiUrl = 'api/friendship-groups';
const apiSearchUrl = 'api/_search/friendship-groups';

// Actions

export const getSearchEntities: ICrudSearchAction<IFriendshipGroup> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_FRIENDSHIPGROUPS,
  payload: axios.get<IFriendshipGroup>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IFriendshipGroup> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_FRIENDSHIPGROUP_LIST,
    payload: axios.get<IFriendshipGroup>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IFriendshipGroup> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FRIENDSHIPGROUP,
    payload: axios.get<IFriendshipGroup>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IFriendshipGroup> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FRIENDSHIPGROUP,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFriendshipGroup> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FRIENDSHIPGROUP,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFriendshipGroup> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FRIENDSHIPGROUP,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
