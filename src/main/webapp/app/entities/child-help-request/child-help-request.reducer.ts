import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IChildHelpRequest, defaultValue } from 'app/shared/model/child-help-request.model';

export const ACTION_TYPES = {
  SEARCH_CHILDHELPREQUESTS: 'childHelpRequest/SEARCH_CHILDHELPREQUESTS',
  FETCH_CHILDHELPREQUEST_LIST: 'childHelpRequest/FETCH_CHILDHELPREQUEST_LIST',
  FETCH_CHILDHELPREQUEST: 'childHelpRequest/FETCH_CHILDHELPREQUEST',
  CREATE_CHILDHELPREQUEST: 'childHelpRequest/CREATE_CHILDHELPREQUEST',
  UPDATE_CHILDHELPREQUEST: 'childHelpRequest/UPDATE_CHILDHELPREQUEST',
  DELETE_CHILDHELPREQUEST: 'childHelpRequest/DELETE_CHILDHELPREQUEST',
  RESET: 'childHelpRequest/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IChildHelpRequest>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ChildHelpRequestState = Readonly<typeof initialState>;

// Reducer

export default (state: ChildHelpRequestState = initialState, action): ChildHelpRequestState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_CHILDHELPREQUESTS):
    case REQUEST(ACTION_TYPES.FETCH_CHILDHELPREQUEST_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CHILDHELPREQUEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CHILDHELPREQUEST):
    case REQUEST(ACTION_TYPES.UPDATE_CHILDHELPREQUEST):
    case REQUEST(ACTION_TYPES.DELETE_CHILDHELPREQUEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_CHILDHELPREQUESTS):
    case FAILURE(ACTION_TYPES.FETCH_CHILDHELPREQUEST_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CHILDHELPREQUEST):
    case FAILURE(ACTION_TYPES.CREATE_CHILDHELPREQUEST):
    case FAILURE(ACTION_TYPES.UPDATE_CHILDHELPREQUEST):
    case FAILURE(ACTION_TYPES.DELETE_CHILDHELPREQUEST):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_CHILDHELPREQUESTS):
    case SUCCESS(ACTION_TYPES.FETCH_CHILDHELPREQUEST_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CHILDHELPREQUEST):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CHILDHELPREQUEST):
    case SUCCESS(ACTION_TYPES.UPDATE_CHILDHELPREQUEST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CHILDHELPREQUEST):
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

const apiUrl = 'api/child-help-requests';
const apiSearchUrl = 'api/_search/child-help-requests';

// Actions

export const getSearchEntities: ICrudSearchAction<IChildHelpRequest> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_CHILDHELPREQUESTS,
  payload: axios.get<IChildHelpRequest>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IChildHelpRequest> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CHILDHELPREQUEST_LIST,
    payload: axios.get<IChildHelpRequest>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IChildHelpRequest> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CHILDHELPREQUEST,
    payload: axios.get<IChildHelpRequest>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IChildHelpRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CHILDHELPREQUEST,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IChildHelpRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CHILDHELPREQUEST,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IChildHelpRequest> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CHILDHELPREQUEST,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
