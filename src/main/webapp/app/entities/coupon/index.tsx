import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Coupon from './coupon';
import CouponDetail from './coupon-detail';
import CouponUpdate from './coupon-update';
import CouponDeleteDialog from './coupon-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CouponUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CouponUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CouponDetail} />
      <ErrorBoundaryRoute path={match.url} component={Coupon} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CouponDeleteDialog} />
  </>
);

export default Routes;
