import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Feeding from './feeding';
import FeedingDetail from './feeding-detail';
import FeedingUpdate from './feeding-update';
import FeedingDeleteDialog from './feeding-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FeedingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FeedingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FeedingDetail} />
      <ErrorBoundaryRoute path={match.url} component={Feeding} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FeedingDeleteDialog} />
  </>
);

export default Routes;
