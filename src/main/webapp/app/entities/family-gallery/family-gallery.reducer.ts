import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFamilyGallery, defaultValue } from 'app/shared/model/family-gallery.model';

export const ACTION_TYPES = {
  SEARCH_FAMILYGALLERIES: 'familyGallery/SEARCH_FAMILYGALLERIES',
  FETCH_FAMILYGALLERY_LIST: 'familyGallery/FETCH_FAMILYGALLERY_LIST',
  FETCH_FAMILYGALLERY: 'familyGallery/FETCH_FAMILYGALLERY',
  CREATE_FAMILYGALLERY: 'familyGallery/CREATE_FAMILYGALLERY',
  UPDATE_FAMILYGALLERY: 'familyGallery/UPDATE_FAMILYGALLERY',
  DELETE_FAMILYGALLERY: 'familyGallery/DELETE_FAMILYGALLERY',
  RESET: 'familyGallery/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFamilyGallery>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type FamilyGalleryState = Readonly<typeof initialState>;

// Reducer

export default (state: FamilyGalleryState = initialState, action): FamilyGalleryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_FAMILYGALLERIES):
    case REQUEST(ACTION_TYPES.FETCH_FAMILYGALLERY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FAMILYGALLERY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_FAMILYGALLERY):
    case REQUEST(ACTION_TYPES.UPDATE_FAMILYGALLERY):
    case REQUEST(ACTION_TYPES.DELETE_FAMILYGALLERY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_FAMILYGALLERIES):
    case FAILURE(ACTION_TYPES.FETCH_FAMILYGALLERY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FAMILYGALLERY):
    case FAILURE(ACTION_TYPES.CREATE_FAMILYGALLERY):
    case FAILURE(ACTION_TYPES.UPDATE_FAMILYGALLERY):
    case FAILURE(ACTION_TYPES.DELETE_FAMILYGALLERY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_FAMILYGALLERIES):
    case SUCCESS(ACTION_TYPES.FETCH_FAMILYGALLERY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_FAMILYGALLERY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_FAMILYGALLERY):
    case SUCCESS(ACTION_TYPES.UPDATE_FAMILYGALLERY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_FAMILYGALLERY):
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

const apiUrl = 'api/family-galleries';
const apiSearchUrl = 'api/_search/family-galleries';

// Actions

export const getSearchEntities: ICrudSearchAction<IFamilyGallery> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_FAMILYGALLERIES,
  payload: axios.get<IFamilyGallery>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IFamilyGallery> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_FAMILYGALLERY_LIST,
    payload: axios.get<IFamilyGallery>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IFamilyGallery> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FAMILYGALLERY,
    payload: axios.get<IFamilyGallery>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IFamilyGallery> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FAMILYGALLERY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFamilyGallery> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FAMILYGALLERY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFamilyGallery> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FAMILYGALLERY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
