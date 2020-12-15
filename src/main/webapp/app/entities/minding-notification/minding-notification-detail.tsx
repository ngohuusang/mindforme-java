import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { ICrudGetAction, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './minding-notification.reducer';
import { IMindingNotification } from 'app/shared/model/minding-notification.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMindingNotificationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MindingNotificationDetail = (props: IMindingNotificationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { mindingNotificationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          MindingNotification [<b>{mindingNotificationEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="type">Type</span>
            <UncontrolledTooltip target="type">F for friends, FF for friends of friends, O for others.</UncontrolledTooltip>
          </dt>
          <dd>{mindingNotificationEntity.type}</dd>
          <dt>
            <span id="minding">Minding</span>
          </dt>
          <dd>{mindingNotificationEntity.minding}</dd>
          <dt>
            <span id="pushNotification">Push Notification</span>
          </dt>
          <dd>{mindingNotificationEntity.pushNotification ? 'true' : 'false'}</dd>
          <dt>
            <span id="email">Email</span>
            <UncontrolledTooltip target="email">D for daily , W for weekly and I for immediately</UncontrolledTooltip>
          </dt>
          <dd>{mindingNotificationEntity.email}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{mindingNotificationEntity.status}</dd>
          <dt>Family</dt>
          <dd>{mindingNotificationEntity.familyId ? mindingNotificationEntity.familyId : ''}</dd>
        </dl>
        <Button tag={Link} to="/minding-notification" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/minding-notification/${mindingNotificationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ mindingNotification }: IRootState) => ({
  mindingNotificationEntity: mindingNotification.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MindingNotificationDetail);
