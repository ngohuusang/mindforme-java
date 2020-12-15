import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMedicalConditionData, defaultValue } from 'app/shared/model/medical-condition-data.model';

export const ACTION_TYPES = {
  SEARCH_MEDICALCONDITIONDATA: 'medicalConditionData/SEARCH_MEDICALCONDITIONDATA',
  FETCH_MEDICALCONDITIONDATA_LIST: 'medicalConditionData/FETCH_MEDICALCONDITIONDATA_LIST',
  FETCH_MEDICALCONDITIONDATA: 'medicalConditionData/FETCH_MEDICALCONDITIONDATA',
  CREATE_MEDICALCONDITIONDATA: 'medicalConditionData/CREATE_MEDICALCONDITIONDATA',
  UPDATE_MEDICALCONDITIONDATA: 'medicalConditionData/UPDATE_MEDICALCONDITIONDATA',
  DELETE_MEDICALCONDITIONDATA: 'medicalConditionData/DELETE_MEDICALCONDITIONDATA',
  RESET: 'medicalConditionData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMedicalConditionData>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type MedicalConditionDataState = Readonly<typeof initialState>;

// Reducer

export default (state: MedicalConditionDataState = initialState, action): MedicalConditionDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_MEDICALCONDITIONDATA):
    case REQUEST(ACTION_TYPES.FETCH_MEDICALCONDITIONDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MEDICALCONDITIONDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MEDICALCONDITIONDATA):
    case REQUEST(ACTION_TYPES.UPDATE_MEDICALCONDITIONDATA):
    case REQUEST(ACTION_TYPES.DELETE_MEDICALCONDITIONDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_MEDICALCONDITIONDATA):
    case FAILURE(ACTION_TYPES.FETCH_MEDICALCONDITIONDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MEDICALCONDITIONDATA):
    case FAILURE(ACTION_TYPES.CREATE_MEDICALCONDITIONDATA):
    case FAILURE(ACTION_TYPES.UPDATE_MEDICALCONDITIONDATA):
    case FAILURE(ACTION_TYPES.DELETE_MEDICALCONDITIONDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_MEDICALCONDITIONDATA):
    case SUCCESS(ACTION_TYPES.FETCH_MEDICALCONDITIONDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEDICALCONDITIONDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MEDICALCONDITIONDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_MEDICALCONDITIONDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MEDICALCONDITIONDATA):
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

const apiUrl = 'api/medical-condition-data';
const apiSearchUrl = 'api/_search/medical-condition-data';

// Actions

export const getSearchEntities: ICrudSearchAction<IMedicalConditionData> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_MEDICALCONDITIONDATA,
  payload: axios.get<IMedicalConditionData>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IMedicalConditionData> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MEDICALCONDITIONDATA_LIST,
    payload: axios.get<IMedicalConditionData>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IMedicalConditionData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MEDICALCONDITIONDATA,
    payload: axios.get<IMedicalConditionData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMedicalConditionData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MEDICALCONDITIONDATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMedicalConditionData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MEDICALCONDITIONDATA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMedicalConditionData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MEDICALCONDITIONDATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
