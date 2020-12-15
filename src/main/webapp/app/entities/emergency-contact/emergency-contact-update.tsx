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
import { getEntity, updateEntity, createEntity, reset } from './emergency-contact.reducer';
import { IEmergencyContact } from 'app/shared/model/emergency-contact.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmergencyContactUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmergencyContactUpdate = (props: IEmergencyContactUpdateProps) => {
  const [addressId, setAddressId] = useState('0');
  const [familyId, setFamilyId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { emergencyContactEntity, addresses, families, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/emergency-contact' + props.location.search);
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
        ...emergencyContactEntity,
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
          <h2 id="mindformeApp.emergencyContact.home.createOrEditLabel">Create or edit a EmergencyContact</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : emergencyContactEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="emergency-contact-id">ID</Label>
                  <AvInput id="emergency-contact-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="emergency-contact-name">
                  Name
                </Label>
                <AvField
                  id="emergency-contact-name"
                  type="text"
                  name="name"
                  validate={{
                    maxLength: { value: 15, errorMessage: 'This field cannot be longer than 15 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="relationToYouLabel" for="emergency-contact-relationToYou">
                  Relation To You
                </Label>
                <AvField
                  id="emergency-contact-relationToYou"
                  type="text"
                  name="relationToYou"
                  validate={{
                    maxLength: { value: 150, errorMessage: 'This field cannot be longer than 150 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="phoneNumberLabel" for="emergency-contact-phoneNumber">
                  Phone Number
                </Label>
                <AvField
                  id="emergency-contact-phoneNumber"
                  type="text"
                  name="phoneNumber"
                  validate={{
                    maxLength: { value: 15, errorMessage: 'This field cannot be longer than 15 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="emergency-contact-email">
                  Email
                </Label>
                <AvField
                  id="emergency-contact-email"
                  type="text"
                  name="email"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    maxLength: { value: 100, errorMessage: 'This field cannot be longer than 100 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="emergency-contact-address">Address</Label>
                <AvInput id="emergency-contact-address" type="select" className="form-control" name="addressId">
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
                <Label for="emergency-contact-family">Family</Label>
                <AvInput id="emergency-contact-family" type="select" className="form-control" name="familyId">
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
              <Button tag={Link} id="cancel-save" to="/emergency-contact" replace color="info">
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
  emergencyContactEntity: storeState.emergencyContact.entity,
  loading: storeState.emergencyContact.loading,
  updating: storeState.emergencyContact.updating,
  updateSuccess: storeState.emergencyContact.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(EmergencyContactUpdate);
