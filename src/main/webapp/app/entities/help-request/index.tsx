import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import HelpRequest from './help-request';
import HelpRequestDetail from './help-request-detail';
import HelpRequestUpdate from './help-request-update';
import HelpRequestDeleteDialog from './help-request-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={HelpRequestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={HelpRequestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={HelpRequestDetail} />
      <ErrorBoundaryRoute path={match.url} component={HelpRequest} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={HelpRequestDeleteDialog} />
  </>
);

export default Routes;
