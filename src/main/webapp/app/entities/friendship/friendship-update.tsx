import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IFriendshipGroup } from 'app/shared/model/friendship-group.model';
import { getEntities as getFriendshipGroups } from 'app/entities/friendship-group/friendship-group.reducer';
import { getEntity, updateEntity, createEntity, reset } from './friendship.reducer';
import { IFriendship } from 'app/shared/model/friendship.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFriendshipUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FriendshipUpdate = (props: IFriendshipUpdateProps) => {
  const [friendId, setFriendId] = useState('0');
  const [userId, setUserId] = useState('0');
  const [groupId, setGroupId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { friendshipEntity, users, friendshipGroups, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/friendship' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
    props.getFriendshipGroups();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...friendshipEntity,
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
          <h2 id="mindformeApp.friendship.home.createOrEditLabel">Create or edit a Friendship</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : friendshipEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="friendship-id">ID</Label>
                  <AvInput id="friendship-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label for="friendship-friend">Friend</Label>
                <AvInput id="friendship-friend" type="select" className="form-control" name="friendId">
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
                <Label for="friendship-user">User</Label>
                <AvInput id="friendship-user" type="select" className="form-control" name="userId">
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
                <Label for="friendship-group">Group</Label>
                <AvInput id="friendship-group" type="select" className="form-control" name="groupId">
                  <option value="" key="0" />
                  {friendshipGroups
                    ? friendshipGroups.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/friendship" replace color="info">
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
  friendshipGroups: storeState.friendshipGroup.entities,
  friendshipEntity: storeState.friendship.entity,
  loading: storeState.friendship.loading,
  updating: storeState.friendship.updating,
  updateSuccess: storeState.friendship.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getFriendshipGroups,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FriendshipUpdate);
