import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Friendship from './friendship';
import FriendshipDetail from './friendship-detail';
import FriendshipUpdate from './friendship-update';
import FriendshipDeleteDialog from './friendship-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FriendshipUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FriendshipUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FriendshipDetail} />
      <ErrorBoundaryRoute path={match.url} component={Friendship} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FriendshipDeleteDialog} />
  </>
);

export default Routes;
