import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './notification.reducer';
import { INotification } from 'app/shared/model/notification.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INotificationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const NotificationDetail = (props: INotificationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { notificationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Notification [<b>{notificationEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{notificationEntity.type}</dd>
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>{notificationEntity.content}</dd>
          <dt>
            <span id="link">Link</span>
          </dt>
          <dd>{notificationEntity.link}</dd>
          <dt>
            <span id="metaData">Meta Data</span>
          </dt>
          <dd>{notificationEntity.metaData}</dd>
          <dt>
            <span id="read">Read</span>
          </dt>
          <dd>{notificationEntity.read ? 'true' : 'false'}</dd>
          <dt>Sender</dt>
          <dd>{notificationEntity.senderLogin ? notificationEntity.senderLogin : ''}</dd>
          <dt>Receiver</dt>
          <dd>{notificationEntity.receiverLogin ? notificationEntity.receiverLogin : ''}</dd>
        </dl>
        <Button tag={Link} to="/notification" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/notification/${notificationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ notification }: IRootState) => ({
  notificationEntity: notification.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(NotificationDetail);
