import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IChildRelation, defaultValue } from 'app/shared/model/child-relation.model';

export const ACTION_TYPES = {
  SEARCH_CHILDRELATIONS: 'childRelation/SEARCH_CHILDRELATIONS',
  FETCH_CHILDRELATION_LIST: 'childRelation/FETCH_CHILDRELATION_LIST',
  FETCH_CHILDRELATION: 'childRelation/FETCH_CHILDRELATION',
  CREATE_CHILDRELATION: 'childRelation/CREATE_CHILDRELATION',
  UPDATE_CHILDRELATION: 'childRelation/UPDATE_CHILDRELATION',
  DELETE_CHILDRELATION: 'childRelation/DELETE_CHILDRELATION',
  RESET: 'childRelation/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IChildRelation>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ChildRelationState = Readonly<typeof initialState>;

// Reducer

export default (state: ChildRelationState = initialState, action): ChildRelationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_CHILDRELATIONS):
    case REQUEST(ACTION_TYPES.FETCH_CHILDRELATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CHILDRELATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CHILDRELATION):
    case REQUEST(ACTION_TYPES.UPDATE_CHILDRELATION):
    case REQUEST(ACTION_TYPES.DELETE_CHILDRELATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_CHILDRELATIONS):
    case FAILURE(ACTION_TYPES.FETCH_CHILDRELATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CHILDRELATION):
    case FAILURE(ACTION_TYPES.CREATE_CHILDRELATION):
    case FAILURE(ACTION_TYPES.UPDATE_CHILDRELATION):
    case FAILURE(ACTION_TYPES.DELETE_CHILDRELATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_CHILDRELATIONS):
    case SUCCESS(ACTION_TYPES.FETCH_CHILDRELATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CHILDRELATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CHILDRELATION):
    case SUCCESS(ACTION_TYPES.UPDATE_CHILDRELATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CHILDRELATION):
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

const apiUrl = 'api/child-relations';
const apiSearchUrl = 'api/_search/child-relations';

// Actions

export const getSearchEntities: ICrudSearchAction<IChildRelation> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_CHILDRELATIONS,
  payload: axios.get<IChildRelation>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IChildRelation> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CHILDRELATION_LIST,
    payload: axios.get<IChildRelation>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IChildRelation> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CHILDRELATION,
    payload: axios.get<IChildRelation>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IChildRelation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CHILDRELATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IChildRelation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CHILDRELATION,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IChildRelation> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CHILDRELATION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
