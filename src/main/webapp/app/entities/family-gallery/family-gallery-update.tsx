import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IFamily } from 'app/shared/model/family.model';
import { getEntities as getFamilies } from 'app/entities/family/family.reducer';
import { getEntity, updateEntity, createEntity, reset } from './family-gallery.reducer';
import { IFamilyGallery } from 'app/shared/model/family-gallery.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFamilyGalleryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FamilyGalleryUpdate = (props: IFamilyGalleryUpdateProps) => {
  const [familyId, setFamilyId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { familyGalleryEntity, families, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/family-gallery' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getFamilies();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...familyGalleryEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mindformeApp.familyGallery.home.createOrEditLabel">Create or edit a FamilyGallery</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : familyGalleryEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="family-gallery-id">ID</Label>
                  <AvInput id="family-gallery-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="imageUrlLabel" for="family-gallery-imageUrl">
                  Image Url
                </Label>
                <AvField id="family-gallery-imageUrl" type="text" name="imageUrl" />
              </AvGroup>
              <AvGroup>
                <Label id="sortOrderLabel" for="family-gallery-sortOrder">
                  Sort Order
                </Label>
                <AvField id="family-gallery-sortOrder" type="string" className="form-control" name="sortOrder" />
              </AvGroup>
              <AvGroup check>
                <Label id="isDefaultLabel">
                  <AvInput id="family-gallery-isDefault" type="checkbox" className="form-check-input" name="isDefault" />
                  Is Default
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="privacyLabel" for="family-gallery-privacy">
                  Privacy
                </Label>
                <AvInput
                  id="family-gallery-privacy"
                  type="select"
                  className="form-control"
                  name="privacy"
                  value={(!isNew && familyGalleryEntity.privacy) || 'PUBLIC'}
                >
                  <option value="PUBLIC">PUBLIC</option>
                  <option value="FRIENDS">FRIENDS</option>
                  <option value="FRIENDS_OF_FRIENDS">FRIENDS_OF_FRIENDS</option>
                  <option value="GROUP">GROUP</option>
                  <option value="PRIVATE">PRIVATE</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="family-gallery-family">Family</Label>
                <AvInput id="family-gallery-family" type="select" className="form-control" name="familyId">
                  <option value="" key="0" />
                  {families
                    ? families.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/family-gallery" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  families: storeState.family.entities,
  familyGalleryEntity: storeState.familyGallery.entity,
  loading: storeState.familyGallery.loading,
  updating: storeState.familyGallery.updating,
  updateSuccess: storeState.familyGallery.updateSuccess,
});

const mapDispatchToProps = {
  getFamilies,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FamilyGalleryUpdate);
