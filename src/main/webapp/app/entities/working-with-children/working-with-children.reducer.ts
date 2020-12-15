import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWorkingWithChildren, defaultValue } from 'app/shared/model/working-with-children.model';

export const ACTION_TYPES = {
  SEARCH_WORKINGWITHCHILDREN: 'workingWithChildren/SEARCH_WORKINGWITHCHILDREN',
  FETCH_WORKINGWITHCHILDREN_LIST: 'workingWithChildren/FETCH_WORKINGWITHCHILDREN_LIST',
  FETCH_WORKINGWITHCHILDREN: 'workingWithChildren/FETCH_WORKINGWITHCHILDREN',
  CREATE_WORKINGWITHCHILDREN: 'workingWithChildren/CREATE_WORKINGWITHCHILDREN',
  UPDATE_WORKINGWITHCHILDREN: 'workingWithChildren/UPDATE_WORKINGWITHCHILDREN',
  DELETE_WORKINGWITHCHILDREN: 'workingWithChildren/DELETE_WORKINGWITHCHILDREN',
  RESET: 'workingWithChildren/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWorkingWithChildren>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type WorkingWithChildrenState = Readonly<typeof initialState>;

// Reducer

export default (state: WorkingWithChildrenState = initialState, action): WorkingWithChildrenState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_WORKINGWITHCHILDREN):
    case REQUEST(ACTION_TYPES.FETCH_WORKINGWITHCHILDREN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WORKINGWITHCHILDREN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_WORKINGWITHCHILDREN):
    case REQUEST(ACTION_TYPES.UPDATE_WORKINGWITHCHILDREN):
    case REQUEST(ACTION_TYPES.DELETE_WORKINGWITHCHILDREN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_WORKINGWITHCHILDREN):
    case FAILURE(ACTION_TYPES.FETCH_WORKINGWITHCHILDREN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WORKINGWITHCHILDREN):
    case FAILURE(ACTION_TYPES.CREATE_WORKINGWITHCHILDREN):
    case FAILURE(ACTION_TYPES.UPDATE_WORKINGWITHCHILDREN):
    case FAILURE(ACTION_TYPES.DELETE_WORKINGWITHCHILDREN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_WORKINGWITHCHILDREN):
    case SUCCESS(ACTION_TYPES.FETCH_WORKINGWITHCHILDREN_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_WORKINGWITHCHILDREN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_WORKINGWITHCHILDREN):
    case SUCCESS(ACTION_TYPES.UPDATE_WORKINGWITHCHILDREN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_WORKINGWITHCHILDREN):
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

const apiUrl = 'api/working-with-children';
const apiSearchUrl = 'api/_search/working-with-children';

// Actions

export const getSearchEntities: ICrudSearchAction<IWorkingWithChildren> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_WORKINGWITHCHILDREN,
  payload: axios.get<IWorkingWithChildren>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IWorkingWithChildren> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_WORKINGWITHCHILDREN_LIST,
    payload: axios.get<IWorkingWithChildren>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IWorkingWithChildren> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WORKINGWITHCHILDREN,
    payload: axios.get<IWorkingWithChildren>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IWorkingWithChildren> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WORKINGWITHCHILDREN,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWorkingWithChildren> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WORKINGWITHCHILDREN,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWorkingWithChildren> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WORKINGWITHCHILDREN,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
