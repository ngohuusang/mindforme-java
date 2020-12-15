import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ILanguage, defaultValue } from 'app/shared/model/language.model';

export const ACTION_TYPES = {
  SEARCH_LANGUAGES: 'language/SEARCH_LANGUAGES',
  FETCH_LANGUAGE_LIST: 'language/FETCH_LANGUAGE_LIST',
  FETCH_LANGUAGE: 'language/FETCH_LANGUAGE',
  CREATE_LANGUAGE: 'language/CREATE_LANGUAGE',
  UPDATE_LANGUAGE: 'language/UPDATE_LANGUAGE',
  DELETE_LANGUAGE: 'language/DELETE_LANGUAGE',
  RESET: 'language/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ILanguage>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type LanguageState = Readonly<typeof initialState>;

// Reducer

export default (state: LanguageState = initialState, action): LanguageState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_LANGUAGES):
    case REQUEST(ACTION_TYPES.FETCH_LANGUAGE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LANGUAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_LANGUAGE):
    case REQUEST(ACTION_TYPES.UPDATE_LANGUAGE):
    case REQUEST(ACTION_TYPES.DELETE_LANGUAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_LANGUAGES):
    case FAILURE(ACTION_TYPES.FETCH_LANGUAGE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LANGUAGE):
    case FAILURE(ACTION_TYPES.CREATE_LANGUAGE):
    case FAILURE(ACTION_TYPES.UPDATE_LANGUAGE):
    case FAILURE(ACTION_TYPES.DELETE_LANGUAGE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_LANGUAGES):
    case SUCCESS(ACTION_TYPES.FETCH_LANGUAGE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_LANGUAGE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_LANGUAGE):
    case SUCCESS(ACTION_TYPES.UPDATE_LANGUAGE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_LANGUAGE):
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

const apiUrl = 'api/languages';
const apiSearchUrl = 'api/_search/languages';

// Actions

export const getSearchEntities: ICrudSearchAction<ILanguage> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_LANGUAGES,
  payload: axios.get<ILanguage>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<ILanguage> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_LANGUAGE_LIST,
    payload: axios.get<ILanguage>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ILanguage> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LANGUAGE,
    payload: axios.get<ILanguage>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ILanguage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LANGUAGE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ILanguage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LANGUAGE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ILanguage> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LANGUAGE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
