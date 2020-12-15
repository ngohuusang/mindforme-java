import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './notification.reducer';
import { INotification } from 'app/shared/model/notification.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INotificationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const NotificationUpdate = (props: INotificationUpdateProps) => {
  const [senderId, setSenderId] = useState('0');
  const [receiverId, setReceiverId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { notificationEntity, users, loading, updating } = props;

  const { metaData } = notificationEntity;

  const handleClose = () => {
    props.history.push('/notification' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
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
        ...notificationEntity,
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
          <h2 id="mindformeApp.notification.home.createOrEditLabel">Create or edit a Notification</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : notificationEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="notification-id">ID</Label>
                  <AvInput id="notification-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="typeLabel" for="notification-type">
                  Type
                </Label>
                <AvInput
                  id="notification-type"
                  type="select"
                  className="form-control"
                  name="type"
                  value={(!isNew && notificationEntity.type) || 'ACCEPT_FRIEND'}
                >
                  <option value="ACCEPT_FRIEND">ACCEPT_FRIEND</option>
                  <option value="REMOVE_FRIEND">REMOVE_FRIEND</option>
                  <option value="CANCEL_FRIEND">CANCEL_FRIEND</option>
                  <option value="REJECT_FRIEND">REJECT_FRIEND</option>
                  <option value="CREATE_HELPING">CREATE_HELPING</option>
                  <option value="ACCEPT_HELPING">ACCEPT_HELPING</option>
                  <option value="CONFIRM_HELPING">CONFIRM_HELPING</option>
                  <option value="DECLINE_HELPING">DECLINE_HELPING</option>
                  <option value="COMPLETE_HELPING">COMPLETE_HELPING</option>
                  <option value="REVIEW_HELPING">REVIEW_HELPING</option>
                  <option value="NEW_FRIEND">NEW_FRIEND</option>
                  <option value="RENEW_SUBSCRIPTION">RENEW_SUBSCRIPTION</option>
                  <option value="EXPIRED_SUBSCRIPTION">EXPIRED_SUBSCRIPTION</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="contentLabel" for="notification-content">
                  Content
                </Label>
                <AvField id="notification-content" type="text" name="content" />
              </AvGroup>
              <AvGroup>
                <Label id="linkLabel" for="notification-link">
                  Link
                </Label>
                <AvField id="notification-link" type="text" name="link" />
              </AvGroup>
              <AvGroup>
                <Label id="metaDataLabel" for="notification-metaData">
                  Meta Data
                </Label>
                <AvInput id="notification-metaData" type="textarea" name="metaData" />
              </AvGroup>
              <AvGroup check>
                <Label id="readLabel">
                  <AvInput id="notification-read" type="checkbox" className="form-check-input" name="read" />
                  Read
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="notification-sender">Sender</Label>
                <AvInput id="notification-sender" type="select" className="form-control" name="senderId">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.login}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="notification-receiver">Receiver</Label>
                <AvInput id="notification-receiver" type="select" className="form-control" name="receiverId">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.login}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/notification" replace color="info">
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
  users: storeState.userManagement.users,
  notificationEntity: storeState.notification.entity,
  loading: storeState.notification.loading,
  updating: storeState.notification.updating,
  updateSuccess: storeState.notification.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(NotificationUpdate);
