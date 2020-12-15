import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FavouriteFood from './favourite-food';
import FavouriteFoodDetail from './favourite-food-detail';
import FavouriteFoodUpdate from './favourite-food-update';
import FavouriteFoodDeleteDialog from './favourite-food-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FavouriteFoodUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FavouriteFoodUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FavouriteFoodDetail} />
      <ErrorBoundaryRoute path={match.url} component={FavouriteFood} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FavouriteFoodDeleteDialog} />
  </>
);

export default Routes;
