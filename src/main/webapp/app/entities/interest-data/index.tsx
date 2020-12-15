import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import InterestData from './interest-data';
import InterestDataDetail from './interest-data-detail';
import InterestDataUpdate from './interest-data-update';
import InterestDataDeleteDialog from './interest-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InterestDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InterestDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InterestDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={InterestData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={InterestDataDeleteDialog} />
  </>
);

export default Routes;
