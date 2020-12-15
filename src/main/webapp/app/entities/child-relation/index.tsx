import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ChildRelation from './child-relation';
import ChildRelationDetail from './child-relation-detail';
import ChildRelationUpdate from './child-relation-update';
import ChildRelationDeleteDialog from './child-relation-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ChildRelationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ChildRelationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ChildRelationDetail} />
      <ErrorBoundaryRoute path={match.url} component={ChildRelation} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ChildRelationDeleteDialog} />
  </>
);

export default Routes;
