import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import StateData from './state-data';
import StateDataDetail from './state-data-detail';
import StateDataUpdate from './state-data-update';
import StateDataDeleteDialog from './state-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StateDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StateDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StateDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={StateData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StateDataDeleteDialog} />
  </>
);

export default Routes;
