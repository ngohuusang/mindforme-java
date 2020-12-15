import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRuleData, defaultValue } from 'app/shared/model/rule-data.model';

export const ACTION_TYPES = {
  SEARCH_RULEDATA: 'ruleData/SEARCH_RULEDATA',
  FETCH_RULEDATA_LIST: 'ruleData/FETCH_RULEDATA_LIST',
  FETCH_RULEDATA: 'ruleData/FETCH_RULEDATA',
  CREATE_RULEDATA: 'ruleData/CREATE_RULEDATA',
  UPDATE_RULEDATA: 'ruleData/UPDATE_RULEDATA',
  DELETE_RULEDATA: 'ruleData/DELETE_RULEDATA',
  RESET: 'ruleData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRuleData>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type RuleDataState = Readonly<typeof initialState>;

// Reducer

export default (state: RuleDataState = initialState, action): RuleDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_RULEDATA):
    case REQUEST(ACTION_TYPES.FETCH_RULEDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RULEDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_RULEDATA):
    case REQUEST(ACTION_TYPES.UPDATE_RULEDATA):
    case REQUEST(ACTION_TYPES.DELETE_RULEDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_RULEDATA):
    case FAILURE(ACTION_TYPES.FETCH_RULEDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RULEDATA):
    case FAILURE(ACTION_TYPES.CREATE_RULEDATA):
    case FAILURE(ACTION_TYPES.UPDATE_RULEDATA):
    case FAILURE(ACTION_TYPES.DELETE_RULEDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_RULEDATA):
    case SUCCESS(ACTION_TYPES.FETCH_RULEDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_RULEDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_RULEDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_RULEDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_RULEDATA):
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

const apiUrl = 'api/rule-data';
const apiSearchUrl = 'api/_search/rule-data';

// Actions

export const getSearchEntities: ICrudSearchAction<IRuleData> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_RULEDATA,
  payload: axios.get<IRuleData>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IRuleData> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_RULEDATA_LIST,
    payload: axios.get<IRuleData>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IRuleData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RULEDATA,
    payload: axios.get<IRuleData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IRuleData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RULEDATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRuleData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RULEDATA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRuleData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RULEDATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
