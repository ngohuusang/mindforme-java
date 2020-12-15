import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPlanData, defaultValue } from 'app/shared/model/plan-data.model';

export const ACTION_TYPES = {
  SEARCH_PLANDATA: 'planData/SEARCH_PLANDATA',
  FETCH_PLANDATA_LIST: 'planData/FETCH_PLANDATA_LIST',
  FETCH_PLANDATA: 'planData/FETCH_PLANDATA',
  CREATE_PLANDATA: 'planData/CREATE_PLANDATA',
  UPDATE_PLANDATA: 'planData/UPDATE_PLANDATA',
  DELETE_PLANDATA: 'planData/DELETE_PLANDATA',
  RESET: 'planData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPlanData>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PlanDataState = Readonly<typeof initialState>;

// Reducer

export default (state: PlanDataState = initialState, action): PlanDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PLANDATA):
    case REQUEST(ACTION_TYPES.FETCH_PLANDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PLANDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PLANDATA):
    case REQUEST(ACTION_TYPES.UPDATE_PLANDATA):
    case REQUEST(ACTION_TYPES.DELETE_PLANDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_PLANDATA):
    case FAILURE(ACTION_TYPES.FETCH_PLANDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PLANDATA):
    case FAILURE(ACTION_TYPES.CREATE_PLANDATA):
    case FAILURE(ACTION_TYPES.UPDATE_PLANDATA):
    case FAILURE(ACTION_TYPES.DELETE_PLANDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PLANDATA):
    case SUCCESS(ACTION_TYPES.FETCH_PLANDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PLANDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PLANDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_PLANDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PLANDATA):
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

const apiUrl = 'api/plan-data';
const apiSearchUrl = 'api/_search/plan-data';

// Actions

export const getSearchEntities: ICrudSearchAction<IPlanData> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PLANDATA,
  payload: axios.get<IPlanData>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IPlanData> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PLANDATA_LIST,
    payload: axios.get<IPlanData>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPlanData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PLANDATA,
    payload: axios.get<IPlanData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPlanData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PLANDATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPlanData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PLANDATA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPlanData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PLANDATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
