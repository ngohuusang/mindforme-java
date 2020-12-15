import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPet } from 'app/shared/model/pet.model';
import { getEntities as getPets } from 'app/entities/pet/pet.reducer';
import { getEntity, updateEntity, createEntity, reset } from './walking-other.reducer';
import { IWalkingOther } from 'app/shared/model/walking-other.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWalkingOtherUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WalkingOtherUpdate = (props: IWalkingOtherUpdateProps) => {
  const [petId, setPetId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { walkingOtherEntity, pets, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/walking-other' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPets();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...walkingOtherEntity,
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
          <h2 id="mindformeApp.walkingOther.home.createOrEditLabel">Create or edit a WalkingOther</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : walkingOtherEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="walking-other-id">ID</Label>
                  <AvInput id="walking-other-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="walking-other-name">
                  Name
                </Label>
                <AvField id="walking-other-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="walking-other-status">
                  Status
                </Label>
                <AvInput
                  id="walking-other-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && walkingOtherEntity.status) || 'Active'}
                >
                  <option value="Active">Active</option>
                  <option value="Inactive">Inactive</option>
                  <option value="Pending">Pending</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/walking-other" replace color="info">
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
  pets: storeState.pet.entities,
  walkingOtherEntity: storeState.walkingOther.entity,
  loading: storeState.walkingOther.loading,
  updating: storeState.walkingOther.updating,
  updateSuccess: storeState.walkingOther.updateSuccess,
});

const mapDispatchToProps = {
  getPets,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WalkingOtherUpdate);
