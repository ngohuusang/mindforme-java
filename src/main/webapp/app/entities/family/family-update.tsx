import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAddress } from 'app/shared/model/address.model';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { IPlan } from 'app/shared/model/plan.model';
import { getEntities as getPlans } from 'app/entities/plan/plan.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './family.reducer';
import { IFamily } from 'app/shared/model/family.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFamilyUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FamilyUpdate = (props: IFamilyUpdateProps) => {
  const [addressId, setAddressId] = useState('0');
  const [planId, setPlanId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { familyEntity, addresses, plans, loading, updating } = props;

  const { rule } = familyEntity;

  const handleClose = () => {
    props.history.push('/family' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getAddresses();
    props.getPlans();
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
    values.planExpire = convertDateTimeToServer(values.planExpire);

    if (errors.length === 0) {
      const entity = {
        ...familyEntity,
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
          <h2 id="mindformeApp.family.home.createOrEditLabel">Create or edit a Family</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : familyEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="family-id">ID</Label>
                  <AvInput id="family-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="family-name">
                  Name
                </Label>
                <AvField
                  id="family-name"
                  type="text"
                  name="name"
                  validate={{
                    maxLength: { value: 45, errorMessage: 'This field cannot be longer than 45 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="karmaPointsLabel" for="family-karmaPoints">
                  Karma Points
                </Label>
                <AvField id="family-karmaPoints" type="string" className="form-control" name="karmaPoints" />
              </AvGroup>
              <AvGroup>
                <Label id="overviewLabel" for="family-overview">
                  Overview
                </Label>
                <AvField id="family-overview" type="text" name="overview" />
              </AvGroup>
              <AvGroup>
                <Label id="ratingLabel" for="family-rating">
                  Rating
                </Label>
                <AvField id="family-rating" type="text" name="rating" />
              </AvGroup>
              <AvGroup>
                <Label id="imageUrlLabel" for="family-imageUrl">
                  Image Url
                </Label>
                <AvField id="family-imageUrl" type="text" name="imageUrl" />
              </AvGroup>
              <AvGroup>
                <Label id="planExpireLabel" for="family-planExpire">
                  Plan Expire
                </Label>
                <AvInput
                  id="family-planExpire"
                  type="datetime-local"
                  className="form-control"
                  name="planExpire"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.familyEntity.planExpire)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="ruleLabel" for="family-rule">
                  Rule
                </Label>
                <AvInput id="family-rule" type="textarea" name="rule" />
              </AvGroup>
              <AvGroup>
                <Label id="freeMonthsLabel" for="family-freeMonths">
                  Free Months
                </Label>
                <AvField id="family-freeMonths" type="text" name="freeMonths" />
              </AvGroup>
              <AvGroup>
                <Label id="otherVerifyLabel" for="family-otherVerify">
                  Other Verify
                </Label>
                <AvField id="family-otherVerify" type="string" className="form-control" name="otherVerify" />
              </AvGroup>
              <AvGroup check>
                <Label id="kc25PaidLabel">
                  <AvInput id="family-kc25Paid" type="checkbox" className="form-check-input" name="kc25Paid" />
                  Kc 25 Paid
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="kc75PaidLabel">
                  <AvInput id="family-kc75Paid" type="checkbox" className="form-check-input" name="kc75Paid" />
                  Kc 75 Paid
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="privacyFamilyLabel" for="family-privacyFamily">
                  Privacy Family
                </Label>
                <AvInput
                  id="family-privacyFamily"
                  type="select"
                  className="form-control"
                  name="privacyFamily"
                  value={(!isNew && familyEntity.privacyFamily) || 'PUBLIC'}
                >
                  <option value="PUBLIC">PUBLIC</option>
                  <option value="FRIENDS">FRIENDS</option>
                  <option value="FRIENDS_OF_FRIENDS">FRIENDS_OF_FRIENDS</option>
                  <option value="GROUP">GROUP</option>
                  <option value="PRIVATE">PRIVATE</option>
                </AvInput>
              </AvGroup>
              <AvGroup check>
                <Label id="shareToFacebookLabel">
                  <AvInput id="family-shareToFacebook" type="checkbox" className="form-check-input" name="shareToFacebook" />
                  Share To Facebook
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="privacyPersonalLabel" for="family-privacyPersonal">
                  Privacy Personal
                </Label>
                <AvInput
                  id="family-privacyPersonal"
                  type="select"
                  className="form-control"
                  name="privacyPersonal"
                  value={(!isNew && familyEntity.privacyPersonal) || 'PUBLIC'}
                >
                  <option value="PUBLIC">PUBLIC</option>
                  <option value="FRIENDS">FRIENDS</option>
                  <option value="FRIENDS_OF_FRIENDS">FRIENDS_OF_FRIENDS</option>
                  <option value="GROUP">GROUP</option>
                  <option value="PRIVATE">PRIVATE</option>
                </AvInput>
              </AvGroup>
              <AvGroup check>
                <Label id="addToCalendarLabel">
                  <AvInput id="family-addToCalendar" type="checkbox" className="form-check-input" name="addToCalendar" />
                  Add To Calendar
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="remindEventsLabel">
                  <AvInput id="family-remindEvents" type="checkbox" className="form-check-input" name="remindEvents" />
                  Remind Events
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="notifyFacebookLabel">
                  <AvInput id="family-notifyFacebook" type="checkbox" className="form-check-input" name="notifyFacebook" />
                  Notify Facebook
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="distanceRequestLabel" for="family-distanceRequest">
                  Distance Request
                </Label>
                <AvField id="family-distanceRequest" type="string" className="form-control" name="distanceRequest" />
              </AvGroup>
              <AvGroup>
                <Label id="distanceUnitLabel" for="family-distanceUnit">
                  Distance Unit
                </Label>
                <AvInput
                  id="family-distanceUnit"
                  type="select"
                  className="form-control"
                  name="distanceUnit"
                  value={(!isNew && familyEntity.distanceUnit) || 'KM'}
                >
                  <option value="KM">KM</option>
                  <option value="MILE">MILE</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="mailRequestFriendLabel" for="family-mailRequestFriend">
                  Mail Request Friend
                </Label>
                <AvInput
                  id="family-mailRequestFriend"
                  type="select"
                  className="form-control"
                  name="mailRequestFriend"
                  value={(!isNew && familyEntity.mailRequestFriend) || 'IMMEDIATELY'}
                >
                  <option value="IMMEDIATELY">IMMEDIATELY</option>
                  <option value="DAILY">DAILY</option>
                  <option value="WEEKLY">WEEKLY</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="mailRequestFriendOfFriendLabel" for="family-mailRequestFriendOfFriend">
                  Mail Request Friend Of Friend
                </Label>
                <AvInput
                  id="family-mailRequestFriendOfFriend"
                  type="select"
                  className="form-control"
                  name="mailRequestFriendOfFriend"
                  value={(!isNew && familyEntity.mailRequestFriendOfFriend) || 'IMMEDIATELY'}
                >
                  <option value="IMMEDIATELY">IMMEDIATELY</option>
                  <option value="DAILY">DAILY</option>
                  <option value="WEEKLY">WEEKLY</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="mailRequestLabel" for="family-mailRequest">
                  Mail Request
                </Label>
                <AvInput
                  id="family-mailRequest"
                  type="select"
                  className="form-control"
                  name="mailRequest"
                  value={(!isNew && familyEntity.mailRequest) || 'IMMEDIATELY'}
                >
                  <option value="IMMEDIATELY">IMMEDIATELY</option>
                  <option value="DAILY">DAILY</option>
                  <option value="WEEKLY">WEEKLY</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="family-address">Address</Label>
                <AvInput id="family-address" type="select" className="form-control" name="addressId">
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
                <Label for="family-plan">Plan</Label>
                <AvInput id="family-plan" type="select" className="form-control" name="planId">
                  <option value="" key="0" />
                  {plans
                    ? plans.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/family" replace color="info">
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
  plans: storeState.plan.entities,
  familyEntity: storeState.family.entity,
  loading: storeState.family.loading,
  updating: storeState.family.updating,
  updateSuccess: storeState.family.updateSuccess,
});

const mapDispatchToProps = {
  getAddresses,
  getPlans,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FamilyUpdate);
