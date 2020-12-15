import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CityData from './city-data';
import CityDataDetail from './city-data-detail';
import CityDataUpdate from './city-data-update';
import CityDataDeleteDialog from './city-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CityDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CityDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CityDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={CityData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CityDataDeleteDialog} />
  </>
);

export default Routes;
