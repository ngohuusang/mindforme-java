import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmergencyContact from './emergency-contact';
import EmergencyContactDetail from './emergency-contact-detail';
import EmergencyContactUpdate from './emergency-contact-update';
import EmergencyContactDeleteDialog from './emergency-contact-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmergencyContactUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmergencyContactUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmergencyContactDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmergencyContact} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmergencyContactDeleteDialog} />
  </>
);

export default Routes;
