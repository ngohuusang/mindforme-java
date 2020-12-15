import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmergencyContact, defaultValue } from 'app/shared/model/emergency-contact.model';

export const ACTION_TYPES = {
  SEARCH_EMERGENCYCONTACTS: 'emergencyContact/SEARCH_EMERGENCYCONTACTS',
  FETCH_EMERGENCYCONTACT_LIST: 'emergencyContact/FETCH_EMERGENCYCONTACT_LIST',
  FETCH_EMERGENCYCONTACT: 'emergencyContact/FETCH_EMERGENCYCONTACT',
  CREATE_EMERGENCYCONTACT: 'emergencyContact/CREATE_EMERGENCYCONTACT',
  UPDATE_EMERGENCYCONTACT: 'emergencyContact/UPDATE_EMERGENCYCONTACT',
  DELETE_EMERGENCYCONTACT: 'emergencyContact/DELETE_EMERGENCYCONTACT',
  RESET: 'emergencyContact/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmergencyContact>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EmergencyContactState = Readonly<typeof initialState>;

// Reducer

export default (state: EmergencyContactState = initialState, action): EmergencyContactState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_EMERGENCYCONTACTS):
    case REQUEST(ACTION_TYPES.FETCH_EMERGENCYCONTACT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMERGENCYCONTACT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EMERGENCYCONTACT):
    case REQUEST(ACTION_TYPES.UPDATE_EMERGENCYCONTACT):
    case REQUEST(ACTION_TYPES.DELETE_EMERGENCYCONTACT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_EMERGENCYCONTACTS):
    case FAILURE(ACTION_TYPES.FETCH_EMERGENCYCONTACT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMERGENCYCONTACT):
    case FAILURE(ACTION_TYPES.CREATE_EMERGENCYCONTACT):
    case FAILURE(ACTION_TYPES.UPDATE_EMERGENCYCONTACT):
    case FAILURE(ACTION_TYPES.DELETE_EMERGENCYCONTACT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_EMERGENCYCONTACTS):
    case SUCCESS(ACTION_TYPES.FETCH_EMERGENCYCONTACT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMERGENCYCONTACT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMERGENCYCONTACT):
    case SUCCESS(ACTION_TYPES.UPDATE_EMERGENCYCONTACT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMERGENCYCONTACT):
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

const apiUrl = 'api/emergency-contacts';
const apiSearchUrl = 'api/_search/emergency-contacts';

// Actions

export const getSearchEntities: ICrudSearchAction<IEmergencyContact> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_EMERGENCYCONTACTS,
  payload: axios.get<IEmergencyContact>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IEmergencyContact> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMERGENCYCONTACT_LIST,
    payload: axios.get<IEmergencyContact>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEmergencyContact> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMERGENCYCONTACT,
    payload: axios.get<IEmergencyContact>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEmergencyContact> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMERGENCYCONTACT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmergencyContact> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMERGENCYCONTACT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmergencyContact> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMERGENCYCONTACT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
