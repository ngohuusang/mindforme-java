import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAllergy, defaultValue } from 'app/shared/model/allergy.model';

export const ACTION_TYPES = {
  SEARCH_ALLERGIES: 'allergy/SEARCH_ALLERGIES',
  FETCH_ALLERGY_LIST: 'allergy/FETCH_ALLERGY_LIST',
  FETCH_ALLERGY: 'allergy/FETCH_ALLERGY',
  CREATE_ALLERGY: 'allergy/CREATE_ALLERGY',
  UPDATE_ALLERGY: 'allergy/UPDATE_ALLERGY',
  DELETE_ALLERGY: 'allergy/DELETE_ALLERGY',
  RESET: 'allergy/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAllergy>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type AllergyState = Readonly<typeof initialState>;

// Reducer

export default (state: AllergyState = initialState, action): AllergyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_ALLERGIES):
    case REQUEST(ACTION_TYPES.FETCH_ALLERGY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ALLERGY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ALLERGY):
    case REQUEST(ACTION_TYPES.UPDATE_ALLERGY):
    case REQUEST(ACTION_TYPES.DELETE_ALLERGY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_ALLERGIES):
    case FAILURE(ACTION_TYPES.FETCH_ALLERGY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ALLERGY):
    case FAILURE(ACTION_TYPES.CREATE_ALLERGY):
    case FAILURE(ACTION_TYPES.UPDATE_ALLERGY):
    case FAILURE(ACTION_TYPES.DELETE_ALLERGY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_ALLERGIES):
    case SUCCESS(ACTION_TYPES.FETCH_ALLERGY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ALLERGY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ALLERGY):
    case SUCCESS(ACTION_TYPES.UPDATE_ALLERGY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ALLERGY):
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

const apiUrl = 'api/allergies';
const apiSearchUrl = 'api/_search/allergies';

// Actions

export const getSearchEntities: ICrudSearchAction<IAllergy> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_ALLERGIES,
  payload: axios.get<IAllergy>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IAllergy> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ALLERGY_LIST,
    payload: axios.get<IAllergy>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IAllergy> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ALLERGY,
    payload: axios.get<IAllergy>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAllergy> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ALLERGY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAllergy> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ALLERGY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAllergy> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ALLERGY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
