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
import { IHelpRequest } from 'app/shared/model/help-request.model';
import { getEntities as getHelpRequests } from 'app/entities/help-request/help-request.reducer';
import { getEntity, updateEntity, createEntity, reset } from './pet-help-request.reducer';
import { IPetHelpRequest } from 'app/shared/model/pet-help-request.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPetHelpRequestUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PetHelpRequestUpdate = (props: IPetHelpRequestUpdateProps) => {
  const [idspet, setIdspet] = useState([]);
  const [requestId, setRequestId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { petHelpRequestEntity, pets, helpRequests, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/pet-help-request' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPets();
    props.getHelpRequests();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.dateFrom = convertDateTimeToServer(values.dateFrom);
    values.dateTo = convertDateTimeToServer(values.dateTo);

    if (errors.length === 0) {
      const entity = {
        ...petHelpRequestEntity,
        ...values,
        pets: mapIdList(values.pets),
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
          <h2 id="mindformeApp.petHelpRequest.home.createOrEditLabel">Create or edit a PetHelpRequest</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : petHelpRequestEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="pet-help-request-id">ID</Label>
                  <AvInput id="pet-help-request-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="totalHelpTimeLabel" for="pet-help-request-totalHelpTime">
                  Total Help Time
                </Label>
                <AvField id="pet-help-request-totalHelpTime" type="string" className="form-control" name="totalHelpTime" />
              </AvGroup>
              <AvGroup>
                <Label id="dateFromLabel" for="pet-help-request-dateFrom">
                  Date From
                </Label>
                <AvInput
                  id="pet-help-request-dateFrom"
                  type="datetime-local"
                  className="form-control"
                  name="dateFrom"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.petHelpRequestEntity.dateFrom)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateToLabel" for="pet-help-request-dateTo">
                  Date To
                </Label>
                <AvInput
                  id="pet-help-request-dateTo"
                  type="datetime-local"
                  className="form-control"
                  name="dateTo"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.petHelpRequestEntity.dateTo)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="timeFromLabel" for="pet-help-request-timeFrom">
                  Time From
                </Label>
                <AvField
                  id="pet-help-request-timeFrom"
                  type="text"
                  name="timeFrom"
                  validate={{
                    maxLength: { value: 10, errorMessage: 'This field cannot be longer than 10 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="timeToLabel" for="pet-help-request-timeTo">
                  Time To
                </Label>
                <AvField
                  id="pet-help-request-timeTo"
                  type="text"
                  name="timeTo"
                  validate={{
                    maxLength: { value: 10, errorMessage: 'This field cannot be longer than 10 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="contentLabel" for="pet-help-request-content">
                  Content
                </Label>
                <AvField
                  id="pet-help-request-content"
                  type="text"
                  name="content"
                  validate={{
                    maxLength: { value: 255, errorMessage: 'This field cannot be longer than 255 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="pet-help-request-pet">Pet</Label>
                <AvInput
                  id="pet-help-request-pet"
                  type="select"
                  multiple
                  className="form-control"
                  name="pets"
                  value={petHelpRequestEntity.pets && petHelpRequestEntity.pets.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {pets
                    ? pets.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="pet-help-request-request">Request</Label>
                <AvInput id="pet-help-request-request" type="select" className="form-control" name="requestId">
                  <option value="" key="0" />
                  {helpRequests
                    ? helpRequests.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/pet-help-request" replace color="info">
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
  helpRequests: storeState.helpRequest.entities,
  petHelpRequestEntity: storeState.petHelpRequest.entity,
  loading: storeState.petHelpRequest.loading,
  updating: storeState.petHelpRequest.updating,
  updateSuccess: storeState.petHelpRequest.updateSuccess,
});

const mapDispatchToProps = {
  getPets,
  getHelpRequests,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PetHelpRequestUpdate);
