import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPetHelpRequest, defaultValue } from 'app/shared/model/pet-help-request.model';

export const ACTION_TYPES = {
  SEARCH_PETHELPREQUESTS: 'petHelpRequest/SEARCH_PETHELPREQUESTS',
  FETCH_PETHELPREQUEST_LIST: 'petHelpRequest/FETCH_PETHELPREQUEST_LIST',
  FETCH_PETHELPREQUEST: 'petHelpRequest/FETCH_PETHELPREQUEST',
  CREATE_PETHELPREQUEST: 'petHelpRequest/CREATE_PETHELPREQUEST',
  UPDATE_PETHELPREQUEST: 'petHelpRequest/UPDATE_PETHELPREQUEST',
  DELETE_PETHELPREQUEST: 'petHelpRequest/DELETE_PETHELPREQUEST',
  RESET: 'petHelpRequest/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPetHelpRequest>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PetHelpRequestState = Readonly<typeof initialState>;

// Reducer

export default (state: PetHelpRequestState = initialState, action): PetHelpRequestState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PETHELPREQUESTS):
    case REQUEST(ACTION_TYPES.FETCH_PETHELPREQUEST_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PETHELPREQUEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PETHELPREQUEST):
    case REQUEST(ACTION_TYPES.UPDATE_PETHELPREQUEST):
    case REQUEST(ACTION_TYPES.DELETE_PETHELPREQUEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_PETHELPREQUESTS):
    case FAILURE(ACTION_TYPES.FETCH_PETHELPREQUEST_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PETHELPREQUEST):
    case FAILURE(ACTION_TYPES.CREATE_PETHELPREQUEST):
    case FAILURE(ACTION_TYPES.UPDATE_PETHELPREQUEST):
    case FAILURE(ACTION_TYPES.DELETE_PETHELPREQUEST):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PETHELPREQUESTS):
    case SUCCESS(ACTION_TYPES.FETCH_PETHELPREQUEST_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PETHELPREQUEST):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PETHELPREQUEST):
    case SUCCESS(ACTION_TYPES.UPDATE_PETHELPREQUEST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PETHELPREQUEST):
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

const apiUrl = 'api/pet-help-requests';
const apiSearchUrl = 'api/_search/pet-help-requests';

// Actions

export const getSearchEntities: ICrudSearchAction<IPetHelpRequest> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PETHELPREQUESTS,
  payload: axios.get<IPetHelpRequest>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IPetHelpRequest> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PETHELPREQUEST_LIST,
    payload: axios.get<IPetHelpRequest>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPetHelpRequest> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PETHELPREQUEST,
    payload: axios.get<IPetHelpRequest>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPetHelpRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PETHELPREQUEST,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPetHelpRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PETHELPREQUEST,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPetHelpRequest> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PETHELPREQUEST,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
