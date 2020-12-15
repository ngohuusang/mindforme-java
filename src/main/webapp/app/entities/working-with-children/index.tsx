import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import WorkingWithChildren from './working-with-children';
import WorkingWithChildrenDetail from './working-with-children-detail';
import WorkingWithChildrenUpdate from './working-with-children-update';
import WorkingWithChildrenDeleteDialog from './working-with-children-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WorkingWithChildrenUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WorkingWithChildrenUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WorkingWithChildrenDetail} />
      <ErrorBoundaryRoute path={match.url} component={WorkingWithChildren} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={WorkingWithChildrenDeleteDialog} />
  </>
);

export default Routes;
