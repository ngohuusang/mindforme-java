import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserIdentification from './user-identification';
import UserIdentificationDetail from './user-identification-detail';
import UserIdentificationUpdate from './user-identification-update';
import UserIdentificationDeleteDialog from './user-identification-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserIdentificationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserIdentificationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserIdentificationDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserIdentification} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UserIdentificationDeleteDialog} />
  </>
);

export default Routes;
