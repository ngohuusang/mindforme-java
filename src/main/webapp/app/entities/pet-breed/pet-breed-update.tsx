import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPetType } from 'app/shared/model/pet-type.model';
import { getEntities as getPetTypes } from 'app/entities/pet-type/pet-type.reducer';
import { getEntity, updateEntity, createEntity, reset } from './pet-breed.reducer';
import { IPetBreed } from 'app/shared/model/pet-breed.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPetBreedUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PetBreedUpdate = (props: IPetBreedUpdateProps) => {
  const [petTypeId, setPetTypeId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { petBreedEntity, petTypes, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/pet-breed' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPetTypes();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...petBreedEntity,
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
          <h2 id="mindformeApp.petBreed.home.createOrEditLabel">Create or edit a PetBreed</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : petBreedEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="pet-breed-id">ID</Label>
                  <AvInput id="pet-breed-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="pet-breed-name">
                  Name
                </Label>
                <AvField id="pet-breed-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="pet-breed-status">
                  Status
                </Label>
                <AvInput
                  id="pet-breed-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && petBreedEntity.status) || 'Active'}
                >
                  <option value="Active">Active</option>
                  <option value="Inactive">Inactive</option>
                  <option value="Pending">Pending</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="pet-breed-petType">Pet Type</Label>
                <AvInput id="pet-breed-petType" type="select" className="form-control" name="petTypeId">
                  <option value="" key="0" />
                  {petTypes
                    ? petTypes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/pet-breed" replace color="info">
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
  petTypes: storeState.petType.entities,
  petBreedEntity: storeState.petBreed.entity,
  loading: storeState.petBreed.loading,
  updating: storeState.petBreed.updating,
  updateSuccess: storeState.petBreed.updateSuccess,
});

const mapDispatchToProps = {
  getPetTypes,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PetBreedUpdate);
