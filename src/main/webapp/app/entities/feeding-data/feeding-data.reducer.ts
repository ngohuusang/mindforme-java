import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFeedingData, defaultValue } from 'app/shared/model/feeding-data.model';

export const ACTION_TYPES = {
  SEARCH_FEEDINGDATA: 'feedingData/SEARCH_FEEDINGDATA',
  FETCH_FEEDINGDATA_LIST: 'feedingData/FETCH_FEEDINGDATA_LIST',
  FETCH_FEEDINGDATA: 'feedingData/FETCH_FEEDINGDATA',
  CREATE_FEEDINGDATA: 'feedingData/CREATE_FEEDINGDATA',
  UPDATE_FEEDINGDATA: 'feedingData/UPDATE_FEEDINGDATA',
  DELETE_FEEDINGDATA: 'feedingData/DELETE_FEEDINGDATA',
  RESET: 'feedingData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFeedingData>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type FeedingDataState = Readonly<typeof initialState>;

// Reducer

export default (state: FeedingDataState = initialState, action): FeedingDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_FEEDINGDATA):
    case REQUEST(ACTION_TYPES.FETCH_FEEDINGDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FEEDINGDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_FEEDINGDATA):
    case REQUEST(ACTION_TYPES.UPDATE_FEEDINGDATA):
    case REQUEST(ACTION_TYPES.DELETE_FEEDINGDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_FEEDINGDATA):
    case FAILURE(ACTION_TYPES.FETCH_FEEDINGDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FEEDINGDATA):
    case FAILURE(ACTION_TYPES.CREATE_FEEDINGDATA):
    case FAILURE(ACTION_TYPES.UPDATE_FEEDINGDATA):
    case FAILURE(ACTION_TYPES.DELETE_FEEDINGDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_FEEDINGDATA):
    case SUCCESS(ACTION_TYPES.FETCH_FEEDINGDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_FEEDINGDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_FEEDINGDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_FEEDINGDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_FEEDINGDATA):
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

const apiUrl = 'api/feeding-data';
const apiSearchUrl = 'api/_search/feeding-data';

// Actions

export const getSearchEntities: ICrudSearchAction<IFeedingData> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_FEEDINGDATA,
  payload: axios.get<IFeedingData>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IFeedingData> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_FEEDINGDATA_LIST,
    payload: axios.get<IFeedingData>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IFeedingData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FEEDINGDATA,
    payload: axios.get<IFeedingData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IFeedingData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FEEDINGDATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFeedingData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FEEDINGDATA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFeedingData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FEEDINGDATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
