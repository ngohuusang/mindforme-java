import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPetTypeData, defaultValue } from 'app/shared/model/pet-type-data.model';

export const ACTION_TYPES = {
  SEARCH_PETTYPEDATA: 'petTypeData/SEARCH_PETTYPEDATA',
  FETCH_PETTYPEDATA_LIST: 'petTypeData/FETCH_PETTYPEDATA_LIST',
  FETCH_PETTYPEDATA: 'petTypeData/FETCH_PETTYPEDATA',
  CREATE_PETTYPEDATA: 'petTypeData/CREATE_PETTYPEDATA',
  UPDATE_PETTYPEDATA: 'petTypeData/UPDATE_PETTYPEDATA',
  DELETE_PETTYPEDATA: 'petTypeData/DELETE_PETTYPEDATA',
  RESET: 'petTypeData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPetTypeData>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PetTypeDataState = Readonly<typeof initialState>;

// Reducer

export default (state: PetTypeDataState = initialState, action): PetTypeDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PETTYPEDATA):
    case REQUEST(ACTION_TYPES.FETCH_PETTYPEDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PETTYPEDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PETTYPEDATA):
    case REQUEST(ACTION_TYPES.UPDATE_PETTYPEDATA):
    case REQUEST(ACTION_TYPES.DELETE_PETTYPEDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_PETTYPEDATA):
    case FAILURE(ACTION_TYPES.FETCH_PETTYPEDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PETTYPEDATA):
    case FAILURE(ACTION_TYPES.CREATE_PETTYPEDATA):
    case FAILURE(ACTION_TYPES.UPDATE_PETTYPEDATA):
    case FAILURE(ACTION_TYPES.DELETE_PETTYPEDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PETTYPEDATA):
    case SUCCESS(ACTION_TYPES.FETCH_PETTYPEDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PETTYPEDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PETTYPEDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_PETTYPEDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PETTYPEDATA):
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

const apiUrl = 'api/pet-type-data';
const apiSearchUrl = 'api/_search/pet-type-data';

// Actions

export const getSearchEntities: ICrudSearchAction<IPetTypeData> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PETTYPEDATA,
  payload: axios.get<IPetTypeData>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IPetTypeData> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PETTYPEDATA_LIST,
    payload: axios.get<IPetTypeData>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPetTypeData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PETTYPEDATA,
    payload: axios.get<IPetTypeData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPetTypeData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PETTYPEDATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPetTypeData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PETTYPEDATA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPetTypeData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PETTYPEDATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
