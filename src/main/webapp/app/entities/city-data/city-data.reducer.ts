import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICityData, defaultValue } from 'app/shared/model/city-data.model';

export const ACTION_TYPES = {
  SEARCH_CITYDATA: 'cityData/SEARCH_CITYDATA',
  FETCH_CITYDATA_LIST: 'cityData/FETCH_CITYDATA_LIST',
  FETCH_CITYDATA: 'cityData/FETCH_CITYDATA',
  CREATE_CITYDATA: 'cityData/CREATE_CITYDATA',
  UPDATE_CITYDATA: 'cityData/UPDATE_CITYDATA',
  DELETE_CITYDATA: 'cityData/DELETE_CITYDATA',
  RESET: 'cityData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICityData>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CityDataState = Readonly<typeof initialState>;

// Reducer

export default (state: CityDataState = initialState, action): CityDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_CITYDATA):
    case REQUEST(ACTION_TYPES.FETCH_CITYDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CITYDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CITYDATA):
    case REQUEST(ACTION_TYPES.UPDATE_CITYDATA):
    case REQUEST(ACTION_TYPES.DELETE_CITYDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_CITYDATA):
    case FAILURE(ACTION_TYPES.FETCH_CITYDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CITYDATA):
    case FAILURE(ACTION_TYPES.CREATE_CITYDATA):
    case FAILURE(ACTION_TYPES.UPDATE_CITYDATA):
    case FAILURE(ACTION_TYPES.DELETE_CITYDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_CITYDATA):
    case SUCCESS(ACTION_TYPES.FETCH_CITYDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CITYDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CITYDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_CITYDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CITYDATA):
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

const apiUrl = 'api/city-data';
const apiSearchUrl = 'api/_search/city-data';

// Actions

export const getSearchEntities: ICrudSearchAction<ICityData> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_CITYDATA,
  payload: axios.get<ICityData>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<ICityData> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CITYDATA_LIST,
    payload: axios.get<ICityData>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICityData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CITYDATA,
    payload: axios.get<ICityData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICityData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CITYDATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICityData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CITYDATA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICityData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CITYDATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
