import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MindingNotification from './minding-notification';
import MindingNotificationDetail from './minding-notification-detail';
import MindingNotificationUpdate from './minding-notification-update';
import MindingNotificationDeleteDialog from './minding-notification-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MindingNotificationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MindingNotificationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MindingNotificationDetail} />
      <ErrorBoundaryRoute path={match.url} component={MindingNotification} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MindingNotificationDeleteDialog} />
  </>
);

export default Routes;
