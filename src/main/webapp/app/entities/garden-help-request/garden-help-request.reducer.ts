import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGardenHelpRequest, defaultValue } from 'app/shared/model/garden-help-request.model';

export const ACTION_TYPES = {
  SEARCH_GARDENHELPREQUESTS: 'gardenHelpRequest/SEARCH_GARDENHELPREQUESTS',
  FETCH_GARDENHELPREQUEST_LIST: 'gardenHelpRequest/FETCH_GARDENHELPREQUEST_LIST',
  FETCH_GARDENHELPREQUEST: 'gardenHelpRequest/FETCH_GARDENHELPREQUEST',
  CREATE_GARDENHELPREQUEST: 'gardenHelpRequest/CREATE_GARDENHELPREQUEST',
  UPDATE_GARDENHELPREQUEST: 'gardenHelpRequest/UPDATE_GARDENHELPREQUEST',
  DELETE_GARDENHELPREQUEST: 'gardenHelpRequest/DELETE_GARDENHELPREQUEST',
  RESET: 'gardenHelpRequest/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGardenHelpRequest>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type GardenHelpRequestState = Readonly<typeof initialState>;

// Reducer

export default (state: GardenHelpRequestState = initialState, action): GardenHelpRequestState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_GARDENHELPREQUESTS):
    case REQUEST(ACTION_TYPES.FETCH_GARDENHELPREQUEST_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GARDENHELPREQUEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GARDENHELPREQUEST):
    case REQUEST(ACTION_TYPES.UPDATE_GARDENHELPREQUEST):
    case REQUEST(ACTION_TYPES.DELETE_GARDENHELPREQUEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_GARDENHELPREQUESTS):
    case FAILURE(ACTION_TYPES.FETCH_GARDENHELPREQUEST_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GARDENHELPREQUEST):
    case FAILURE(ACTION_TYPES.CREATE_GARDENHELPREQUEST):
    case FAILURE(ACTION_TYPES.UPDATE_GARDENHELPREQUEST):
    case FAILURE(ACTION_TYPES.DELETE_GARDENHELPREQUEST):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_GARDENHELPREQUESTS):
    case SUCCESS(ACTION_TYPES.FETCH_GARDENHELPREQUEST_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_GARDENHELPREQUEST):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GARDENHELPREQUEST):
    case SUCCESS(ACTION_TYPES.UPDATE_GARDENHELPREQUEST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GARDENHELPREQUEST):
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

const apiUrl = 'api/garden-help-requests';
const apiSearchUrl = 'api/_search/garden-help-requests';

// Actions

export const getSearchEntities: ICrudSearchAction<IGardenHelpRequest> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_GARDENHELPREQUESTS,
  payload: axios.get<IGardenHelpRequest>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IGardenHelpRequest> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_GARDENHELPREQUEST_LIST,
    payload: axios.get<IGardenHelpRequest>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IGardenHelpRequest> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GARDENHELPREQUEST,
    payload: axios.get<IGardenHelpRequest>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGardenHelpRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GARDENHELPREQUEST,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGardenHelpRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GARDENHELPREQUEST,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGardenHelpRequest> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GARDENHELPREQUEST,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
