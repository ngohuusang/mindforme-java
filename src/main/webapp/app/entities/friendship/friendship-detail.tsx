import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './friendship.reducer';
import { IFriendship } from 'app/shared/model/friendship.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFriendshipDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FriendshipDetail = (props: IFriendshipDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { friendshipEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Friendship [<b>{friendshipEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>Friend</dt>
          <dd>{friendshipEntity.friendLogin ? friendshipEntity.friendLogin : ''}</dd>
          <dt>User</dt>
          <dd>{friendshipEntity.userLogin ? friendshipEntity.userLogin : ''}</dd>
          <dt>Group</dt>
          <dd>{friendshipEntity.groupName ? friendshipEntity.groupName : ''}</dd>
        </dl>
        <Button tag={Link} to="/friendship" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/friendship/${friendshipEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ friendship }: IRootState) => ({
  friendshipEntity: friendship.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FriendshipDetail);
