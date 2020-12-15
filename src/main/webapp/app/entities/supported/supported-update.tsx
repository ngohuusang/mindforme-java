import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISupportedRelation } from 'app/shared/model/supported-relation.model';
import { getEntities as getSupportedRelations } from 'app/entities/supported-relation/supported-relation.reducer';
import { IFamily } from 'app/shared/model/family.model';
import { getEntities as getFamilies } from 'app/entities/family/family.reducer';
import { ISupportedHelpRequest } from 'app/shared/model/supported-help-request.model';
import { getEntities as getSupportedHelpRequests } from 'app/entities/supported-help-request/supported-help-request.reducer';
import { getEntity, updateEntity, createEntity, reset } from './supported.reducer';
import { ISupported } from 'app/shared/model/supported.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISupportedUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SupportedUpdate = (props: ISupportedUpdateProps) => {
  const [relationId, setRelationId] = useState('0');
  const [familyId, setFamilyId] = useState('0');
  const [helpRequestId, setHelpRequestId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { supportedEntity, supportedRelations, families, supportedHelpRequests, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/supported' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getSupportedRelations();
    props.getFamilies();
    props.getSupportedHelpRequests();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.birthday = convertDateTimeToServer(values.birthday);

    if (errors.length === 0) {
      const entity = {
        ...supportedEntity,
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
          <h2 id="mindformeApp.supported.home.createOrEditLabel">Create or edit a Supported</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : supportedEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="supported-id">ID</Label>
                  <AvInput id="supported-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="firstNameLabel" for="supported-firstName">
                  First Name
                </Label>
                <AvField id="supported-firstName" type="text" name="firstName" />
              </AvGroup>
              <AvGroup>
                <Label id="lastNameLabel" for="supported-lastName">
                  Last Name
                </Label>
                <AvField id="supported-lastName" type="text" name="lastName" />
              </AvGroup>
              <AvGroup>
                <Label id="imageUrlLabel" for="supported-imageUrl">
                  Image Url
                </Label>
                <AvField id="supported-imageUrl" type="text" name="imageUrl" />
              </AvGroup>
              <AvGroup>
                <Label id="birthdayLabel" for="supported-birthday">
                  Birthday
                </Label>
                <AvInput
                  id="supported-birthday"
                  type="datetime-local"
                  className="form-control"
                  name="birthday"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.supportedEntity.birthday)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="supported-status">
                  Status
                </Label>
                <AvInput
                  id="supported-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && supportedEntity.status) || 'Active'}
                >
                  <option value="Active">Active</option>
                  <option value="Inactive">Inactive</option>
                  <option value="Pending">Pending</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="supported-relation">Relation</Label>
                <AvInput id="supported-relation" type="select" className="form-control" name="relationId">
                  <option value="" key="0" />
                  {supportedRelations
                    ? supportedRelations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="supported-family">Family</Label>
                <AvInput id="supported-family" type="select" className="form-control" name="familyId">
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
              <Button tag={Link} id="cancel-save" to="/supported" replace color="info">
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
  supportedRelations: storeState.supportedRelation.entities,
  families: storeState.family.entities,
  supportedHelpRequests: storeState.supportedHelpRequest.entities,
  supportedEntity: storeState.supported.entity,
  loading: storeState.supported.loading,
  updating: storeState.supported.updating,
  updateSuccess: storeState.supported.updateSuccess,
});

const mapDispatchToProps = {
  getSupportedRelations,
  getFamilies,
  getSupportedHelpRequests,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SupportedUpdate);
