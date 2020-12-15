import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IStateData, defaultValue } from 'app/shared/model/state-data.model';

export const ACTION_TYPES = {
  SEARCH_STATEDATA: 'stateData/SEARCH_STATEDATA',
  FETCH_STATEDATA_LIST: 'stateData/FETCH_STATEDATA_LIST',
  FETCH_STATEDATA: 'stateData/FETCH_STATEDATA',
  CREATE_STATEDATA: 'stateData/CREATE_STATEDATA',
  UPDATE_STATEDATA: 'stateData/UPDATE_STATEDATA',
  DELETE_STATEDATA: 'stateData/DELETE_STATEDATA',
  RESET: 'stateData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IStateData>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type StateDataState = Readonly<typeof initialState>;

// Reducer

export default (state: StateDataState = initialState, action): StateDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_STATEDATA):
    case REQUEST(ACTION_TYPES.FETCH_STATEDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_STATEDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_STATEDATA):
    case REQUEST(ACTION_TYPES.UPDATE_STATEDATA):
    case REQUEST(ACTION_TYPES.DELETE_STATEDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_STATEDATA):
    case FAILURE(ACTION_TYPES.FETCH_STATEDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_STATEDATA):
    case FAILURE(ACTION_TYPES.CREATE_STATEDATA):
    case FAILURE(ACTION_TYPES.UPDATE_STATEDATA):
    case FAILURE(ACTION_TYPES.DELETE_STATEDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_STATEDATA):
    case SUCCESS(ACTION_TYPES.FETCH_STATEDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_STATEDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_STATEDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_STATEDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_STATEDATA):
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

const apiUrl = 'api/state-data';
const apiSearchUrl = 'api/_search/state-data';

// Actions

export const getSearchEntities: ICrudSearchAction<IStateData> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_STATEDATA,
  payload: axios.get<IStateData>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IStateData> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_STATEDATA_LIST,
    payload: axios.get<IStateData>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IStateData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_STATEDATA,
    payload: axios.get<IStateData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IStateData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_STATEDATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IStateData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_STATEDATA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IStateData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_STATEDATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
