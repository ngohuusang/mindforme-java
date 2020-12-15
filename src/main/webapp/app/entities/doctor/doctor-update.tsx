import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAddress } from 'app/shared/model/address.model';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { IFamily } from 'app/shared/model/family.model';
import { getEntities as getFamilies } from 'app/entities/family/family.reducer';
import { getEntity, updateEntity, createEntity, reset } from './doctor.reducer';
import { IDoctor } from 'app/shared/model/doctor.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDoctorUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DoctorUpdate = (props: IDoctorUpdateProps) => {
  const [addressId, setAddressId] = useState('0');
  const [familyId, setFamilyId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { doctorEntity, addresses, families, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/doctor' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getAddresses();
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
        ...doctorEntity,
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
          <h2 id="mindformeApp.doctor.home.createOrEditLabel">Create or edit a Doctor</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : doctorEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="doctor-id">ID</Label>
                  <AvInput id="doctor-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="doctorNameLabel" for="doctor-doctorName">
                  Doctor Name
                </Label>
                <AvField
                  id="doctor-doctorName"
                  type="text"
                  name="doctorName"
                  validate={{
                    maxLength: { value: 15, errorMessage: 'This field cannot be longer than 15 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="phoneNumberLabel" for="doctor-phoneNumber">
                  Phone Number
                </Label>
                <AvField
                  id="doctor-phoneNumber"
                  type="text"
                  name="phoneNumber"
                  validate={{
                    maxLength: { value: 15, errorMessage: 'This field cannot be longer than 15 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="doctor-email">
                  Email
                </Label>
                <AvField
                  id="doctor-email"
                  type="text"
                  name="email"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    maxLength: { value: 100, errorMessage: 'This field cannot be longer than 100 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="medicalPracticeLabel" for="doctor-medicalPractice">
                  Medical Practice
                </Label>
                <AvField
                  id="doctor-medicalPractice"
                  type="text"
                  name="medicalPractice"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    maxLength: { value: 100, errorMessage: 'This field cannot be longer than 100 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="doctor-address">Address</Label>
                <AvInput id="doctor-address" type="select" className="form-control" name="addressId">
                  <option value="" key="0" />
                  {addresses
                    ? addresses.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.address}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="doctor-family">Family</Label>
                <AvInput id="doctor-family" type="select" className="form-control" name="familyId">
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
              <Button tag={Link} id="cancel-save" to="/doctor" replace color="info">
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
  addresses: storeState.address.entities,
  families: storeState.family.entities,
  doctorEntity: storeState.doctor.entity,
  loading: storeState.doctor.loading,
  updating: storeState.doctor.updating,
  updateSuccess: storeState.doctor.updateSuccess,
});

const mapDispatchToProps = {
  getAddresses,
  getFamilies,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DoctorUpdate);
