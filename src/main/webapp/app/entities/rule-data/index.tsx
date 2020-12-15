import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RuleData from './rule-data';
import RuleDataDetail from './rule-data-detail';
import RuleDataUpdate from './rule-data-update';
import RuleDataDeleteDialog from './rule-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RuleDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RuleDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RuleDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={RuleData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RuleDataDeleteDialog} />
  </>
);

export default Routes;
