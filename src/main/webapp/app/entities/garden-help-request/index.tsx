import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GardenHelpRequest from './garden-help-request';
import GardenHelpRequestDetail from './garden-help-request-detail';
import GardenHelpRequestUpdate from './garden-help-request-update';
import GardenHelpRequestDeleteDialog from './garden-help-request-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GardenHelpRequestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GardenHelpRequestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GardenHelpRequestDetail} />
      <ErrorBoundaryRoute path={match.url} component={GardenHelpRequest} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GardenHelpRequestDeleteDialog} />
  </>
);

export default Routes;
