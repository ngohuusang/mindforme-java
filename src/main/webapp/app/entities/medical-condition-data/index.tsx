import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MedicalConditionData from './medical-condition-data';
import MedicalConditionDataDetail from './medical-condition-data-detail';
import MedicalConditionDataUpdate from './medical-condition-data-update';
import MedicalConditionDataDeleteDialog from './medical-condition-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MedicalConditionDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MedicalConditionDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MedicalConditionDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={MedicalConditionData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MedicalConditionDataDeleteDialog} />
  </>
);

export default Routes;
