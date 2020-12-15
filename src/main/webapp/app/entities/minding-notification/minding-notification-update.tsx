import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IFamily } from 'app/shared/model/family.model';
import { getEntities as getFamilies } from 'app/entities/family/family.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './minding-notification.reducer';
import { IMindingNotification } from 'app/shared/model/minding-notification.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMindingNotificationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MindingNotificationUpdate = (props: IMindingNotificationUpdateProps) => {
  const [familyId, setFamilyId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { mindingNotificationEntity, families, loading, updating } = props;

  const { minding } = mindingNotificationEntity;

  const handleClose = () => {
    props.history.push('/minding-notification' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getFamilies();
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
    if (errors.length === 0) {
      const entity = {
        ...mindingNotificationEntity,
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
          <h2 id="mindformeApp.mindingNotification.home.createOrEditLabel">Create or edit a MindingNotification</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : mindingNotificationEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="minding-notification-id">ID</Label>
                  <AvInput id="minding-notification-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="typeLabel" for="minding-notification-type">
                  Type
                </Label>
                <AvInput
                  id="minding-notification-type"
                  type="select"
                  className="form-control"
                  name="type"
                  value={(!isNew && mindingNotificationEntity.type) || 'PUBLIC'}
                >
                  <option value="PUBLIC">PUBLIC</option>
                  <option value="FRIENDS">FRIENDS</option>
                  <option value="FRIENDS_OF_FRIENDS">FRIENDS_OF_FRIENDS</option>
                  <option value="GROUP">GROUP</option>
                  <option value="PRIVATE">PRIVATE</option>
                </AvInput>
                <UncontrolledTooltip target="typeLabel">F for friends, FF for friends of friends, O for others.</UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="mindingLabel" for="minding-notification-minding">
                  Minding
                </Label>
                <AvInput id="minding-notification-minding" type="textarea" name="minding" />
              </AvGroup>
              <AvGroup check>
                <Label id="pushNotificationLabel">
                  <AvInput
                    id="minding-notification-pushNotification"
                    type="checkbox"
                    className="form-check-input"
                    name="pushNotification"
                  />
                  Push Notification
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="minding-notification-email">
                  Email
                </Label>
                <AvInput
                  id="minding-notification-email"
                  type="select"
                  className="form-control"
                  name="email"
                  value={(!isNew && mindingNotificationEntity.email) || 'IMMEDIATELY'}
                >
                  <option value="IMMEDIATELY">IMMEDIATELY</option>
                  <option value="DAILY">DAILY</option>
                  <option value="WEEKLY">WEEKLY</option>
                </AvInput>
                <UncontrolledTooltip target="emailLabel">D for daily , W for weekly and I for immediately</UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="minding-notification-status">
                  Status
                </Label>
                <AvInput
                  id="minding-notification-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && mindingNotificationEntity.status) || 'Active'}
                >
                  <option value="Active">Active</option>
                  <option value="Inactive">Inactive</option>
                  <option value="Pending">Pending</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="minding-notification-family">Family</Label>
                <AvInput id="minding-notification-family" type="select" className="form-control" name="familyId">
                  <option value="" key="0" />
                  {families
                    ? families.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/minding-notification" replace color="info">
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
  families: storeState.family.entities,
  mindingNotificationEntity: storeState.mindingNotification.entity,
  loading: storeState.mindingNotification.loading,
  updating: storeState.mindingNotification.updating,
  updateSuccess: storeState.mindingNotification.updateSuccess,
});

const mapDispatchToProps = {
  getFamilies,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MindingNotificationUpdate);
