import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFavouriteFoodData, defaultValue } from 'app/shared/model/favourite-food-data.model';

export const ACTION_TYPES = {
  SEARCH_FAVOURITEFOODDATA: 'favouriteFoodData/SEARCH_FAVOURITEFOODDATA',
  FETCH_FAVOURITEFOODDATA_LIST: 'favouriteFoodData/FETCH_FAVOURITEFOODDATA_LIST',
  FETCH_FAVOURITEFOODDATA: 'favouriteFoodData/FETCH_FAVOURITEFOODDATA',
  CREATE_FAVOURITEFOODDATA: 'favouriteFoodData/CREATE_FAVOURITEFOODDATA',
  UPDATE_FAVOURITEFOODDATA: 'favouriteFoodData/UPDATE_FAVOURITEFOODDATA',
  DELETE_FAVOURITEFOODDATA: 'favouriteFoodData/DELETE_FAVOURITEFOODDATA',
  RESET: 'favouriteFoodData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFavouriteFoodData>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type FavouriteFoodDataState = Readonly<typeof initialState>;

// Reducer

export default (state: FavouriteFoodDataState = initialState, action): FavouriteFoodDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_FAVOURITEFOODDATA):
    case REQUEST(ACTION_TYPES.FETCH_FAVOURITEFOODDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FAVOURITEFOODDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_FAVOURITEFOODDATA):
    case REQUEST(ACTION_TYPES.UPDATE_FAVOURITEFOODDATA):
    case REQUEST(ACTION_TYPES.DELETE_FAVOURITEFOODDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_FAVOURITEFOODDATA):
    case FAILURE(ACTION_TYPES.FETCH_FAVOURITEFOODDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FAVOURITEFOODDATA):
    case FAILURE(ACTION_TYPES.CREATE_FAVOURITEFOODDATA):
    case FAILURE(ACTION_TYPES.UPDATE_FAVOURITEFOODDATA):
    case FAILURE(ACTION_TYPES.DELETE_FAVOURITEFOODDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_FAVOURITEFOODDATA):
    case SUCCESS(ACTION_TYPES.FETCH_FAVOURITEFOODDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_FAVOURITEFOODDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_FAVOURITEFOODDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_FAVOURITEFOODDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_FAVOURITEFOODDATA):
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

const apiUrl = 'api/favourite-food-data';
const apiSearchUrl = 'api/_search/favourite-food-data';

// Actions

export const getSearchEntities: ICrudSearchAction<IFavouriteFoodData> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_FAVOURITEFOODDATA,
  payload: axios.get<IFavouriteFoodData>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IFavouriteFoodData> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_FAVOURITEFOODDATA_LIST,
    payload: axios.get<IFavouriteFoodData>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IFavouriteFoodData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FAVOURITEFOODDATA,
    payload: axios.get<IFavouriteFoodData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IFavouriteFoodData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FAVOURITEFOODDATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFavouriteFoodData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FAVOURITEFOODDATA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFavouriteFoodData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FAVOURITEFOODDATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
