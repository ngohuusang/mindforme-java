import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Family from './family';
import FamilyDetail from './family-detail';
import FamilyUpdate from './family-update';
import FamilyDeleteDialog from './family-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FamilyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FamilyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FamilyDetail} />
      <ErrorBoundaryRoute path={match.url} component={Family} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FamilyDeleteDialog} />
  </>
);

export default Routes;
