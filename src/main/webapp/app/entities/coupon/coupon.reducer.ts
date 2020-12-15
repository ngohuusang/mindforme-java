import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICoupon, defaultValue } from 'app/shared/model/coupon.model';

export const ACTION_TYPES = {
  SEARCH_COUPONS: 'coupon/SEARCH_COUPONS',
  FETCH_COUPON_LIST: 'coupon/FETCH_COUPON_LIST',
  FETCH_COUPON: 'coupon/FETCH_COUPON',
  CREATE_COUPON: 'coupon/CREATE_COUPON',
  UPDATE_COUPON: 'coupon/UPDATE_COUPON',
  DELETE_COUPON: 'coupon/DELETE_COUPON',
  SET_BLOB: 'coupon/SET_BLOB',
  RESET: 'coupon/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICoupon>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CouponState = Readonly<typeof initialState>;

// Reducer

export default (state: CouponState = initialState, action): CouponState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_COUPONS):
    case REQUEST(ACTION_TYPES.FETCH_COUPON_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COUPON):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COUPON):
    case REQUEST(ACTION_TYPES.UPDATE_COUPON):
    case REQUEST(ACTION_TYPES.DELETE_COUPON):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_COUPONS):
    case FAILURE(ACTION_TYPES.FETCH_COUPON_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COUPON):
    case FAILURE(ACTION_TYPES.CREATE_COUPON):
    case FAILURE(ACTION_TYPES.UPDATE_COUPON):
    case FAILURE(ACTION_TYPES.DELETE_COUPON):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_COUPONS):
    case SUCCESS(ACTION_TYPES.FETCH_COUPON_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_COUPON):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COUPON):
    case SUCCESS(ACTION_TYPES.UPDATE_COUPON):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COUPON):
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

const apiUrl = 'api/coupons';
const apiSearchUrl = 'api/_search/coupons';

// Actions

export const getSearchEntities: ICrudSearchAction<ICoupon> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_COUPONS,
  payload: axios.get<ICoupon>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<ICoupon> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_COUPON_LIST,
    payload: axios.get<ICoupon>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICoupon> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COUPON,
    payload: axios.get<ICoupon>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICoupon> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COUPON,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICoupon> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COUPON,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICoupon> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COUPON,
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
