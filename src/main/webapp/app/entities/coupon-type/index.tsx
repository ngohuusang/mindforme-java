import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CouponType from './coupon-type';
import CouponTypeDetail from './coupon-type-detail';
import CouponTypeUpdate from './coupon-type-update';
import CouponTypeDeleteDialog from './coupon-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CouponTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CouponTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CouponTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={CouponType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CouponTypeDeleteDialog} />
  </>
);

export default Routes;
