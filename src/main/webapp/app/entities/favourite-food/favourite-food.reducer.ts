import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFavouriteFood, defaultValue } from 'app/shared/model/favourite-food.model';

export const ACTION_TYPES = {
  SEARCH_FAVOURITEFOODS: 'favouriteFood/SEARCH_FAVOURITEFOODS',
  FETCH_FAVOURITEFOOD_LIST: 'favouriteFood/FETCH_FAVOURITEFOOD_LIST',
  FETCH_FAVOURITEFOOD: 'favouriteFood/FETCH_FAVOURITEFOOD',
  CREATE_FAVOURITEFOOD: 'favouriteFood/CREATE_FAVOURITEFOOD',
  UPDATE_FAVOURITEFOOD: 'favouriteFood/UPDATE_FAVOURITEFOOD',
  DELETE_FAVOURITEFOOD: 'favouriteFood/DELETE_FAVOURITEFOOD',
  RESET: 'favouriteFood/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFavouriteFood>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type FavouriteFoodState = Readonly<typeof initialState>;

// Reducer

export default (state: FavouriteFoodState = initialState, action): FavouriteFoodState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_FAVOURITEFOODS):
    case REQUEST(ACTION_TYPES.FETCH_FAVOURITEFOOD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FAVOURITEFOOD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_FAVOURITEFOOD):
    case REQUEST(ACTION_TYPES.UPDATE_FAVOURITEFOOD):
    case REQUEST(ACTION_TYPES.DELETE_FAVOURITEFOOD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_FAVOURITEFOODS):
    case FAILURE(ACTION_TYPES.FETCH_FAVOURITEFOOD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FAVOURITEFOOD):
    case FAILURE(ACTION_TYPES.CREATE_FAVOURITEFOOD):
    case FAILURE(ACTION_TYPES.UPDATE_FAVOURITEFOOD):
    case FAILURE(ACTION_TYPES.DELETE_FAVOURITEFOOD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_FAVOURITEFOODS):
    case SUCCESS(ACTION_TYPES.FETCH_FAVOURITEFOOD_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_FAVOURITEFOOD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_FAVOURITEFOOD):
    case SUCCESS(ACTION_TYPES.UPDATE_FAVOURITEFOOD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_FAVOURITEFOOD):
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

const apiUrl = 'api/favourite-foods';
const apiSearchUrl = 'api/_search/favourite-foods';

// Actions

export const getSearchEntities: ICrudSearchAction<IFavouriteFood> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_FAVOURITEFOODS,
  payload: axios.get<IFavouriteFood>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IFavouriteFood> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_FAVOURITEFOOD_LIST,
    payload: axios.get<IFavouriteFood>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IFavouriteFood> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FAVOURITEFOOD,
    payload: axios.get<IFavouriteFood>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IFavouriteFood> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FAVOURITEFOOD,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFavouriteFood> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FAVOURITEFOOD,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFavouriteFood> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FAVOURITEFOOD,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
