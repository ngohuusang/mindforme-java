import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IMessage } from 'app/shared/model/message.model';
import { getEntities as getMessages } from 'app/entities/message/message.reducer';
import { getEntity, updateEntity, createEntity, reset } from './message-item.reducer';
import { IMessageItem } from 'app/shared/model/message-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMessageItemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MessageItemUpdate = (props: IMessageItemUpdateProps) => {
  const [senderId, setSenderId] = useState('0');
  const [messageId, setMessageId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { messageItemEntity, users, messages, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/message-item' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
    props.getMessages();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...messageItemEntity,
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
          <h2 id="mindformeApp.messageItem.home.createOrEditLabel">Create or edit a MessageItem</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : messageItemEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="message-item-id">ID</Label>
                  <AvInput id="message-item-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="contentLabel" for="message-item-content">
                  Content
                </Label>
                <AvField id="message-item-content" type="text" name="content" />
              </AvGroup>
              <AvGroup check>
                <Label id="readLabel">
                  <AvInput id="message-item-read" type="checkbox" className="form-check-input" name="read" />
                  Read
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="message-item-sender">Sender</Label>
                <AvInput id="message-item-sender" type="select" className="form-control" name="senderId">
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
                <Label for="message-item-message">Message</Label>
                <AvInput id="message-item-message" type="select" className="form-control" name="messageId">
                  <option value="" key="0" />
                  {messages
                    ? messages.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/message-item" replace color="info">
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
  messages: storeState.message.entities,
  messageItemEntity: storeState.messageItem.entity,
  loading: storeState.messageItem.loading,
  updating: storeState.messageItem.updating,
  updateSuccess: storeState.messageItem.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getMessages,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MessageItemUpdate);
