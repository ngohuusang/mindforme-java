import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICouponType, defaultValue } from 'app/shared/model/coupon-type.model';

export const ACTION_TYPES = {
  SEARCH_COUPONTYPES: 'couponType/SEARCH_COUPONTYPES',
  FETCH_COUPONTYPE_LIST: 'couponType/FETCH_COUPONTYPE_LIST',
  FETCH_COUPONTYPE: 'couponType/FETCH_COUPONTYPE',
  CREATE_COUPONTYPE: 'couponType/CREATE_COUPONTYPE',
  UPDATE_COUPONTYPE: 'couponType/UPDATE_COUPONTYPE',
  DELETE_COUPONTYPE: 'couponType/DELETE_COUPONTYPE',
  RESET: 'couponType/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICouponType>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CouponTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: CouponTypeState = initialState, action): CouponTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_COUPONTYPES):
    case REQUEST(ACTION_TYPES.FETCH_COUPONTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COUPONTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COUPONTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_COUPONTYPE):
    case REQUEST(ACTION_TYPES.DELETE_COUPONTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_COUPONTYPES):
    case FAILURE(ACTION_TYPES.FETCH_COUPONTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COUPONTYPE):
    case FAILURE(ACTION_TYPES.CREATE_COUPONTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_COUPONTYPE):
    case FAILURE(ACTION_TYPES.DELETE_COUPONTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_COUPONTYPES):
    case SUCCESS(ACTION_TYPES.FETCH_COUPONTYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_COUPONTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COUPONTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_COUPONTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COUPONTYPE):
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

const apiUrl = 'api/coupon-types';
const apiSearchUrl = 'api/_search/coupon-types';

// Actions

export const getSearchEntities: ICrudSearchAction<ICouponType> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_COUPONTYPES,
  payload: axios.get<ICouponType>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<ICouponType> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_COUPONTYPE_LIST,
    payload: axios.get<ICouponType>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICouponType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COUPONTYPE,
    payload: axios.get<ICouponType>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICouponType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COUPONTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICouponType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COUPONTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICouponType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COUPONTYPE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
