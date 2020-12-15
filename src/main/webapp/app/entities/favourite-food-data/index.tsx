import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FavouriteFoodData from './favourite-food-data';
import FavouriteFoodDataDetail from './favourite-food-data-detail';
import FavouriteFoodDataUpdate from './favourite-food-data-update';
import FavouriteFoodDataDeleteDialog from './favourite-food-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FavouriteFoodDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FavouriteFoodDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FavouriteFoodDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={FavouriteFoodData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FavouriteFoodDataDeleteDialog} />
  </>
);

export default Routes;
