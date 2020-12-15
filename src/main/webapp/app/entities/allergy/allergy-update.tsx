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
import { IChild } from 'app/shared/model/child.model';
import { getEntities as getChildren } from 'app/entities/child/child.reducer';
import { getEntity, updateEntity, createEntity, reset } from './allergy.reducer';
import { IAllergy } from 'app/shared/model/allergy.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAllergyUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AllergyUpdate = (props: IAllergyUpdateProps) => {
  const [petId, setPetId] = useState('0');
  const [childId, setChildId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { allergyEntity, pets, children, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/allergy' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPets();
    props.getChildren();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...allergyEntity,
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
          <h2 id="mindformeApp.allergy.home.createOrEditLabel">Create or edit a Allergy</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : allergyEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="allergy-id">ID</Label>
                  <AvInput id="allergy-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="allergy-name">
                  Name
                </Label>
                <AvField id="allergy-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="allergy-status">
                  Status
                </Label>
                <AvInput
                  id="allergy-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && allergyEntity.status) || 'Active'}
                >
                  <option value="Active">Active</option>
                  <option value="Inactive">Inactive</option>
                  <option value="Pending">Pending</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/allergy" replace color="info">
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
  children: storeState.child.entities,
  allergyEntity: storeState.allergy.entity,
  loading: storeState.allergy.loading,
  updating: storeState.allergy.updating,
  updateSuccess: storeState.allergy.updateSuccess,
});

const mapDispatchToProps = {
  getPets,
  getChildren,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AllergyUpdate);
