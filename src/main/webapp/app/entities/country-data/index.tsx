import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CountryData from './country-data';
import CountryDataDetail from './country-data-detail';
import CountryDataUpdate from './country-data-update';
import CountryDataDeleteDialog from './country-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CountryDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CountryDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CountryDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={CountryData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CountryDataDeleteDialog} />
  </>
);

export default Routes;
