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
import { getEntity, updateEntity, createEntity, reset } from './city.reducer';
import { ICity } from 'app/shared/model/city.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICityUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CityUpdate = (props: ICityUpdateProps) => {
  const [stateId, setStateId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { cityEntity, states, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/city' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getStates();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...cityEntity,
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
          <h2 id="mindformeApp.city.home.createOrEditLabel">Create or edit a City</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : cityEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="city-id">ID</Label>
                  <AvInput id="city-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="city-name">
                  Name
                </Label>
                <AvField id="city-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="latitudeLabel" for="city-latitude">
                  Latitude
                </Label>
                <AvField id="city-latitude" type="text" name="latitude" />
              </AvGroup>
              <AvGroup>
                <Label id="longitudeLabel" for="city-longitude">
                  Longitude
                </Label>
                <AvField id="city-longitude" type="text" name="longitude" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="city-status">
                  Status
                </Label>
                <AvInput
                  id="city-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && cityEntity.status) || 'Active'}
                >
                  <option value="Active">Active</option>
                  <option value="Inactive">Inactive</option>
                  <option value="Pending">Pending</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="city-state">State</Label>
                <AvInput id="city-state" type="select" className="form-control" name="stateId">
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
              <Button tag={Link} id="cancel-save" to="/city" replace color="info">
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
  cityEntity: storeState.city.entity,
  loading: storeState.city.loading,
  updating: storeState.city.updating,
  updateSuccess: storeState.city.updateSuccess,
});

const mapDispatchToProps = {
  getStates,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CityUpdate);
