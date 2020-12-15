import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICountryData, defaultValue } from 'app/shared/model/country-data.model';

export const ACTION_TYPES = {
  SEARCH_COUNTRYDATA: 'countryData/SEARCH_COUNTRYDATA',
  FETCH_COUNTRYDATA_LIST: 'countryData/FETCH_COUNTRYDATA_LIST',
  FETCH_COUNTRYDATA: 'countryData/FETCH_COUNTRYDATA',
  CREATE_COUNTRYDATA: 'countryData/CREATE_COUNTRYDATA',
  UPDATE_COUNTRYDATA: 'countryData/UPDATE_COUNTRYDATA',
  DELETE_COUNTRYDATA: 'countryData/DELETE_COUNTRYDATA',
  RESET: 'countryData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICountryData>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CountryDataState = Readonly<typeof initialState>;

// Reducer

export default (state: CountryDataState = initialState, action): CountryDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_COUNTRYDATA):
    case REQUEST(ACTION_TYPES.FETCH_COUNTRYDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COUNTRYDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COUNTRYDATA):
    case REQUEST(ACTION_TYPES.UPDATE_COUNTRYDATA):
    case REQUEST(ACTION_TYPES.DELETE_COUNTRYDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_COUNTRYDATA):
    case FAILURE(ACTION_TYPES.FETCH_COUNTRYDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COUNTRYDATA):
    case FAILURE(ACTION_TYPES.CREATE_COUNTRYDATA):
    case FAILURE(ACTION_TYPES.UPDATE_COUNTRYDATA):
    case FAILURE(ACTION_TYPES.DELETE_COUNTRYDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_COUNTRYDATA):
    case SUCCESS(ACTION_TYPES.FETCH_COUNTRYDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_COUNTRYDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COUNTRYDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_COUNTRYDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COUNTRYDATA):
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

const apiUrl = 'api/country-data';
const apiSearchUrl = 'api/_search/country-data';

// Actions

export const getSearchEntities: ICrudSearchAction<ICountryData> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_COUNTRYDATA,
  payload: axios.get<ICountryData>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<ICountryData> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_COUNTRYDATA_LIST,
    payload: axios.get<ICountryData>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICountryData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COUNTRYDATA,
    payload: axios.get<ICountryData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICountryData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COUNTRYDATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICountryData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COUNTRYDATA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICountryData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COUNTRYDATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
