import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IHelpRequest } from 'app/shared/model/help-request.model';
import { getEntities as getHelpRequests } from 'app/entities/help-request/help-request.reducer';
import { getEntity, updateEntity, createEntity, reset } from './garden-help-request.reducer';
import { IGardenHelpRequest } from 'app/shared/model/garden-help-request.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGardenHelpRequestUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GardenHelpRequestUpdate = (props: IGardenHelpRequestUpdateProps) => {
  const [requestId, setRequestId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { gardenHelpRequestEntity, helpRequests, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/garden-help-request' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

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
        ...gardenHelpRequestEntity,
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
          <h2 id="mindformeApp.gardenHelpRequest.home.createOrEditLabel">Create or edit a GardenHelpRequest</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : gardenHelpRequestEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="garden-help-request-id">ID</Label>
                  <AvInput id="garden-help-request-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="totalHelpTimeLabel" for="garden-help-request-totalHelpTime">
                  Total Help Time
                </Label>
                <AvField id="garden-help-request-totalHelpTime" type="string" className="form-control" name="totalHelpTime" />
              </AvGroup>
              <AvGroup>
                <Label id="dateFromLabel" for="garden-help-request-dateFrom">
                  Date From
                </Label>
                <AvInput
                  id="garden-help-request-dateFrom"
                  type="datetime-local"
                  className="form-control"
                  name="dateFrom"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.gardenHelpRequestEntity.dateFrom)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateToLabel" for="garden-help-request-dateTo">
                  Date To
                </Label>
                <AvInput
                  id="garden-help-request-dateTo"
                  type="datetime-local"
                  className="form-control"
                  name="dateTo"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.gardenHelpRequestEntity.dateTo)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="timeFromLabel" for="garden-help-request-timeFrom">
                  Time From
                </Label>
                <AvField
                  id="garden-help-request-timeFrom"
                  type="text"
                  name="timeFrom"
                  validate={{
                    maxLength: { value: 10, errorMessage: 'This field cannot be longer than 10 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="timeToLabel" for="garden-help-request-timeTo">
                  Time To
                </Label>
                <AvField
                  id="garden-help-request-timeTo"
                  type="text"
                  name="timeTo"
                  validate={{
                    maxLength: { value: 10, errorMessage: 'This field cannot be longer than 10 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="servicesLabel" for="garden-help-request-services">
                  Services
                </Label>
                <AvField id="garden-help-request-services" type="text" name="services" />
              </AvGroup>
              <AvGroup check>
                <Label id="edgeTrimmingLabel">
                  <AvInput id="garden-help-request-edgeTrimming" type="checkbox" className="form-check-input" name="edgeTrimming" />
                  Edge Trimming
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="mowingTimeLabel" for="garden-help-request-mowingTime">
                  Mowing Time
                </Label>
                <AvField id="garden-help-request-mowingTime" type="text" name="mowingTime" />
              </AvGroup>
              <AvGroup>
                <Label id="mowingEquipmentLabel" for="garden-help-request-mowingEquipment">
                  Mowing Equipment
                </Label>
                <AvInput
                  id="garden-help-request-mowingEquipment"
                  type="select"
                  className="form-control"
                  name="mowingEquipment"
                  value={(!isNew && gardenHelpRequestEntity.mowingEquipment) || 'OURS'}
                >
                  <option value="OURS">OURS</option>
                  <option value="YOURS">YOURS</option>
                  <option value="EITHER">EITHER</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="lawnTimeLabel" for="garden-help-request-lawnTime">
                  Lawn Time
                </Label>
                <AvField id="garden-help-request-lawnTime" type="text" name="lawnTime" />
              </AvGroup>
              <AvGroup>
                <Label id="lawnEquipmentLabel" for="garden-help-request-lawnEquipment">
                  Lawn Equipment
                </Label>
                <AvInput
                  id="garden-help-request-lawnEquipment"
                  type="select"
                  className="form-control"
                  name="lawnEquipment"
                  value={(!isNew && gardenHelpRequestEntity.lawnEquipment) || 'OURS'}
                >
                  <option value="OURS">OURS</option>
                  <option value="YOURS">YOURS</option>
                  <option value="EITHER">EITHER</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="rubbishLoadLabel" for="garden-help-request-rubbishLoad">
                  Rubbish Load
                </Label>
                <AvField id="garden-help-request-rubbishLoad" type="string" className="form-control" name="rubbishLoad" />
              </AvGroup>
              <AvGroup>
                <Label id="rubbishLoadTypeLabel" for="garden-help-request-rubbishLoadType">
                  Rubbish Load Type
                </Label>
                <AvInput
                  id="garden-help-request-rubbishLoadType"
                  type="select"
                  className="form-control"
                  name="rubbishLoadType"
                  value={(!isNew && gardenHelpRequestEntity.rubbishLoadType) || 'UTE'}
                >
                  <option value="UTE">UTE</option>
                  <option value="VAN">VAN</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="otherDescriptionLabel" for="garden-help-request-otherDescription">
                  Other Description
                </Label>
                <AvField id="garden-help-request-otherDescription" type="text" name="otherDescription" />
              </AvGroup>
              <AvGroup>
                <Label id="otherHoursLabel" for="garden-help-request-otherHours">
                  Other Hours
                </Label>
                <AvField
                  id="garden-help-request-otherHours"
                  type="text"
                  name="otherHours"
                  validate={{
                    maxLength: { value: 45, errorMessage: 'This field cannot be longer than 45 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="otherEquipmentLabel" for="garden-help-request-otherEquipment">
                  Other Equipment
                </Label>
                <AvInput
                  id="garden-help-request-otherEquipment"
                  type="select"
                  className="form-control"
                  name="otherEquipment"
                  value={(!isNew && gardenHelpRequestEntity.otherEquipment) || 'OURS'}
                >
                  <option value="OURS">OURS</option>
                  <option value="YOURS">YOURS</option>
                  <option value="EITHER">EITHER</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="provideTypeLabel" for="garden-help-request-provideType">
                  Provide Type
                </Label>
                <AvInput
                  id="garden-help-request-provideType"
                  type="select"
                  className="form-control"
                  name="provideType"
                  value={(!isNew && gardenHelpRequestEntity.provideType) || 'FAMILY'}
                >
                  <option value="FAMILY">FAMILY</option>
                  <option value="SUPPORTED">SUPPORTED</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="provideForLabel" for="garden-help-request-provideFor">
                  Provide For
                </Label>
                <AvField id="garden-help-request-provideFor" type="string" className="form-control" name="provideFor" />
              </AvGroup>
              <AvGroup>
                <Label for="garden-help-request-request">Request</Label>
                <AvInput id="garden-help-request-request" type="select" className="form-control" name="requestId">
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
              <Button tag={Link} id="cancel-save" to="/garden-help-request" replace color="info">
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
  helpRequests: storeState.helpRequest.entities,
  gardenHelpRequestEntity: storeState.gardenHelpRequest.entity,
  loading: storeState.gardenHelpRequest.loading,
  updating: storeState.gardenHelpRequest.updating,
  updateSuccess: storeState.gardenHelpRequest.updateSuccess,
});

const mapDispatchToProps = {
  getHelpRequests,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GardenHelpRequestUpdate);
