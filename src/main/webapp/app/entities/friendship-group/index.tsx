import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FriendshipGroup from './friendship-group';
import FriendshipGroupDetail from './friendship-group-detail';
import FriendshipGroupUpdate from './friendship-group-update';
import FriendshipGroupDeleteDialog from './friendship-group-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FriendshipGroupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FriendshipGroupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FriendshipGroupDetail} />
      <ErrorBoundaryRoute path={match.url} component={FriendshipGroup} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FriendshipGroupDeleteDialog} />
  </>
);

export default Routes;
