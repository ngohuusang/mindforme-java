import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWalkingOther, defaultValue } from 'app/shared/model/walking-other.model';

export const ACTION_TYPES = {
  SEARCH_WALKINGOTHERS: 'walkingOther/SEARCH_WALKINGOTHERS',
  FETCH_WALKINGOTHER_LIST: 'walkingOther/FETCH_WALKINGOTHER_LIST',
  FETCH_WALKINGOTHER: 'walkingOther/FETCH_WALKINGOTHER',
  CREATE_WALKINGOTHER: 'walkingOther/CREATE_WALKINGOTHER',
  UPDATE_WALKINGOTHER: 'walkingOther/UPDATE_WALKINGOTHER',
  DELETE_WALKINGOTHER: 'walkingOther/DELETE_WALKINGOTHER',
  RESET: 'walkingOther/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWalkingOther>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type WalkingOtherState = Readonly<typeof initialState>;

// Reducer

export default (state: WalkingOtherState = initialState, action): WalkingOtherState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_WALKINGOTHERS):
    case REQUEST(ACTION_TYPES.FETCH_WALKINGOTHER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WALKINGOTHER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_WALKINGOTHER):
    case REQUEST(ACTION_TYPES.UPDATE_WALKINGOTHER):
    case REQUEST(ACTION_TYPES.DELETE_WALKINGOTHER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_WALKINGOTHERS):
    case FAILURE(ACTION_TYPES.FETCH_WALKINGOTHER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WALKINGOTHER):
    case FAILURE(ACTION_TYPES.CREATE_WALKINGOTHER):
    case FAILURE(ACTION_TYPES.UPDATE_WALKINGOTHER):
    case FAILURE(ACTION_TYPES.DELETE_WALKINGOTHER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_WALKINGOTHERS):
    case SUCCESS(ACTION_TYPES.FETCH_WALKINGOTHER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_WALKINGOTHER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_WALKINGOTHER):
    case SUCCESS(ACTION_TYPES.UPDATE_WALKINGOTHER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_WALKINGOTHER):
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

const apiUrl = 'api/walking-others';
const apiSearchUrl = 'api/_search/walking-others';

// Actions

export const getSearchEntities: ICrudSearchAction<IWalkingOther> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_WALKINGOTHERS,
  payload: axios.get<IWalkingOther>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IWalkingOther> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_WALKINGOTHER_LIST,
    payload: axios.get<IWalkingOther>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IWalkingOther> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WALKINGOTHER,
    payload: axios.get<IWalkingOther>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IWalkingOther> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WALKINGOTHER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWalkingOther> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WALKINGOTHER,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWalkingOther> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WALKINGOTHER,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
