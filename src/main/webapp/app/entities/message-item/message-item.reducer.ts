import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMessageItem, defaultValue } from 'app/shared/model/message-item.model';

export const ACTION_TYPES = {
  SEARCH_MESSAGEITEMS: 'messageItem/SEARCH_MESSAGEITEMS',
  FETCH_MESSAGEITEM_LIST: 'messageItem/FETCH_MESSAGEITEM_LIST',
  FETCH_MESSAGEITEM: 'messageItem/FETCH_MESSAGEITEM',
  CREATE_MESSAGEITEM: 'messageItem/CREATE_MESSAGEITEM',
  UPDATE_MESSAGEITEM: 'messageItem/UPDATE_MESSAGEITEM',
  DELETE_MESSAGEITEM: 'messageItem/DELETE_MESSAGEITEM',
  RESET: 'messageItem/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMessageItem>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type MessageItemState = Readonly<typeof initialState>;

// Reducer

export default (state: MessageItemState = initialState, action): MessageItemState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_MESSAGEITEMS):
    case REQUEST(ACTION_TYPES.FETCH_MESSAGEITEM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MESSAGEITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MESSAGEITEM):
    case REQUEST(ACTION_TYPES.UPDATE_MESSAGEITEM):
    case REQUEST(ACTION_TYPES.DELETE_MESSAGEITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_MESSAGEITEMS):
    case FAILURE(ACTION_TYPES.FETCH_MESSAGEITEM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MESSAGEITEM):
    case FAILURE(ACTION_TYPES.CREATE_MESSAGEITEM):
    case FAILURE(ACTION_TYPES.UPDATE_MESSAGEITEM):
    case FAILURE(ACTION_TYPES.DELETE_MESSAGEITEM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_MESSAGEITEMS):
    case SUCCESS(ACTION_TYPES.FETCH_MESSAGEITEM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_MESSAGEITEM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MESSAGEITEM):
    case SUCCESS(ACTION_TYPES.UPDATE_MESSAGEITEM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MESSAGEITEM):
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

const apiUrl = 'api/message-items';
const apiSearchUrl = 'api/_search/message-items';

// Actions

export const getSearchEntities: ICrudSearchAction<IMessageItem> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_MESSAGEITEMS,
  payload: axios.get<IMessageItem>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IMessageItem> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MESSAGEITEM_LIST,
    payload: axios.get<IMessageItem>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IMessageItem> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MESSAGEITEM,
    payload: axios.get<IMessageItem>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMessageItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MESSAGEITEM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMessageItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MESSAGEITEM,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMessageItem> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MESSAGEITEM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
