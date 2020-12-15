import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import WalkingOther from './walking-other';
import WalkingOtherDetail from './walking-other-detail';
import WalkingOtherUpdate from './walking-other-update';
import WalkingOtherDeleteDialog from './walking-other-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WalkingOtherUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WalkingOtherUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WalkingOtherDetail} />
      <ErrorBoundaryRoute path={match.url} component={WalkingOther} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={WalkingOtherDeleteDialog} />
  </>
);

export default Routes;
