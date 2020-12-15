import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMindingNotification, defaultValue } from 'app/shared/model/minding-notification.model';

export const ACTION_TYPES = {
  SEARCH_MINDINGNOTIFICATIONS: 'mindingNotification/SEARCH_MINDINGNOTIFICATIONS',
  FETCH_MINDINGNOTIFICATION_LIST: 'mindingNotification/FETCH_MINDINGNOTIFICATION_LIST',
  FETCH_MINDINGNOTIFICATION: 'mindingNotification/FETCH_MINDINGNOTIFICATION',
  CREATE_MINDINGNOTIFICATION: 'mindingNotification/CREATE_MINDINGNOTIFICATION',
  UPDATE_MINDINGNOTIFICATION: 'mindingNotification/UPDATE_MINDINGNOTIFICATION',
  DELETE_MINDINGNOTIFICATION: 'mindingNotification/DELETE_MINDINGNOTIFICATION',
  SET_BLOB: 'mindingNotification/SET_BLOB',
  RESET: 'mindingNotification/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMindingNotification>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type MindingNotificationState = Readonly<typeof initialState>;

// Reducer

export default (state: MindingNotificationState = initialState, action): MindingNotificationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_MINDINGNOTIFICATIONS):
    case REQUEST(ACTION_TYPES.FETCH_MINDINGNOTIFICATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MINDINGNOTIFICATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MINDINGNOTIFICATION):
    case REQUEST(ACTION_TYPES.UPDATE_MINDINGNOTIFICATION):
    case REQUEST(ACTION_TYPES.DELETE_MINDINGNOTIFICATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_MINDINGNOTIFICATIONS):
    case FAILURE(ACTION_TYPES.FETCH_MINDINGNOTIFICATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MINDINGNOTIFICATION):
    case FAILURE(ACTION_TYPES.CREATE_MINDINGNOTIFICATION):
    case FAILURE(ACTION_TYPES.UPDATE_MINDINGNOTIFICATION):
    case FAILURE(ACTION_TYPES.DELETE_MINDINGNOTIFICATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_MINDINGNOTIFICATIONS):
    case SUCCESS(ACTION_TYPES.FETCH_MINDINGNOTIFICATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_MINDINGNOTIFICATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MINDINGNOTIFICATION):
    case SUCCESS(ACTION_TYPES.UPDATE_MINDINGNOTIFICATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MINDINGNOTIFICATION):
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

const apiUrl = 'api/minding-notifications';
const apiSearchUrl = 'api/_search/minding-notifications';

// Actions

export const getSearchEntities: ICrudSearchAction<IMindingNotification> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_MINDINGNOTIFICATIONS,
  payload: axios.get<IMindingNotification>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IMindingNotification> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MINDINGNOTIFICATION_LIST,
    payload: axios.get<IMindingNotification>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IMindingNotification> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MINDINGNOTIFICATION,
    payload: axios.get<IMindingNotification>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMindingNotification> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MINDINGNOTIFICATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMindingNotification> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MINDINGNOTIFICATION,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMindingNotification> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MINDINGNOTIFICATION,
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
