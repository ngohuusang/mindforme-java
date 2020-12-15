import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PetBreed from './pet-breed';
import PetBreedDetail from './pet-breed-detail';
import PetBreedUpdate from './pet-breed-update';
import PetBreedDeleteDialog from './pet-breed-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PetBreedUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PetBreedUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PetBreedDetail} />
      <ErrorBoundaryRoute path={match.url} component={PetBreed} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PetBreedDeleteDialog} />
  </>
);

export default Routes;
