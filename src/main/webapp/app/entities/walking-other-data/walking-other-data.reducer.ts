import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWalkingOtherData, defaultValue } from 'app/shared/model/walking-other-data.model';

export const ACTION_TYPES = {
  SEARCH_WALKINGOTHERDATA: 'walkingOtherData/SEARCH_WALKINGOTHERDATA',
  FETCH_WALKINGOTHERDATA_LIST: 'walkingOtherData/FETCH_WALKINGOTHERDATA_LIST',
  FETCH_WALKINGOTHERDATA: 'walkingOtherData/FETCH_WALKINGOTHERDATA',
  CREATE_WALKINGOTHERDATA: 'walkingOtherData/CREATE_WALKINGOTHERDATA',
  UPDATE_WALKINGOTHERDATA: 'walkingOtherData/UPDATE_WALKINGOTHERDATA',
  DELETE_WALKINGOTHERDATA: 'walkingOtherData/DELETE_WALKINGOTHERDATA',
  RESET: 'walkingOtherData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWalkingOtherData>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type WalkingOtherDataState = Readonly<typeof initialState>;

// Reducer

export default (state: WalkingOtherDataState = initialState, action): WalkingOtherDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_WALKINGOTHERDATA):
    case REQUEST(ACTION_TYPES.FETCH_WALKINGOTHERDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WALKINGOTHERDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_WALKINGOTHERDATA):
    case REQUEST(ACTION_TYPES.UPDATE_WALKINGOTHERDATA):
    case REQUEST(ACTION_TYPES.DELETE_WALKINGOTHERDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_WALKINGOTHERDATA):
    case FAILURE(ACTION_TYPES.FETCH_WALKINGOTHERDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WALKINGOTHERDATA):
    case FAILURE(ACTION_TYPES.CREATE_WALKINGOTHERDATA):
    case FAILURE(ACTION_TYPES.UPDATE_WALKINGOTHERDATA):
    case FAILURE(ACTION_TYPES.DELETE_WALKINGOTHERDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_WALKINGOTHERDATA):
    case SUCCESS(ACTION_TYPES.FETCH_WALKINGOTHERDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_WALKINGOTHERDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_WALKINGOTHERDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_WALKINGOTHERDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_WALKINGOTHERDATA):
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

const apiUrl = 'api/walking-other-data';
const apiSearchUrl = 'api/_search/walking-other-data';

// Actions

export const getSearchEntities: ICrudSearchAction<IWalkingOtherData> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_WALKINGOTHERDATA,
  payload: axios.get<IWalkingOtherData>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IWalkingOtherData> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_WALKINGOTHERDATA_LIST,
    payload: axios.get<IWalkingOtherData>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IWalkingOtherData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WALKINGOTHERDATA,
    payload: axios.get<IWalkingOtherData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IWalkingOtherData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WALKINGOTHERDATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWalkingOtherData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WALKINGOTHERDATA,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWalkingOtherData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WALKINGOTHERDATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
