import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Supported from './supported';
import SupportedDetail from './supported-detail';
import SupportedUpdate from './supported-update';
import SupportedDeleteDialog from './supported-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SupportedUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SupportedUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SupportedDetail} />
      <ErrorBoundaryRoute path={match.url} component={Supported} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SupportedDeleteDialog} />
  </>
);

export default Routes;
