import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import WalkingOtherData from './walking-other-data';
import WalkingOtherDataDetail from './walking-other-data-detail';
import WalkingOtherDataUpdate from './walking-other-data-update';
import WalkingOtherDataDeleteDialog from './walking-other-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WalkingOtherDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WalkingOtherDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WalkingOtherDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={WalkingOtherData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={WalkingOtherDataDeleteDialog} />
  </>
);

export default Routes;
