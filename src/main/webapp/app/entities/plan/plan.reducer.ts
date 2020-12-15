import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPlan, defaultValue } from 'app/shared/model/plan.model';

export const ACTION_TYPES = {
  SEARCH_PLANS: 'plan/SEARCH_PLANS',
  FETCH_PLAN_LIST: 'plan/FETCH_PLAN_LIST',
  FETCH_PLAN: 'plan/FETCH_PLAN',
  CREATE_PLAN: 'plan/CREATE_PLAN',
  UPDATE_PLAN: 'plan/UPDATE_PLAN',
  DELETE_PLAN: 'plan/DELETE_PLAN',
  RESET: 'plan/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPlan>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PlanState = Readonly<typeof initialState>;

// Reducer

export default (state: PlanState = initialState, action): PlanState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PLANS):
    case REQUEST(ACTION_TYPES.FETCH_PLAN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PLAN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PLAN):
    case REQUEST(ACTION_TYPES.UPDATE_PLAN):
    case REQUEST(ACTION_TYPES.DELETE_PLAN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_PLANS):
    case FAILURE(ACTION_TYPES.FETCH_PLAN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PLAN):
    case FAILURE(ACTION_TYPES.CREATE_PLAN):
    case FAILURE(ACTION_TYPES.UPDATE_PLAN):
    case FAILURE(ACTION_TYPES.DELETE_PLAN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PLANS):
    case SUCCESS(ACTION_TYPES.FETCH_PLAN_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PLAN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PLAN):
    case SUCCESS(ACTION_TYPES.UPDATE_PLAN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PLAN):
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

const apiUrl = 'api/plans';
const apiSearchUrl = 'api/_search/plans';

// Actions

export const getSearchEntities: ICrudSearchAction<IPlan> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PLANS,
  payload: axios.get<IPlan>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IPlan> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PLAN_LIST,
    payload: axios.get<IPlan>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPlan> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PLAN,
    payload: axios.get<IPlan>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPlan> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PLAN,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPlan> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PLAN,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPlan> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PLAN,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
