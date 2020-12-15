import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMedicalCondition, defaultValue } from 'app/shared/model/medical-condition.model';

export const ACTION_TYPES = {
  SEARCH_MEDICALCONDITIONS: 'medicalCondition/SEARCH_MEDICALCONDITIONS',
  FETCH_MEDICALCONDITION_LIST: 'medicalCondition/FETCH_MEDICALCONDITION_LIST',
  FETCH_MEDICALCONDITION: 'medicalCondition/FETCH_MEDICALCONDITION',
  CREATE_MEDICALCONDITION: 'medicalCondition/CREATE_MEDICALCONDITION',
  UPDATE_MEDICALCONDITION: 'medicalCondition/UPDATE_MEDICALCONDITION',
  DELETE_MEDICALCONDITION: 'medicalCondition/DELETE_MEDICALCONDITION',
  RESET: 'medicalCondition/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMedicalCondition>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type MedicalConditionState = Readonly<typeof initialState>;

// Reducer

export default (state: MedicalConditionState = initialState, action): MedicalConditionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_MEDICALCONDITIONS):
    case REQUEST(ACTION_TYPES.FETCH_MEDICALCONDITION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MEDICALCONDITION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MEDICALCONDITION):
    case REQUEST(ACTION_TYPES.UPDATE_MEDICALCONDITION):
    case REQUEST(ACTION_TYPES.DELETE_MEDICALCONDITION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_MEDICALCONDITIONS):
    case FAILURE(ACTION_TYPES.FETCH_MEDICALCONDITION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MEDICALCONDITION):
    case FAILURE(ACTION_TYPES.CREATE_MEDICALCONDITION):
    case FAILURE(ACTION_TYPES.UPDATE_MEDICALCONDITION):
    case FAILURE(ACTION_TYPES.DELETE_MEDICALCONDITION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_MEDICALCONDITIONS):
    case SUCCESS(ACTION_TYPES.FETCH_MEDICALCONDITION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEDICALCONDITION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MEDICALCONDITION):
    case SUCCESS(ACTION_TYPES.UPDATE_MEDICALCONDITION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MEDICALCONDITION):
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

const apiUrl = 'api/medical-conditions';
const apiSearchUrl = 'api/_search/medical-conditions';

// Actions

export const getSearchEntities: ICrudSearchAction<IMedicalCondition> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_MEDICALCONDITIONS,
  payload: axios.get<IMedicalCondition>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IMedicalCondition> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MEDICALCONDITION_LIST,
    payload: axios.get<IMedicalCondition>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IMedicalCondition> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MEDICALCONDITION,
    payload: axios.get<IMedicalCondition>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMedicalCondition> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MEDICALCONDITION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMedicalCondition> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MEDICALCONDITION,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMedicalCondition> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MEDICALCONDITION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
