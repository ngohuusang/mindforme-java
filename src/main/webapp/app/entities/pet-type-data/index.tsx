import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PetTypeData from './pet-type-data';
import PetTypeDataDetail from './pet-type-data-detail';
import PetTypeDataUpdate from './pet-type-data-update';
import PetTypeDataDeleteDialog from './pet-type-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PetTypeDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PetTypeDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PetTypeDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={PetTypeData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PetTypeDataDeleteDialog} />
  </>
);

export default Routes;
