import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AllergyData from './allergy-data';
import AllergyDataDetail from './allergy-data-detail';
import AllergyDataUpdate from './allergy-data-update';
import AllergyDataDeleteDialog from './allergy-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AllergyDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AllergyDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AllergyDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={AllergyData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AllergyDataDeleteDialog} />
  </>
);

export default Routes;
