import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Allergy from './allergy';
import AllergyDetail from './allergy-detail';
import AllergyUpdate from './allergy-update';
import AllergyDeleteDialog from './allergy-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AllergyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AllergyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AllergyDetail} />
      <ErrorBoundaryRoute path={match.url} component={Allergy} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AllergyDeleteDialog} />
  </>
);

export default Routes;
