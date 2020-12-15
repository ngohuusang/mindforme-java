import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FamilyGallery from './family-gallery';
import FamilyGalleryDetail from './family-gallery-detail';
import FamilyGalleryUpdate from './family-gallery-update';
import FamilyGalleryDeleteDialog from './family-gallery-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FamilyGalleryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FamilyGalleryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FamilyGalleryDetail} />
      <ErrorBoundaryRoute path={match.url} component={FamilyGallery} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FamilyGalleryDeleteDialog} />
  </>
);

export default Routes;
