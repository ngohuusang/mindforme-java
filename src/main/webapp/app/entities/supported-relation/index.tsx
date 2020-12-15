import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SupportedRelation from './supported-relation';
import SupportedRelationDetail from './supported-relation-detail';
import SupportedRelationUpdate from './supported-relation-update';
import SupportedRelationDeleteDialog from './supported-relation-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SupportedRelationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SupportedRelationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SupportedRelationDetail} />
      <ErrorBoundaryRoute path={match.url} component={SupportedRelation} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SupportedRelationDeleteDialog} />
  </>
);

export default Routes;
