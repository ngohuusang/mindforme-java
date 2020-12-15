import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Interest from './interest';
import InterestDetail from './interest-detail';
import InterestUpdate from './interest-update';
import InterestDeleteDialog from './interest-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InterestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InterestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InterestDetail} />
      <ErrorBoundaryRoute path={match.url} component={Interest} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={InterestDeleteDialog} />
  </>
);

export default Routes;
