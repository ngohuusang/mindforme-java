import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PetBreedData from './pet-breed-data';
import PetBreedDataDetail from './pet-breed-data-detail';
import PetBreedDataUpdate from './pet-breed-data-update';
import PetBreedDataDeleteDialog from './pet-breed-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PetBreedDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PetBreedDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PetBreedDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={PetBreedData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PetBreedDataDeleteDialog} />
  </>
);

export default Routes;
