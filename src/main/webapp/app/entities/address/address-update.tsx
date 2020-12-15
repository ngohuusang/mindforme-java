import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IState } from 'app/shared/model/state.model';
import { getEntities as getStates } from 'app/entities/state/state.reducer';
import { ICountry } from 'app/shared/model/country.model';
import { getEntities as getCountries } from 'app/entities/country/country.reducer';
import { ICity } from 'app/shared/model/city.model';
import { getEntities as getCities } from 'app/entities/city/city.reducer';
import { getEntity, updateEntity, createEntity, reset } from './address.reducer';
import { IAddress } from 'app/shared/model/address.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAddressUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AddressUpdate = (props: IAddressUpdateProps) => {
  const [stateId, setStateId] = useState('0');
  const [countryId, setCountryId] = useState('0');
  const [cityId, setCityId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { addressEntity, states, countries, cities, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/address' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getStates();
    props.getCountries();
    props.getCities();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...addressEntity,
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
          <h2 id="mindformeApp.address.home.createOrEditLabel">Create or edit a Address</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : addressEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="address-id">ID</Label>
                  <AvInput id="address-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="addressLabel" for="address-address">
                  Address
                </Label>
                <AvField id="address-address" type="text" name="address" />
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="address-name">
                  Name
                </Label>
                <AvField id="address-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="latitudeLabel" for="address-latitude">
                  Latitude
                </Label>
                <AvField id="address-latitude" type="text" name="latitude" />
              </AvGroup>
              <AvGroup>
                <Label id="longitudeLabel" for="address-longitude">
                  Longitude
                </Label>
                <AvField id="address-longitude" type="text" name="longitude" />
              </AvGroup>
              <AvGroup>
                <Label id="lineLabel" for="address-line">
                  Line
                </Label>
                <AvField id="address-line" type="text" name="line" />
              </AvGroup>
              <AvGroup>
                <Label id="unitLabel" for="address-unit">
                  Unit
                </Label>
                <AvField id="address-unit" type="text" name="unit" />
              </AvGroup>
              <AvGroup>
                <Label id="numberLabel" for="address-number">
                  Number
                </Label>
                <AvField id="address-number" type="text" name="number" />
              </AvGroup>
              <AvGroup>
                <Label id="streetLabel" for="address-street">
                  Street
                </Label>
                <AvField id="address-street" type="text" name="street" />
              </AvGroup>
              <AvGroup>
                <Label id="postcodeLabel" for="address-postcode">
                  Postcode
                </Label>
                <AvField id="address-postcode" type="text" name="postcode" />
              </AvGroup>
              <AvGroup>
                <Label id="suburbLabel" for="address-suburb">
                  Suburb
                </Label>
                <AvField id="address-suburb" type="text" name="suburb" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="address-status">
                  Status
                </Label>
                <AvInput
                  id="address-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && addressEntity.status) || 'Active'}
                >
                  <option value="Active">Active</option>
                  <option value="Inactive">Inactive</option>
                  <option value="Pending">Pending</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="address-state">State</Label>
                <AvInput id="address-state" type="select" className="form-control" name="stateId">
                  <option value="" key="0" />
                  {states
                    ? states.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="address-country">Country</Label>
                <AvInput id="address-country" type="select" className="form-control" name="countryId">
                  <option value="" key="0" />
                  {countries
                    ? countries.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="address-city">City</Label>
                <AvInput id="address-city" type="select" className="form-control" name="cityId">
                  <option value="" key="0" />
                  {cities
                    ? cities.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/address" replace color="info">
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
  states: storeState.state.entities,
  countries: storeState.country.entities,
  cities: storeState.city.entities,
  addressEntity: storeState.address.entity,
  loading: storeState.address.loading,
  updating: storeState.address.updating,
  updateSuccess: storeState.address.updateSuccess,
});

const mapDispatchToProps = {
  getStates,
  getCountries,
  getCities,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AddressUpdate);
