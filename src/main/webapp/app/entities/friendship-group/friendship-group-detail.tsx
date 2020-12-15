import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './friendship-group.reducer';
import { IFriendshipGroup } from 'app/shared/model/friendship-group.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFriendshipGroupDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FriendshipGroupDetail = (props: IFriendshipGroupDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { friendshipGroupEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          FriendshipGroup [<b>{friendshipGroupEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{friendshipGroupEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{friendshipGroupEntity.description}</dd>
          <dt>
            <span id="order">Order</span>
          </dt>
          <dd>{friendshipGroupEntity.order}</dd>
          <dt>
            <span id="numberOfMembers">Number Of Members</span>
          </dt>
          <dd>{friendshipGroupEntity.numberOfMembers}</dd>
        </dl>
        <Button tag={Link} to="/friendship-group" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/friendship-group/${friendshipGroupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ friendshipGroup }: IRootState) => ({
  friendshipGroupEntity: friendshipGroup.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FriendshipGroupDetail);
