import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import HouseHelpRequest from './house-help-request';
import HouseHelpRequestDetail from './house-help-request-detail';
import HouseHelpRequestUpdate from './house-help-request-update';
import HouseHelpRequestDeleteDialog from './house-help-request-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={HouseHelpRequestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={HouseHelpRequestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={HouseHelpRequestDetail} />
      <ErrorBoundaryRoute path={match.url} component={HouseHelpRequest} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={HouseHelpRequestDeleteDialog} />
  </>
);

export default Routes;
