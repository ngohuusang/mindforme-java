import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IHelpRequest } from 'app/shared/model/help-request.model';
import { getEntities as getHelpRequests } from 'app/entities/help-request/help-request.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './house-help-request.reducer';
import { IHouseHelpRequest } from 'app/shared/model/house-help-request.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IHouseHelpRequestUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HouseHelpRequestUpdate = (props: IHouseHelpRequestUpdateProps) => {
  const [requestId, setRequestId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { houseHelpRequestEntity, helpRequests, loading, updating } = props;

  const { services, houseMindingDetail } = houseHelpRequestEntity;

  const handleClose = () => {
    props.history.push('/house-help-request' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getHelpRequests();
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.cleaningFromTime = convertDateTimeToServer(values.cleaningFromTime);
    values.cleaningToTime = convertDateTimeToServer(values.cleaningToTime);
    values.cookingFromTime = convertDateTimeToServer(values.cookingFromTime);
    values.cookingToTime = convertDateTimeToServer(values.cookingToTime);
    values.mailFromDate = convertDateTimeToServer(values.mailFromDate);
    values.mailToDate = convertDateTimeToServer(values.mailToDate);
    values.otherFromTime = convertDateTimeToServer(values.otherFromTime);
    values.otherToTime = convertDateTimeToServer(values.otherToTime);

    if (errors.length === 0) {
      const entity = {
        ...houseHelpRequestEntity,
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
          <h2 id="mindformeApp.houseHelpRequest.home.createOrEditLabel">Create or edit a HouseHelpRequest</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : houseHelpRequestEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="house-help-request-id">ID</Label>
                  <AvInput id="house-help-request-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="servicesLabel" for="house-help-request-services">
                  Services
                </Label>
                <AvInput id="house-help-request-services" type="textarea" name="services" />
              </AvGroup>
              <AvGroup>
                <Label id="cleaningTimeLabel" for="house-help-request-cleaningTime">
                  Cleaning Time
                </Label>
                <AvField id="house-help-request-cleaningTime" type="string" className="form-control" name="cleaningTime" />
              </AvGroup>
              <AvGroup>
                <Label id="cleaningFromTimeLabel" for="house-help-request-cleaningFromTime">
                  Cleaning From Time
                </Label>
                <AvInput
                  id="house-help-request-cleaningFromTime"
                  type="datetime-local"
                  className="form-control"
                  name="cleaningFromTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.houseHelpRequestEntity.cleaningFromTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="cleaningToTimeLabel" for="house-help-request-cleaningToTime">
                  Cleaning To Time
                </Label>
                <AvInput
                  id="house-help-request-cleaningToTime"
                  type="datetime-local"
                  className="form-control"
                  name="cleaningToTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.houseHelpRequestEntity.cleaningToTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="cleaningEquipmentLabel" for="house-help-request-cleaningEquipment">
                  Cleaning Equipment
                </Label>
                <AvInput
                  id="house-help-request-cleaningEquipment"
                  type="select"
                  className="form-control"
                  name="cleaningEquipment"
                  value={(!isNew && houseHelpRequestEntity.cleaningEquipment) || 'OURS'}
                >
                  <option value="OURS">OURS</option>
                  <option value="YOURS">YOURS</option>
                  <option value="EITHER">EITHER</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="cleaningDescriptionLabel" for="house-help-request-cleaningDescription">
                  Cleaning Description
                </Label>
                <AvField id="house-help-request-cleaningDescription" type="text" name="cleaningDescription" />
              </AvGroup>
              <AvGroup>
                <Label id="cookingFromTimeLabel" for="house-help-request-cookingFromTime">
                  Cooking From Time
                </Label>
                <AvInput
                  id="house-help-request-cookingFromTime"
                  type="datetime-local"
                  className="form-control"
                  name="cookingFromTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.houseHelpRequestEntity.cookingFromTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="cookingToTimeLabel" for="house-help-request-cookingToTime">
                  Cooking To Time
                </Label>
                <AvInput
                  id="house-help-request-cookingToTime"
                  type="datetime-local"
                  className="form-control"
                  name="cookingToTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.houseHelpRequestEntity.cookingToTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="cookingServesLabel" for="house-help-request-cookingServes">
                  Cooking Serves
                </Label>
                <AvField id="house-help-request-cookingServes" type="string" className="form-control" name="cookingServes" />
              </AvGroup>
              <AvGroup>
                <Label id="cookingDataLabel" for="house-help-request-cookingData">
                  Cooking Data
                </Label>
                <AvField id="house-help-request-cookingData" type="text" name="cookingData" />
              </AvGroup>
              <AvGroup>
                <Label id="pickupTypeLabel" for="house-help-request-pickupType">
                  Pickup Type
                </Label>
                <AvField
                  id="house-help-request-pickupType"
                  type="string"
                  className="form-control"
                  name="pickupType"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="houseMindingDetailLabel" for="house-help-request-houseMindingDetail">
                  House Minding Detail
                </Label>
                <AvInput id="house-help-request-houseMindingDetail" type="textarea" name="houseMindingDetail" />
              </AvGroup>
              <AvGroup>
                <Label id="mailFromDateLabel" for="house-help-request-mailFromDate">
                  Mail From Date
                </Label>
                <AvInput
                  id="house-help-request-mailFromDate"
                  type="datetime-local"
                  className="form-control"
                  name="mailFromDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.houseHelpRequestEntity.mailFromDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="mailToDateLabel" for="house-help-request-mailToDate">
                  Mail To Date
                </Label>
                <AvInput
                  id="house-help-request-mailToDate"
                  type="datetime-local"
                  className="form-control"
                  name="mailToDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.houseHelpRequestEntity.mailToDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="mailAfterLabel" for="house-help-request-mailAfter">
                  Mail After
                </Label>
                <AvField
                  id="house-help-request-mailAfter"
                  type="text"
                  name="mailAfter"
                  validate={{
                    maxLength: { value: 45, errorMessage: 'This field cannot be longer than 45 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="mailCollectionDaysLabel" for="house-help-request-mailCollectionDays">
                  Mail Collection Days
                </Label>
                <AvField
                  id="house-help-request-mailCollectionDays"
                  type="text"
                  name="mailCollectionDays"
                  validate={{
                    maxLength: { value: 255, errorMessage: 'This field cannot be longer than 255 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="otherDescriptionLabel" for="house-help-request-otherDescription">
                  Other Description
                </Label>
                <AvField
                  id="house-help-request-otherDescription"
                  type="text"
                  name="otherDescription"
                  validate={{
                    maxLength: { value: 255, errorMessage: 'This field cannot be longer than 255 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="otherHoursLabel" for="house-help-request-otherHours">
                  Other Hours
                </Label>
                <AvField id="house-help-request-otherHours" type="string" className="form-control" name="otherHours" />
              </AvGroup>
              <AvGroup>
                <Label id="otherFromTimeLabel" for="house-help-request-otherFromTime">
                  Other From Time
                </Label>
                <AvInput
                  id="house-help-request-otherFromTime"
                  type="datetime-local"
                  className="form-control"
                  name="otherFromTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.houseHelpRequestEntity.otherFromTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="otherToTimeLabel" for="house-help-request-otherToTime">
                  Other To Time
                </Label>
                <AvInput
                  id="house-help-request-otherToTime"
                  type="datetime-local"
                  className="form-control"
                  name="otherToTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.houseHelpRequestEntity.otherToTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="otherEquipmentLabel" for="house-help-request-otherEquipment">
                  Other Equipment
                </Label>
                <AvInput
                  id="house-help-request-otherEquipment"
                  type="select"
                  className="form-control"
                  name="otherEquipment"
                  value={(!isNew && houseHelpRequestEntity.otherEquipment) || 'OURS'}
                >
                  <option value="OURS">OURS</option>
                  <option value="YOURS">YOURS</option>
                  <option value="EITHER">EITHER</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="provideForLabel" for="house-help-request-provideFor">
                  Provide For
                </Label>
                <AvField id="house-help-request-provideFor" type="string" className="form-control" name="provideFor" />
              </AvGroup>
              <AvGroup>
                <Label id="provideTypeLabel" for="house-help-request-provideType">
                  Provide Type
                </Label>
                <AvInput
                  id="house-help-request-provideType"
                  type="select"
                  className="form-control"
                  name="provideType"
                  value={(!isNew && houseHelpRequestEntity.provideType) || 'FAMILY'}
                >
                  <option value="FAMILY">FAMILY</option>
                  <option value="SUPPORTED">SUPPORTED</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="house-help-request-request">Request</Label>
                <AvInput id="house-help-request-request" type="select" className="form-control" name="requestId">
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
              <Button tag={Link} id="cancel-save" to="/house-help-request" replace color="info">
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
  houseHelpRequestEntity: storeState.houseHelpRequest.entity,
  loading: storeState.houseHelpRequest.loading,
  updating: storeState.houseHelpRequest.updating,
  updateSuccess: storeState.houseHelpRequest.updateSuccess,
});

const mapDispatchToProps = {
  getHelpRequests,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HouseHelpRequestUpdate);
