import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FeedingData from './feeding-data';
import FeedingDataDetail from './feeding-data-detail';
import FeedingDataUpdate from './feeding-data-update';
import FeedingDataDeleteDialog from './feeding-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FeedingDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FeedingDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FeedingDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={FeedingData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FeedingDataDeleteDialog} />
  </>
);

export default Routes;
