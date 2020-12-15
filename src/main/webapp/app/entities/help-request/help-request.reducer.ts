import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IHelpRequest, defaultValue } from 'app/shared/model/help-request.model';

export const ACTION_TYPES = {
  SEARCH_HELPREQUESTS: 'helpRequest/SEARCH_HELPREQUESTS',
  FETCH_HELPREQUEST_LIST: 'helpRequest/FETCH_HELPREQUEST_LIST',
  FETCH_HELPREQUEST: 'helpRequest/FETCH_HELPREQUEST',
  CREATE_HELPREQUEST: 'helpRequest/CREATE_HELPREQUEST',
  UPDATE_HELPREQUEST: 'helpRequest/UPDATE_HELPREQUEST',
  DELETE_HELPREQUEST: 'helpRequest/DELETE_HELPREQUEST',
  SET_BLOB: 'helpRequest/SET_BLOB',
  RESET: 'helpRequest/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IHelpRequest>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type HelpRequestState = Readonly<typeof initialState>;

// Reducer

export default (state: HelpRequestState = initialState, action): HelpRequestState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_HELPREQUESTS):
    case REQUEST(ACTION_TYPES.FETCH_HELPREQUEST_LIST):
    case REQUEST(ACTION_TYPES.FETCH_HELPREQUEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_HELPREQUEST):
    case REQUEST(ACTION_TYPES.UPDATE_HELPREQUEST):
    case REQUEST(ACTION_TYPES.DELETE_HELPREQUEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_HELPREQUESTS):
    case FAILURE(ACTION_TYPES.FETCH_HELPREQUEST_LIST):
    case FAILURE(ACTION_TYPES.FETCH_HELPREQUEST):
    case FAILURE(ACTION_TYPES.CREATE_HELPREQUEST):
    case FAILURE(ACTION_TYPES.UPDATE_HELPREQUEST):
    case FAILURE(ACTION_TYPES.DELETE_HELPREQUEST):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_HELPREQUESTS):
    case SUCCESS(ACTION_TYPES.FETCH_HELPREQUEST_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_HELPREQUEST):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_HELPREQUEST):
    case SUCCESS(ACTION_TYPES.UPDATE_HELPREQUEST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_HELPREQUEST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/help-requests';
const apiSearchUrl = 'api/_search/help-requests';

// Actions

export const getSearchEntities: ICrudSearchAction<IHelpRequest> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_HELPREQUESTS,
  payload: axios.get<IHelpRequest>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IHelpRequest> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_HELPREQUEST_LIST,
    payload: axios.get<IHelpRequest>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IHelpRequest> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_HELPREQUEST,
    payload: axios.get<IHelpRequest>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IHelpRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_HELPREQUEST,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IHelpRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_HELPREQUEST,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IHelpRequest> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_HELPREQUEST,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
