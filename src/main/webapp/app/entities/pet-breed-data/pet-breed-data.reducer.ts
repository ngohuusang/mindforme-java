import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPetBreedData, defaultValue } from 'app/shared/model/pet-breed-data.model';

export const ACTION_TYPES = {
  SEARCH_PETBREEDDATA: 'petBreedData/SEARCH_PETBREEDDATA',
  FETCH_PETBREEDDATA_LIST: 'petBreedData/FETCH_PETBREEDDATA_LIST',
  FETCH_PETBREEDDATA: 'petBreedData/FETCH_PETBREEDDATA',
  CREATE_PETBREEDDATA: 'petBreedData/CREATE_PETBREEDDATA',
  UPDATE_PETBREEDDATA: 'petBreedData/UPDATE_PETBREEDDATA',
  DELETE_PETBREEDDATA: 'petBreedData/DELETE_PETBREEDDATA',
  RESET: 'petBreedData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPetBreedData>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PetBreedDataState = Readonly<typeof initialState>;

// Reducer

export default (state: PetBreedDataState = initialState, action): PetBreedDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PETBREEDDATA):
    case REQUEST(ACTION_TYPES.FETCH_PETBREEDDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PETBREEDDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PETBREEDDATA):
    case REQUEST(ACTION_TYPES.UPDATE_PETBREEDDATA):
    case REQUEST(ACTION_TYPES.DELETE_PETBREEDDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_PETBREEDDATA):
    case FAILURE(ACTION_TYPES.FETCH_PETBREEDDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PETBREEDDATA):
    case FAILURE(ACTION_TYPES.CREATE_PETBREEDDATA):
    case FAILURE(ACTION_TYPES.UPDATE_PETBREEDDATA):
    case FAILURE(ACTION_TYPES.DELETE_PETBREEDDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PETBREEDDATA):
    case SUCCESS(ACTION_TYPES.FETCH_PETBREEDDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PETBREEDDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PETBREEDDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_PETBREEDDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PETBREEDDATA):
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

const apiUrl = 'api/pet-breed-data';
const apiSearchUrl = 'api/_search/pet-breed-data';

// Actions

export const getSearchEntities: ICrudSearchAction<IPetBreedData> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PETBREEDDATA,
  payload: axios.get<IPetBreedData>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IPetBreedData> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PETBREEDDATA_LIST,
    payload: axios.get<IPetBreedData>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPetBreedData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PETBREEDDATA,
    payload: axios.get<IPetBreedData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPetBreedData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PETBREEDDATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPetBreedData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PETBREEDDATA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPetBreedData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PETBREEDDATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
