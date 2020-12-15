import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IChildRelationData, defaultValue } from 'app/shared/model/child-relation-data.model';

export const ACTION_TYPES = {
  SEARCH_CHILDRELATIONDATA: 'childRelationData/SEARCH_CHILDRELATIONDATA',
  FETCH_CHILDRELATIONDATA_LIST: 'childRelationData/FETCH_CHILDRELATIONDATA_LIST',
  FETCH_CHILDRELATIONDATA: 'childRelationData/FETCH_CHILDRELATIONDATA',
  CREATE_CHILDRELATIONDATA: 'childRelationData/CREATE_CHILDRELATIONDATA',
  UPDATE_CHILDRELATIONDATA: 'childRelationData/UPDATE_CHILDRELATIONDATA',
  DELETE_CHILDRELATIONDATA: 'childRelationData/DELETE_CHILDRELATIONDATA',
  RESET: 'childRelationData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IChildRelationData>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ChildRelationDataState = Readonly<typeof initialState>;

// Reducer

export default (state: ChildRelationDataState = initialState, action): ChildRelationDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_CHILDRELATIONDATA):
    case REQUEST(ACTION_TYPES.FETCH_CHILDRELATIONDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CHILDRELATIONDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CHILDRELATIONDATA):
    case REQUEST(ACTION_TYPES.UPDATE_CHILDRELATIONDATA):
    case REQUEST(ACTION_TYPES.DELETE_CHILDRELATIONDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_CHILDRELATIONDATA):
    case FAILURE(ACTION_TYPES.FETCH_CHILDRELATIONDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CHILDRELATIONDATA):
    case FAILURE(ACTION_TYPES.CREATE_CHILDRELATIONDATA):
    case FAILURE(ACTION_TYPES.UPDATE_CHILDRELATIONDATA):
    case FAILURE(ACTION_TYPES.DELETE_CHILDRELATIONDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_CHILDRELATIONDATA):
    case SUCCESS(ACTION_TYPES.FETCH_CHILDRELATIONDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CHILDRELATIONDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CHILDRELATIONDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_CHILDRELATIONDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CHILDRELATIONDATA):
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

const apiUrl = 'api/child-relation-data';
const apiSearchUrl = 'api/_search/child-relation-data';

// Actions

export const getSearchEntities: ICrudSearchAction<IChildRelationData> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_CHILDRELATIONDATA,
  payload: axios.get<IChildRelationData>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IChildRelationData> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CHILDRELATIONDATA_LIST,
    payload: axios.get<IChildRelationData>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IChildRelationData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CHILDRELATIONDATA,
    payload: axios.get<IChildRelationData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IChildRelationData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CHILDRELATIONDATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IChildRelationData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CHILDRELATIONDATA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IChildRelationData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CHILDRELATIONDATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
