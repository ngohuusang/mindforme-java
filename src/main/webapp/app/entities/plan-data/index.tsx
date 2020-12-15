import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PlanData from './plan-data';
import PlanDataDetail from './plan-data-detail';
import PlanDataUpdate from './plan-data-update';
import PlanDataDeleteDialog from './plan-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PlanDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PlanDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PlanDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={PlanData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PlanDataDeleteDialog} />
  </>
);

export default Routes;
