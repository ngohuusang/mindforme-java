import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISupportedHelpRequest, defaultValue } from 'app/shared/model/supported-help-request.model';

export const ACTION_TYPES = {
  SEARCH_SUPPORTEDHELPREQUESTS: 'supportedHelpRequest/SEARCH_SUPPORTEDHELPREQUESTS',
  FETCH_SUPPORTEDHELPREQUEST_LIST: 'supportedHelpRequest/FETCH_SUPPORTEDHELPREQUEST_LIST',
  FETCH_SUPPORTEDHELPREQUEST: 'supportedHelpRequest/FETCH_SUPPORTEDHELPREQUEST',
  CREATE_SUPPORTEDHELPREQUEST: 'supportedHelpRequest/CREATE_SUPPORTEDHELPREQUEST',
  UPDATE_SUPPORTEDHELPREQUEST: 'supportedHelpRequest/UPDATE_SUPPORTEDHELPREQUEST',
  DELETE_SUPPORTEDHELPREQUEST: 'supportedHelpRequest/DELETE_SUPPORTEDHELPREQUEST',
  RESET: 'supportedHelpRequest/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISupportedHelpRequest>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SupportedHelpRequestState = Readonly<typeof initialState>;

// Reducer

export default (state: SupportedHelpRequestState = initialState, action): SupportedHelpRequestState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SUPPORTEDHELPREQUESTS):
    case REQUEST(ACTION_TYPES.FETCH_SUPPORTEDHELPREQUEST_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SUPPORTEDHELPREQUEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SUPPORTEDHELPREQUEST):
    case REQUEST(ACTION_TYPES.UPDATE_SUPPORTEDHELPREQUEST):
    case REQUEST(ACTION_TYPES.DELETE_SUPPORTEDHELPREQUEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_SUPPORTEDHELPREQUESTS):
    case FAILURE(ACTION_TYPES.FETCH_SUPPORTEDHELPREQUEST_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SUPPORTEDHELPREQUEST):
    case FAILURE(ACTION_TYPES.CREATE_SUPPORTEDHELPREQUEST):
    case FAILURE(ACTION_TYPES.UPDATE_SUPPORTEDHELPREQUEST):
    case FAILURE(ACTION_TYPES.DELETE_SUPPORTEDHELPREQUEST):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_SUPPORTEDHELPREQUESTS):
    case SUCCESS(ACTION_TYPES.FETCH_SUPPORTEDHELPREQUEST_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUPPORTEDHELPREQUEST):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SUPPORTEDHELPREQUEST):
    case SUCCESS(ACTION_TYPES.UPDATE_SUPPORTEDHELPREQUEST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SUPPORTEDHELPREQUEST):
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

const apiUrl = 'api/supported-help-requests';
const apiSearchUrl = 'api/_search/supported-help-requests';

// Actions

export const getSearchEntities: ICrudSearchAction<ISupportedHelpRequest> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SUPPORTEDHELPREQUESTS,
  payload: axios.get<ISupportedHelpRequest>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<ISupportedHelpRequest> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SUPPORTEDHELPREQUEST_LIST,
    payload: axios.get<ISupportedHelpRequest>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISupportedHelpRequest> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SUPPORTEDHELPREQUEST,
    payload: axios.get<ISupportedHelpRequest>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISupportedHelpRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SUPPORTEDHELPREQUEST,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISupportedHelpRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SUPPORTEDHELPREQUEST,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISupportedHelpRequest> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SUPPORTEDHELPREQUEST,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
