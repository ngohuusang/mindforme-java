import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAddress } from 'app/shared/model/address.model';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './help-request.reducer';
import { IHelpRequest } from 'app/shared/model/help-request.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IHelpRequestUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HelpRequestUpdate = (props: IHelpRequestUpdateProps) => {
  const [helpLocationId, setHelpLocationId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { helpRequestEntity, addresses, loading, updating } = props;

  const { message, instruction } = helpRequestEntity;

  const handleClose = () => {
    props.history.push('/help-request' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getAddresses();
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
    values.realStart = convertDateTimeToServer(values.realStart);
    values.realEnd = convertDateTimeToServer(values.realEnd);

    if (errors.length === 0) {
      const entity = {
        ...helpRequestEntity,
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
          <h2 id="mindformeApp.helpRequest.home.createOrEditLabel">Create or edit a HelpRequest</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : helpRequestEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="help-request-id">ID</Label>
                  <AvInput id="help-request-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="titleLabel" for="help-request-title">
                  Title
                </Label>
                <AvField id="help-request-title" type="text" name="title" />
              </AvGroup>
              <AvGroup>
                <Label id="typeLabel" for="help-request-type">
                  Type
                </Label>
                <AvInput
                  id="help-request-type"
                  type="select"
                  className="form-control"
                  name="type"
                  value={(!isNew && helpRequestEntity.type) || 'CHILD'}
                >
                  <option value="CHILD">CHILD</option>
                  <option value="PET">PET</option>
                  <option value="SUPPORTED">SUPPORTED</option>
                  <option value="HOUSE">HOUSE</option>
                  <option value="GARDEN">GARDEN</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="acceptanceLabel" for="help-request-acceptance">
                  Acceptance
                </Label>
                <AvField
                  id="help-request-acceptance"
                  type="text"
                  name="acceptance"
                  validate={{
                    maxLength: { value: 255, errorMessage: 'This field cannot be longer than 255 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="requestStatusLabel" for="help-request-requestStatus">
                  Request Status
                </Label>
                <AvInput
                  id="help-request-requestStatus"
                  type="select"
                  className="form-control"
                  name="requestStatus"
                  value={(!isNew && helpRequestEntity.requestStatus) || 'NEW'}
                >
                  <option value="NEW">NEW</option>
                  <option value="ACCEPTED">ACCEPTED</option>
                  <option value="APPROVED">APPROVED</option>
                  <option value="COMPLETED">COMPLETED</option>
                  <option value="RATED">RATED</option>
                  <option value="UNDONE">UNDONE</option>
                  <option value="CANCEL">CANCEL</option>
                </AvInput>
                <UncontrolledTooltip target="requestStatusLabel">
                  N for New , A for accepted, AP for approved and c for complete.,R-rated/Completelu done
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup check>
                <Label id="isOfferLabel">
                  <AvInput id="help-request-isOffer" type="checkbox" className="form-check-input" name="isOffer" />
                  Is Offer
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="offerToLabel" for="help-request-offerTo">
                  Offer To
                </Label>
                <AvInput
                  id="help-request-offerTo"
                  type="select"
                  className="form-control"
                  name="offerTo"
                  value={(!isNew && helpRequestEntity.offerTo) || 'PUBLIC'}
                >
                  <option value="PUBLIC">PUBLIC</option>
                  <option value="FRIENDS">FRIENDS</option>
                  <option value="FRIENDS_OF_FRIENDS">FRIENDS_OF_FRIENDS</option>
                  <option value="GROUP">GROUP</option>
                  <option value="PRIVATE">PRIVATE</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="messageLabel" for="help-request-message">
                  Message
                </Label>
                <AvInput id="help-request-message" type="textarea" name="message" />
              </AvGroup>
              <AvGroup>
                <Label id="instructionLabel" for="help-request-instruction">
                  Instruction
                </Label>
                <AvInput id="help-request-instruction" type="textarea" name="instruction" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="help-request-status">
                  Status
                </Label>
                <AvInput
                  id="help-request-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && helpRequestEntity.status) || 'Active'}
                >
                  <option value="Active">Active</option>
                  <option value="Inactive">Inactive</option>
                  <option value="Pending">Pending</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="locationTypeLabel" for="help-request-locationType">
                  Location Type
                </Label>
                <AvInput
                  id="help-request-locationType"
                  type="select"
                  className="form-control"
                  name="locationType"
                  value={(!isNew && helpRequestEntity.locationType) || 'OUR_OR_YOUR_HOME'}
                >
                  <option value="OUR_OR_YOUR_HOME">OUR_OR_YOUR_HOME</option>
                  <option value="OUR_HOME">OUR_HOME</option>
                  <option value="YOUR_HOME">YOUR_HOME</option>
                  <option value="OTHER">OTHER</option>
                </AvInput>
                <UncontrolledTooltip target="locationTypeLabel">
                  0 for at our home or your home1 for at our home2 for at your home3 for Other
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="realStartLabel" for="help-request-realStart">
                  Real Start
                </Label>
                <AvInput
                  id="help-request-realStart"
                  type="datetime-local"
                  className="form-control"
                  name="realStart"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.helpRequestEntity.realStart)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="realEndLabel" for="help-request-realEnd">
                  Real End
                </Label>
                <AvInput
                  id="help-request-realEnd"
                  type="datetime-local"
                  className="form-control"
                  name="realEnd"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.helpRequestEntity.realEnd)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="ratingLabel" for="help-request-rating">
                  Rating
                </Label>
                <AvField id="help-request-rating" type="string" className="form-control" name="rating" />
              </AvGroup>
              <AvGroup>
                <Label id="requesterCommentLabel" for="help-request-requesterComment">
                  Requester Comment
                </Label>
                <AvField id="help-request-requesterComment" type="text" name="requesterComment" />
              </AvGroup>
              <AvGroup>
                <Label id="helperCommentLabel" for="help-request-helperComment">
                  Helper Comment
                </Label>
                <AvField id="help-request-helperComment" type="text" name="helperComment" />
              </AvGroup>
              <AvGroup>
                <Label id="flaggedLabel" for="help-request-flagged">
                  Flagged
                </Label>
                <AvField id="help-request-flagged" type="string" className="form-control" name="flagged" />
              </AvGroup>
              <AvGroup>
                <Label id="coinsLabel" for="help-request-coins">
                  Coins
                </Label>
                <AvField id="help-request-coins" type="string" className="form-control" name="coins" />
              </AvGroup>
              <AvGroup>
                <Label id="bonusLabel" for="help-request-bonus">
                  Bonus
                </Label>
                <AvField id="help-request-bonus" type="string" className="form-control" name="bonus" />
              </AvGroup>
              <AvGroup>
                <Label for="help-request-helpLocation">Help Location</Label>
                <AvInput id="help-request-helpLocation" type="select" className="form-control" name="helpLocationId">
                  <option value="" key="0" />
                  {addresses
                    ? addresses.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/help-request" replace color="info">
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
  helpRequestEntity: storeState.helpRequest.entity,
  loading: storeState.helpRequest.loading,
  updating: storeState.helpRequest.updating,
  updateSuccess: storeState.helpRequest.updateSuccess,
});

const mapDispatchToProps = {
  getAddresses,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HelpRequestUpdate);
