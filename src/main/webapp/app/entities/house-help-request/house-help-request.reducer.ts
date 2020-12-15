import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IHouseHelpRequest, defaultValue } from 'app/shared/model/house-help-request.model';

export const ACTION_TYPES = {
  SEARCH_HOUSEHELPREQUESTS: 'houseHelpRequest/SEARCH_HOUSEHELPREQUESTS',
  FETCH_HOUSEHELPREQUEST_LIST: 'houseHelpRequest/FETCH_HOUSEHELPREQUEST_LIST',
  FETCH_HOUSEHELPREQUEST: 'houseHelpRequest/FETCH_HOUSEHELPREQUEST',
  CREATE_HOUSEHELPREQUEST: 'houseHelpRequest/CREATE_HOUSEHELPREQUEST',
  UPDATE_HOUSEHELPREQUEST: 'houseHelpRequest/UPDATE_HOUSEHELPREQUEST',
  DELETE_HOUSEHELPREQUEST: 'houseHelpRequest/DELETE_HOUSEHELPREQUEST',
  SET_BLOB: 'houseHelpRequest/SET_BLOB',
  RESET: 'houseHelpRequest/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IHouseHelpRequest>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type HouseHelpRequestState = Readonly<typeof initialState>;

// Reducer

export default (state: HouseHelpRequestState = initialState, action): HouseHelpRequestState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_HOUSEHELPREQUESTS):
    case REQUEST(ACTION_TYPES.FETCH_HOUSEHELPREQUEST_LIST):
    case REQUEST(ACTION_TYPES.FETCH_HOUSEHELPREQUEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_HOUSEHELPREQUEST):
    case REQUEST(ACTION_TYPES.UPDATE_HOUSEHELPREQUEST):
    case REQUEST(ACTION_TYPES.DELETE_HOUSEHELPREQUEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_HOUSEHELPREQUESTS):
    case FAILURE(ACTION_TYPES.FETCH_HOUSEHELPREQUEST_LIST):
    case FAILURE(ACTION_TYPES.FETCH_HOUSEHELPREQUEST):
    case FAILURE(ACTION_TYPES.CREATE_HOUSEHELPREQUEST):
    case FAILURE(ACTION_TYPES.UPDATE_HOUSEHELPREQUEST):
    case FAILURE(ACTION_TYPES.DELETE_HOUSEHELPREQUEST):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_HOUSEHELPREQUESTS):
    case SUCCESS(ACTION_TYPES.FETCH_HOUSEHELPREQUEST_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_HOUSEHELPREQUEST):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_HOUSEHELPREQUEST):
    case SUCCESS(ACTION_TYPES.UPDATE_HOUSEHELPREQUEST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_HOUSEHELPREQUEST):
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

const apiUrl = 'api/house-help-requests';
const apiSearchUrl = 'api/_search/house-help-requests';

// Actions

export const getSearchEntities: ICrudSearchAction<IHouseHelpRequest> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_HOUSEHELPREQUESTS,
  payload: axios.get<IHouseHelpRequest>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IHouseHelpRequest> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_HOUSEHELPREQUEST_LIST,
    payload: axios.get<IHouseHelpRequest>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IHouseHelpRequest> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_HOUSEHELPREQUEST,
    payload: axios.get<IHouseHelpRequest>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IHouseHelpRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_HOUSEHELPREQUEST,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IHouseHelpRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_HOUSEHELPREQUEST,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IHouseHelpRequest> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_HOUSEHELPREQUEST,
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
