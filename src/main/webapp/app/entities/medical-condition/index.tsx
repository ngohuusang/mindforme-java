import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MedicalCondition from './medical-condition';
import MedicalConditionDetail from './medical-condition-detail';
import MedicalConditionUpdate from './medical-condition-update';
import MedicalConditionDeleteDialog from './medical-condition-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MedicalConditionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MedicalConditionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MedicalConditionDetail} />
      <ErrorBoundaryRoute path={match.url} component={MedicalCondition} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MedicalConditionDeleteDialog} />
  </>
);

export default Routes;
