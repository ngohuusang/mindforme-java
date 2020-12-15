import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISupportedRelation, defaultValue } from 'app/shared/model/supported-relation.model';

export const ACTION_TYPES = {
  SEARCH_SUPPORTEDRELATIONS: 'supportedRelation/SEARCH_SUPPORTEDRELATIONS',
  FETCH_SUPPORTEDRELATION_LIST: 'supportedRelation/FETCH_SUPPORTEDRELATION_LIST',
  FETCH_SUPPORTEDRELATION: 'supportedRelation/FETCH_SUPPORTEDRELATION',
  CREATE_SUPPORTEDRELATION: 'supportedRelation/CREATE_SUPPORTEDRELATION',
  UPDATE_SUPPORTEDRELATION: 'supportedRelation/UPDATE_SUPPORTEDRELATION',
  DELETE_SUPPORTEDRELATION: 'supportedRelation/DELETE_SUPPORTEDRELATION',
  RESET: 'supportedRelation/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISupportedRelation>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SupportedRelationState = Readonly<typeof initialState>;

// Reducer

export default (state: SupportedRelationState = initialState, action): SupportedRelationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SUPPORTEDRELATIONS):
    case REQUEST(ACTION_TYPES.FETCH_SUPPORTEDRELATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SUPPORTEDRELATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SUPPORTEDRELATION):
    case REQUEST(ACTION_TYPES.UPDATE_SUPPORTEDRELATION):
    case REQUEST(ACTION_TYPES.DELETE_SUPPORTEDRELATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_SUPPORTEDRELATIONS):
    case FAILURE(ACTION_TYPES.FETCH_SUPPORTEDRELATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SUPPORTEDRELATION):
    case FAILURE(ACTION_TYPES.CREATE_SUPPORTEDRELATION):
    case FAILURE(ACTION_TYPES.UPDATE_SUPPORTEDRELATION):
    case FAILURE(ACTION_TYPES.DELETE_SUPPORTEDRELATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_SUPPORTEDRELATIONS):
    case SUCCESS(ACTION_TYPES.FETCH_SUPPORTEDRELATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUPPORTEDRELATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SUPPORTEDRELATION):
    case SUCCESS(ACTION_TYPES.UPDATE_SUPPORTEDRELATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SUPPORTEDRELATION):
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

const apiUrl = 'api/supported-relations';
const apiSearchUrl = 'api/_search/supported-relations';

// Actions

export const getSearchEntities: ICrudSearchAction<ISupportedRelation> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SUPPORTEDRELATIONS,
  payload: axios.get<ISupportedRelation>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<ISupportedRelation> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SUPPORTEDRELATION_LIST,
    payload: axios.get<ISupportedRelation>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISupportedRelation> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SUPPORTEDRELATION,
    payload: axios.get<ISupportedRelation>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISupportedRelation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SUPPORTEDRELATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISupportedRelation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SUPPORTEDRELATION,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISupportedRelation> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SUPPORTEDRELATION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
