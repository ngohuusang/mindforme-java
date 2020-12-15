import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAllergyData, defaultValue } from 'app/shared/model/allergy-data.model';

export const ACTION_TYPES = {
  SEARCH_ALLERGYDATA: 'allergyData/SEARCH_ALLERGYDATA',
  FETCH_ALLERGYDATA_LIST: 'allergyData/FETCH_ALLERGYDATA_LIST',
  FETCH_ALLERGYDATA: 'allergyData/FETCH_ALLERGYDATA',
  CREATE_ALLERGYDATA: 'allergyData/CREATE_ALLERGYDATA',
  UPDATE_ALLERGYDATA: 'allergyData/UPDATE_ALLERGYDATA',
  DELETE_ALLERGYDATA: 'allergyData/DELETE_ALLERGYDATA',
  RESET: 'allergyData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAllergyData>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type AllergyDataState = Readonly<typeof initialState>;

// Reducer

export default (state: AllergyDataState = initialState, action): AllergyDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_ALLERGYDATA):
    case REQUEST(ACTION_TYPES.FETCH_ALLERGYDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ALLERGYDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ALLERGYDATA):
    case REQUEST(ACTION_TYPES.UPDATE_ALLERGYDATA):
    case REQUEST(ACTION_TYPES.DELETE_ALLERGYDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_ALLERGYDATA):
    case FAILURE(ACTION_TYPES.FETCH_ALLERGYDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ALLERGYDATA):
    case FAILURE(ACTION_TYPES.CREATE_ALLERGYDATA):
    case FAILURE(ACTION_TYPES.UPDATE_ALLERGYDATA):
    case FAILURE(ACTION_TYPES.DELETE_ALLERGYDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_ALLERGYDATA):
    case SUCCESS(ACTION_TYPES.FETCH_ALLERGYDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ALLERGYDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ALLERGYDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_ALLERGYDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ALLERGYDATA):
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

const apiUrl = 'api/allergy-data';
const apiSearchUrl = 'api/_search/allergy-data';

// Actions

export const getSearchEntities: ICrudSearchAction<IAllergyData> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_ALLERGYDATA,
  payload: axios.get<IAllergyData>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IAllergyData> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ALLERGYDATA_LIST,
    payload: axios.get<IAllergyData>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IAllergyData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ALLERGYDATA,
    payload: axios.get<IAllergyData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAllergyData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ALLERGYDATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAllergyData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ALLERGYDATA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAllergyData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ALLERGYDATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
