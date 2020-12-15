import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PetHelpRequest from './pet-help-request';
import PetHelpRequestDetail from './pet-help-request-detail';
import PetHelpRequestUpdate from './pet-help-request-update';
import PetHelpRequestDeleteDialog from './pet-help-request-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PetHelpRequestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PetHelpRequestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PetHelpRequestDetail} />
      <ErrorBoundaryRoute path={match.url} component={PetHelpRequest} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PetHelpRequestDeleteDialog} />
  </>
);

export default Routes;
