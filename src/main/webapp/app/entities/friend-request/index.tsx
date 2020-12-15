import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FriendRequest from './friend-request';
import FriendRequestDetail from './friend-request-detail';
import FriendRequestUpdate from './friend-request-update';
import FriendRequestDeleteDialog from './friend-request-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FriendRequestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FriendRequestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FriendRequestDetail} />
      <ErrorBoundaryRoute path={match.url} component={FriendRequest} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FriendRequestDeleteDialog} />
  </>
);

export default Routes;
