import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './friendship-group.reducer';
import { IFriendshipGroup } from 'app/shared/model/friendship-group.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFriendshipGroupUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FriendshipGroupUpdate = (props: IFriendshipGroupUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { friendshipGroupEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/friendship-group' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...friendshipGroupEntity,
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
          <h2 id="mindformeApp.friendshipGroup.home.createOrEditLabel">Create or edit a FriendshipGroup</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : friendshipGroupEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="friendship-group-id">ID</Label>
                  <AvInput id="friendship-group-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="friendship-group-name">
                  Name
                </Label>
                <AvField
                  id="friendship-group-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="friendship-group-description">
                  Description
                </Label>
                <AvField id="friendship-group-description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="orderLabel" for="friendship-group-order">
                  Order
                </Label>
                <AvField id="friendship-group-order" type="string" className="form-control" name="order" />
              </AvGroup>
              <AvGroup>
                <Label id="numberOfMembersLabel" for="friendship-group-numberOfMembers">
                  Number Of Members
                </Label>
                <AvField id="friendship-group-numberOfMembers" type="string" className="form-control" name="numberOfMembers" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/friendship-group" replace color="info">
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
  friendshipGroupEntity: storeState.friendshipGroup.entity,
  loading: storeState.friendshipGroup.loading,
  updating: storeState.friendshipGroup.updating,
  updateSuccess: storeState.friendshipGroup.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FriendshipGroupUpdate);
