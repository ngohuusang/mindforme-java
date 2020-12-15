import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SupportedRelationData from './supported-relation-data';
import SupportedRelationDataDetail from './supported-relation-data-detail';
import SupportedRelationDataUpdate from './supported-relation-data-update';
import SupportedRelationDataDeleteDialog from './supported-relation-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SupportedRelationDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SupportedRelationDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SupportedRelationDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={SupportedRelationData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SupportedRelationDataDeleteDialog} />
  </>
);

export default Routes;
