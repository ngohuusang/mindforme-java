import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFeeding, defaultValue } from 'app/shared/model/feeding.model';

export const ACTION_TYPES = {
  SEARCH_FEEDINGS: 'feeding/SEARCH_FEEDINGS',
  FETCH_FEEDING_LIST: 'feeding/FETCH_FEEDING_LIST',
  FETCH_FEEDING: 'feeding/FETCH_FEEDING',
  CREATE_FEEDING: 'feeding/CREATE_FEEDING',
  UPDATE_FEEDING: 'feeding/UPDATE_FEEDING',
  DELETE_FEEDING: 'feeding/DELETE_FEEDING',
  RESET: 'feeding/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFeeding>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type FeedingState = Readonly<typeof initialState>;

// Reducer

export default (state: FeedingState = initialState, action): FeedingState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_FEEDINGS):
    case REQUEST(ACTION_TYPES.FETCH_FEEDING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FEEDING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_FEEDING):
    case REQUEST(ACTION_TYPES.UPDATE_FEEDING):
    case REQUEST(ACTION_TYPES.DELETE_FEEDING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_FEEDINGS):
    case FAILURE(ACTION_TYPES.FETCH_FEEDING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FEEDING):
    case FAILURE(ACTION_TYPES.CREATE_FEEDING):
    case FAILURE(ACTION_TYPES.UPDATE_FEEDING):
    case FAILURE(ACTION_TYPES.DELETE_FEEDING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_FEEDINGS):
    case SUCCESS(ACTION_TYPES.FETCH_FEEDING_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_FEEDING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_FEEDING):
    case SUCCESS(ACTION_TYPES.UPDATE_FEEDING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_FEEDING):
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

const apiUrl = 'api/feedings';
const apiSearchUrl = 'api/_search/feedings';

// Actions

export const getSearchEntities: ICrudSearchAction<IFeeding> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_FEEDINGS,
  payload: axios.get<IFeeding>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IFeeding> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_FEEDING_LIST,
    payload: axios.get<IFeeding>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IFeeding> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FEEDING,
    payload: axios.get<IFeeding>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IFeeding> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FEEDING,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFeeding> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FEEDING,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFeeding> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FEEDING,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
