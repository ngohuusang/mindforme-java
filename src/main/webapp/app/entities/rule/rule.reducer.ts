import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRule, defaultValue } from 'app/shared/model/rule.model';

export const ACTION_TYPES = {
  SEARCH_RULES: 'rule/SEARCH_RULES',
  FETCH_RULE_LIST: 'rule/FETCH_RULE_LIST',
  FETCH_RULE: 'rule/FETCH_RULE',
  CREATE_RULE: 'rule/CREATE_RULE',
  UPDATE_RULE: 'rule/UPDATE_RULE',
  DELETE_RULE: 'rule/DELETE_RULE',
  RESET: 'rule/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRule>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type RuleState = Readonly<typeof initialState>;

// Reducer

export default (state: RuleState = initialState, action): RuleState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_RULES):
    case REQUEST(ACTION_TYPES.FETCH_RULE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RULE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_RULE):
    case REQUEST(ACTION_TYPES.UPDATE_RULE):
    case REQUEST(ACTION_TYPES.DELETE_RULE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_RULES):
    case FAILURE(ACTION_TYPES.FETCH_RULE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RULE):
    case FAILURE(ACTION_TYPES.CREATE_RULE):
    case FAILURE(ACTION_TYPES.UPDATE_RULE):
    case FAILURE(ACTION_TYPES.DELETE_RULE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_RULES):
    case SUCCESS(ACTION_TYPES.FETCH_RULE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_RULE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_RULE):
    case SUCCESS(ACTION_TYPES.UPDATE_RULE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_RULE):
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

const apiUrl = 'api/rules';
const apiSearchUrl = 'api/_search/rules';

// Actions

export const getSearchEntities: ICrudSearchAction<IRule> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_RULES,
  payload: axios.get<IRule>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IRule> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_RULE_LIST,
    payload: axios.get<IRule>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IRule> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RULE,
    payload: axios.get<IRule>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IRule> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RULE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRule> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RULE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRule> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RULE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
