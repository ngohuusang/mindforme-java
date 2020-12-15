import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPetBreed, defaultValue } from 'app/shared/model/pet-breed.model';

export const ACTION_TYPES = {
  SEARCH_PETBREEDS: 'petBreed/SEARCH_PETBREEDS',
  FETCH_PETBREED_LIST: 'petBreed/FETCH_PETBREED_LIST',
  FETCH_PETBREED: 'petBreed/FETCH_PETBREED',
  CREATE_PETBREED: 'petBreed/CREATE_PETBREED',
  UPDATE_PETBREED: 'petBreed/UPDATE_PETBREED',
  DELETE_PETBREED: 'petBreed/DELETE_PETBREED',
  RESET: 'petBreed/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPetBreed>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PetBreedState = Readonly<typeof initialState>;

// Reducer

export default (state: PetBreedState = initialState, action): PetBreedState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PETBREEDS):
    case REQUEST(ACTION_TYPES.FETCH_PETBREED_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PETBREED):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PETBREED):
    case REQUEST(ACTION_TYPES.UPDATE_PETBREED):
    case REQUEST(ACTION_TYPES.DELETE_PETBREED):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_PETBREEDS):
    case FAILURE(ACTION_TYPES.FETCH_PETBREED_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PETBREED):
    case FAILURE(ACTION_TYPES.CREATE_PETBREED):
    case FAILURE(ACTION_TYPES.UPDATE_PETBREED):
    case FAILURE(ACTION_TYPES.DELETE_PETBREED):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PETBREEDS):
    case SUCCESS(ACTION_TYPES.FETCH_PETBREED_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PETBREED):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PETBREED):
    case SUCCESS(ACTION_TYPES.UPDATE_PETBREED):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PETBREED):
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

const apiUrl = 'api/pet-breeds';
const apiSearchUrl = 'api/_search/pet-breeds';

// Actions

export const getSearchEntities: ICrudSearchAction<IPetBreed> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PETBREEDS,
  payload: axios.get<IPetBreed>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IPetBreed> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PETBREED_LIST,
    payload: axios.get<IPetBreed>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPetBreed> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PETBREED,
    payload: axios.get<IPetBreed>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPetBreed> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PETBREED,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPetBreed> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PETBREED,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPetBreed> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PETBREED,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
