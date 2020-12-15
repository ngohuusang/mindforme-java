import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISupportedRelationData, defaultValue } from 'app/shared/model/supported-relation-data.model';

export const ACTION_TYPES = {
  SEARCH_SUPPORTEDRELATIONDATA: 'supportedRelationData/SEARCH_SUPPORTEDRELATIONDATA',
  FETCH_SUPPORTEDRELATIONDATA_LIST: 'supportedRelationData/FETCH_SUPPORTEDRELATIONDATA_LIST',
  FETCH_SUPPORTEDRELATIONDATA: 'supportedRelationData/FETCH_SUPPORTEDRELATIONDATA',
  CREATE_SUPPORTEDRELATIONDATA: 'supportedRelationData/CREATE_SUPPORTEDRELATIONDATA',
  UPDATE_SUPPORTEDRELATIONDATA: 'supportedRelationData/UPDATE_SUPPORTEDRELATIONDATA',
  DELETE_SUPPORTEDRELATIONDATA: 'supportedRelationData/DELETE_SUPPORTEDRELATIONDATA',
  RESET: 'supportedRelationData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISupportedRelationData>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SupportedRelationDataState = Readonly<typeof initialState>;

// Reducer

export default (state: SupportedRelationDataState = initialState, action): SupportedRelationDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SUPPORTEDRELATIONDATA):
    case REQUEST(ACTION_TYPES.FETCH_SUPPORTEDRELATIONDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SUPPORTEDRELATIONDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SUPPORTEDRELATIONDATA):
    case REQUEST(ACTION_TYPES.UPDATE_SUPPORTEDRELATIONDATA):
    case REQUEST(ACTION_TYPES.DELETE_SUPPORTEDRELATIONDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_SUPPORTEDRELATIONDATA):
    case FAILURE(ACTION_TYPES.FETCH_SUPPORTEDRELATIONDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SUPPORTEDRELATIONDATA):
    case FAILURE(ACTION_TYPES.CREATE_SUPPORTEDRELATIONDATA):
    case FAILURE(ACTION_TYPES.UPDATE_SUPPORTEDRELATIONDATA):
    case FAILURE(ACTION_TYPES.DELETE_SUPPORTEDRELATIONDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_SUPPORTEDRELATIONDATA):
    case SUCCESS(ACTION_TYPES.FETCH_SUPPORTEDRELATIONDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUPPORTEDRELATIONDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SUPPORTEDRELATIONDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_SUPPORTEDRELATIONDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SUPPORTEDRELATIONDATA):
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

const apiUrl = 'api/supported-relation-data';
const apiSearchUrl = 'api/_search/supported-relation-data';

// Actions

export const getSearchEntities: ICrudSearchAction<ISupportedRelationData> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SUPPORTEDRELATIONDATA,
  payload: axios.get<ISupportedRelationData>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<ISupportedRelationData> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SUPPORTEDRELATIONDATA_LIST,
    payload: axios.get<ISupportedRelationData>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISupportedRelationData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SUPPORTEDRELATIONDATA,
    payload: axios.get<ISupportedRelationData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISupportedRelationData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SUPPORTEDRELATIONDATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISupportedRelationData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SUPPORTEDRELATIONDATA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISupportedRelationData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SUPPORTEDRELATIONDATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
