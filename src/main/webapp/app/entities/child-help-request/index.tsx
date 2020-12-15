import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ChildHelpRequest from './child-help-request';
import ChildHelpRequestDetail from './child-help-request-detail';
import ChildHelpRequestUpdate from './child-help-request-update';
import ChildHelpRequestDeleteDialog from './child-help-request-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ChildHelpRequestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ChildHelpRequestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ChildHelpRequestDetail} />
      <ErrorBoundaryRoute path={match.url} component={ChildHelpRequest} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ChildHelpRequestDeleteDialog} />
  </>
);

export default Routes;
