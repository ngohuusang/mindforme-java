import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './friend-request.reducer';
import { IFriendRequest } from 'app/shared/model/friend-request.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFriendRequestDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FriendRequestDetail = (props: IFriendRequestDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { friendRequestEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          FriendRequest [<b>{friendRequestEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{friendRequestEntity.status}</dd>
          <dt>Friend</dt>
          <dd>{friendRequestEntity.friendLogin ? friendRequestEntity.friendLogin : ''}</dd>
          <dt>User</dt>
          <dd>{friendRequestEntity.userLogin ? friendRequestEntity.userLogin : ''}</dd>
        </dl>
        <Button tag={Link} to="/friend-request" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/friend-request/${friendRequestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ friendRequest }: IRootState) => ({
  friendRequestEntity: friendRequest.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FriendRequestDetail);
