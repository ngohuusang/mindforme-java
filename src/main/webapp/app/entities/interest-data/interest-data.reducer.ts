import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IInterestData, defaultValue } from 'app/shared/model/interest-data.model';

export const ACTION_TYPES = {
  SEARCH_INTERESTDATA: 'interestData/SEARCH_INTERESTDATA',
  FETCH_INTERESTDATA_LIST: 'interestData/FETCH_INTERESTDATA_LIST',
  FETCH_INTERESTDATA: 'interestData/FETCH_INTERESTDATA',
  CREATE_INTERESTDATA: 'interestData/CREATE_INTERESTDATA',
  UPDATE_INTERESTDATA: 'interestData/UPDATE_INTERESTDATA',
  DELETE_INTERESTDATA: 'interestData/DELETE_INTERESTDATA',
  RESET: 'interestData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IInterestData>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type InterestDataState = Readonly<typeof initialState>;

// Reducer

export default (state: InterestDataState = initialState, action): InterestDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_INTERESTDATA):
    case REQUEST(ACTION_TYPES.FETCH_INTERESTDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_INTERESTDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_INTERESTDATA):
    case REQUEST(ACTION_TYPES.UPDATE_INTERESTDATA):
    case REQUEST(ACTION_TYPES.DELETE_INTERESTDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_INTERESTDATA):
    case FAILURE(ACTION_TYPES.FETCH_INTERESTDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_INTERESTDATA):
    case FAILURE(ACTION_TYPES.CREATE_INTERESTDATA):
    case FAILURE(ACTION_TYPES.UPDATE_INTERESTDATA):
    case FAILURE(ACTION_TYPES.DELETE_INTERESTDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_INTERESTDATA):
    case SUCCESS(ACTION_TYPES.FETCH_INTERESTDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_INTERESTDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_INTERESTDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_INTERESTDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_INTERESTDATA):
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

const apiUrl = 'api/interest-data';
const apiSearchUrl = 'api/_search/interest-data';

// Actions

export const getSearchEntities: ICrudSearchAction<IInterestData> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_INTERESTDATA,
  payload: axios.get<IInterestData>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IInterestData> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_INTERESTDATA_LIST,
    payload: axios.get<IInterestData>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IInterestData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_INTERESTDATA,
    payload: axios.get<IInterestData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IInterestData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_INTERESTDATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IInterestData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_INTERESTDATA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IInterestData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_INTERESTDATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
