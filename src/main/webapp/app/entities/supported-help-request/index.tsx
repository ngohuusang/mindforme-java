import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SupportedHelpRequest from './supported-help-request';
import SupportedHelpRequestDetail from './supported-help-request-detail';
import SupportedHelpRequestUpdate from './supported-help-request-update';
import SupportedHelpRequestDeleteDialog from './supported-help-request-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SupportedHelpRequestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SupportedHelpRequestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SupportedHelpRequestDetail} />
      <ErrorBoundaryRoute path={match.url} component={SupportedHelpRequest} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SupportedHelpRequestDeleteDialog} />
  </>
);

export default Routes;
