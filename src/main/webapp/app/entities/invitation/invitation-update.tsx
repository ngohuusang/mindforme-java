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
import { getEntity, updateEntity, createEntity, reset } from './invitation.reducer';
import { IInvitation } from 'app/shared/model/invitation.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IInvitationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InvitationUpdate = (props: IInvitationUpdateProps) => {
  const [userId, setUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { invitationEntity, users, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/invitation' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.freePeriod = convertDateTimeToServer(values.freePeriod);

    if (errors.length === 0) {
      const entity = {
        ...invitationEntity,
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
          <h2 id="mindformeApp.invitation.home.createOrEditLabel">Create or edit a Invitation</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : invitationEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="invitation-id">ID</Label>
                  <AvInput id="invitation-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="emailLabel" for="invitation-email">
                  Email
                </Label>
                <AvField id="invitation-email" type="text" name="email" />
              </AvGroup>
              <AvGroup>
                <Label id="facebookLabel" for="invitation-facebook">
                  Facebook
                </Label>
                <AvField id="invitation-facebook" type="text" name="facebook" />
              </AvGroup>
              <AvGroup>
                <Label id="freePeriodLabel" for="invitation-freePeriod">
                  Free Period
                </Label>
                <AvInput
                  id="invitation-freePeriod"
                  type="datetime-local"
                  className="form-control"
                  name="freePeriod"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.invitationEntity.freePeriod)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="invitation-user">User</Label>
                <AvInput id="invitation-user" type="select" className="form-control" name="userId">
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
              <Button tag={Link} id="cancel-save" to="/invitation" replace color="info">
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
  invitationEntity: storeState.invitation.entity,
  loading: storeState.invitation.loading,
  updating: storeState.invitation.updating,
  updateSuccess: storeState.invitation.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InvitationUpdate);
