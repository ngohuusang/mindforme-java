import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IInterest, defaultValue } from 'app/shared/model/interest.model';

export const ACTION_TYPES = {
  SEARCH_INTERESTS: 'interest/SEARCH_INTERESTS',
  FETCH_INTEREST_LIST: 'interest/FETCH_INTEREST_LIST',
  FETCH_INTEREST: 'interest/FETCH_INTEREST',
  CREATE_INTEREST: 'interest/CREATE_INTEREST',
  UPDATE_INTEREST: 'interest/UPDATE_INTEREST',
  DELETE_INTEREST: 'interest/DELETE_INTEREST',
  RESET: 'interest/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IInterest>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type InterestState = Readonly<typeof initialState>;

// Reducer

export default (state: InterestState = initialState, action): InterestState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_INTERESTS):
    case REQUEST(ACTION_TYPES.FETCH_INTEREST_LIST):
    case REQUEST(ACTION_TYPES.FETCH_INTEREST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_INTEREST):
    case REQUEST(ACTION_TYPES.UPDATE_INTEREST):
    case REQUEST(ACTION_TYPES.DELETE_INTEREST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_INTERESTS):
    case FAILURE(ACTION_TYPES.FETCH_INTEREST_LIST):
    case FAILURE(ACTION_TYPES.FETCH_INTEREST):
    case FAILURE(ACTION_TYPES.CREATE_INTEREST):
    case FAILURE(ACTION_TYPES.UPDATE_INTEREST):
    case FAILURE(ACTION_TYPES.DELETE_INTEREST):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_INTERESTS):
    case SUCCESS(ACTION_TYPES.FETCH_INTEREST_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_INTEREST):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_INTEREST):
    case SUCCESS(ACTION_TYPES.UPDATE_INTEREST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_INTEREST):
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

const apiUrl = 'api/interests';
const apiSearchUrl = 'api/_search/interests';

// Actions

export const getSearchEntities: ICrudSearchAction<IInterest> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_INTERESTS,
  payload: axios.get<IInterest>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IInterest> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_INTEREST_LIST,
    payload: axios.get<IInterest>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IInterest> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_INTEREST,
    payload: axios.get<IInterest>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IInterest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_INTEREST,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IInterest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_INTEREST,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IInterest> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_INTEREST,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
