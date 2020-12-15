import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ChildRelationData from './child-relation-data';
import ChildRelationDataDetail from './child-relation-data-detail';
import ChildRelationDataUpdate from './child-relation-data-update';
import ChildRelationDataDeleteDialog from './child-relation-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ChildRelationDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ChildRelationDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ChildRelationDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={ChildRelationData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ChildRelationDataDeleteDialog} />
  </>
);

export default Routes;
