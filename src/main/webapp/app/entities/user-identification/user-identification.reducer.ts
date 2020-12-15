import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUserIdentification, defaultValue } from 'app/shared/model/user-identification.model';

export const ACTION_TYPES = {
  SEARCH_USERIDENTIFICATIONS: 'userIdentification/SEARCH_USERIDENTIFICATIONS',
  FETCH_USERIDENTIFICATION_LIST: 'userIdentification/FETCH_USERIDENTIFICATION_LIST',
  FETCH_USERIDENTIFICATION: 'userIdentification/FETCH_USERIDENTIFICATION',
  CREATE_USERIDENTIFICATION: 'userIdentification/CREATE_USERIDENTIFICATION',
  UPDATE_USERIDENTIFICATION: 'userIdentification/UPDATE_USERIDENTIFICATION',
  DELETE_USERIDENTIFICATION: 'userIdentification/DELETE_USERIDENTIFICATION',
  RESET: 'userIdentification/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUserIdentification>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type UserIdentificationState = Readonly<typeof initialState>;

// Reducer

export default (state: UserIdentificationState = initialState, action): UserIdentificationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_USERIDENTIFICATIONS):
    case REQUEST(ACTION_TYPES.FETCH_USERIDENTIFICATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USERIDENTIFICATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_USERIDENTIFICATION):
    case REQUEST(ACTION_TYPES.UPDATE_USERIDENTIFICATION):
    case REQUEST(ACTION_TYPES.DELETE_USERIDENTIFICATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_USERIDENTIFICATIONS):
    case FAILURE(ACTION_TYPES.FETCH_USERIDENTIFICATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USERIDENTIFICATION):
    case FAILURE(ACTION_TYPES.CREATE_USERIDENTIFICATION):
    case FAILURE(ACTION_TYPES.UPDATE_USERIDENTIFICATION):
    case FAILURE(ACTION_TYPES.DELETE_USERIDENTIFICATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_USERIDENTIFICATIONS):
    case SUCCESS(ACTION_TYPES.FETCH_USERIDENTIFICATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERIDENTIFICATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_USERIDENTIFICATION):
    case SUCCESS(ACTION_TYPES.UPDATE_USERIDENTIFICATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_USERIDENTIFICATION):
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

const apiUrl = 'api/user-identifications';
const apiSearchUrl = 'api/_search/user-identifications';

// Actions

export const getSearchEntities: ICrudSearchAction<IUserIdentification> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_USERIDENTIFICATIONS,
  payload: axios.get<IUserIdentification>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IUserIdentification> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_USERIDENTIFICATION_LIST,
    payload: axios.get<IUserIdentification>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IUserIdentification> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USERIDENTIFICATION,
    payload: axios.get<IUserIdentification>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IUserIdentification> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USERIDENTIFICATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUserIdentification> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USERIDENTIFICATION,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUserIdentification> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USERIDENTIFICATION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
