import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISupported } from 'app/shared/model/supported.model';
import { getEntities as getSupporteds } from 'app/entities/supported/supported.reducer';
import { IHelpRequest } from 'app/shared/model/help-request.model';
import { getEntities as getHelpRequests } from 'app/entities/help-request/help-request.reducer';
import { getEntity, updateEntity, createEntity, reset } from './supported-help-request.reducer';
import { ISupportedHelpRequest } from 'app/shared/model/supported-help-request.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISupportedHelpRequestUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SupportedHelpRequestUpdate = (props: ISupportedHelpRequestUpdateProps) => {
  const [idssupported, setIdssupported] = useState([]);
  const [requestId, setRequestId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { supportedHelpRequestEntity, supporteds, helpRequests, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/supported-help-request' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getSupporteds();
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
        ...supportedHelpRequestEntity,
        ...values,
        supporteds: mapIdList(values.supporteds),
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
          <h2 id="mindformeApp.supportedHelpRequest.home.createOrEditLabel">Create or edit a SupportedHelpRequest</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : supportedHelpRequestEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="supported-help-request-id">ID</Label>
                  <AvInput id="supported-help-request-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="totalHelpTimeLabel" for="supported-help-request-totalHelpTime">
                  Total Help Time
                </Label>
                <AvField id="supported-help-request-totalHelpTime" type="string" className="form-control" name="totalHelpTime" />
              </AvGroup>
              <AvGroup>
                <Label id="dateFromLabel" for="supported-help-request-dateFrom">
                  Date From
                </Label>
                <AvInput
                  id="supported-help-request-dateFrom"
                  type="datetime-local"
                  className="form-control"
                  name="dateFrom"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.supportedHelpRequestEntity.dateFrom)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateToLabel" for="supported-help-request-dateTo">
                  Date To
                </Label>
                <AvInput
                  id="supported-help-request-dateTo"
                  type="datetime-local"
                  className="form-control"
                  name="dateTo"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.supportedHelpRequestEntity.dateTo)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="timeFromLabel" for="supported-help-request-timeFrom">
                  Time From
                </Label>
                <AvField
                  id="supported-help-request-timeFrom"
                  type="text"
                  name="timeFrom"
                  validate={{
                    maxLength: { value: 10, errorMessage: 'This field cannot be longer than 10 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="timeToLabel" for="supported-help-request-timeTo">
                  Time To
                </Label>
                <AvField
                  id="supported-help-request-timeTo"
                  type="text"
                  name="timeTo"
                  validate={{
                    maxLength: { value: 10, errorMessage: 'This field cannot be longer than 10 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="timeTypeLabel" for="supported-help-request-timeType">
                  Time Type
                </Label>
                <AvInput
                  id="supported-help-request-timeType"
                  type="select"
                  className="form-control"
                  name="timeType"
                  value={(!isNew && supportedHelpRequestEntity.timeType) || 'HOUR'}
                >
                  <option value="HOUR">HOUR</option>
                  <option value="SPECIFIC_TIME">SPECIFIC_TIME</option>
                </AvInput>
                <UncontrolledTooltip target="timeTypeLabel">1= hours, 2= specific time</UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="supportedHelpTypeLabel" for="supported-help-request-supportedHelpType">
                  Supported Help Type
                </Label>
                <AvInput
                  id="supported-help-request-supportedHelpType"
                  type="select"
                  className="form-control"
                  name="supportedHelpType"
                  value={(!isNew && supportedHelpRequestEntity.supportedHelpType) || 'WITH_ERRAN'}
                >
                  <option value="WITH_ERRAN">WITH_ERRAN</option>
                  <option value="AT_HOME">AT_HOME</option>
                  <option value="COMPANY">COMPANY</option>
                  <option value="OTHER">OTHER</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="contentLabel" for="supported-help-request-content">
                  Content
                </Label>
                <AvField id="supported-help-request-content" type="text" name="content" />
              </AvGroup>
              <AvGroup>
                <Label id="otherTaskLabel" for="supported-help-request-otherTask">
                  Other Task
                </Label>
                <AvField id="supported-help-request-otherTask" type="text" name="otherTask" />
              </AvGroup>
              <AvGroup>
                <Label for="supported-help-request-supported">Supported</Label>
                <AvInput
                  id="supported-help-request-supported"
                  type="select"
                  multiple
                  className="form-control"
                  name="supporteds"
                  value={supportedHelpRequestEntity.supporteds && supportedHelpRequestEntity.supporteds.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {supporteds
                    ? supporteds.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.firstName}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="supported-help-request-request">Request</Label>
                <AvInput id="supported-help-request-request" type="select" className="form-control" name="requestId">
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
              <Button tag={Link} id="cancel-save" to="/supported-help-request" replace color="info">
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
  supporteds: storeState.supported.entities,
  helpRequests: storeState.helpRequest.entities,
  supportedHelpRequestEntity: storeState.supportedHelpRequest.entity,
  loading: storeState.supportedHelpRequest.loading,
  updating: storeState.supportedHelpRequest.updating,
  updateSuccess: storeState.supportedHelpRequest.updateSuccess,
});

const mapDispatchToProps = {
  getSupporteds,
  getHelpRequests,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SupportedHelpRequestUpdate);
