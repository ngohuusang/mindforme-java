import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISupported, defaultValue } from 'app/shared/model/supported.model';

export const ACTION_TYPES = {
  SEARCH_SUPPORTEDS: 'supported/SEARCH_SUPPORTEDS',
  FETCH_SUPPORTED_LIST: 'supported/FETCH_SUPPORTED_LIST',
  FETCH_SUPPORTED: 'supported/FETCH_SUPPORTED',
  CREATE_SUPPORTED: 'supported/CREATE_SUPPORTED',
  UPDATE_SUPPORTED: 'supported/UPDATE_SUPPORTED',
  DELETE_SUPPORTED: 'supported/DELETE_SUPPORTED',
  RESET: 'supported/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISupported>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SupportedState = Readonly<typeof initialState>;

// Reducer

export default (state: SupportedState = initialState, action): SupportedState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SUPPORTEDS):
    case REQUEST(ACTION_TYPES.FETCH_SUPPORTED_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SUPPORTED):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SUPPORTED):
    case REQUEST(ACTION_TYPES.UPDATE_SUPPORTED):
    case REQUEST(ACTION_TYPES.DELETE_SUPPORTED):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_SUPPORTEDS):
    case FAILURE(ACTION_TYPES.FETCH_SUPPORTED_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SUPPORTED):
    case FAILURE(ACTION_TYPES.CREATE_SUPPORTED):
    case FAILURE(ACTION_TYPES.UPDATE_SUPPORTED):
    case FAILURE(ACTION_TYPES.DELETE_SUPPORTED):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_SUPPORTEDS):
    case SUCCESS(ACTION_TYPES.FETCH_SUPPORTED_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUPPORTED):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SUPPORTED):
    case SUCCESS(ACTION_TYPES.UPDATE_SUPPORTED):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SUPPORTED):
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

const apiUrl = 'api/supporteds';
const apiSearchUrl = 'api/_search/supporteds';

// Actions

export const getSearchEntities: ICrudSearchAction<ISupported> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SUPPORTEDS,
  payload: axios.get<ISupported>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<ISupported> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SUPPORTED_LIST,
    payload: axios.get<ISupported>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISupported> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SUPPORTED,
    payload: axios.get<ISupported>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISupported> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SUPPORTED,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISupported> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SUPPORTED,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISupported> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SUPPORTED,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
