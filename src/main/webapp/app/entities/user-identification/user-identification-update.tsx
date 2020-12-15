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
import { getEntity, updateEntity, createEntity, reset } from './user-identification.reducer';
import { IUserIdentification } from 'app/shared/model/user-identification.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUserIdentificationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserIdentificationUpdate = (props: IUserIdentificationUpdateProps) => {
  const [userId, setUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { userIdentificationEntity, users, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/user-identification' + props.location.search);
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
    values.verifiedDate = convertDateTimeToServer(values.verifiedDate);

    if (errors.length === 0) {
      const entity = {
        ...userIdentificationEntity,
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
          <h2 id="mindformeApp.userIdentification.home.createOrEditLabel">Create or edit a UserIdentification</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : userIdentificationEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="user-identification-id">ID</Label>
                  <AvInput id="user-identification-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="idTypeLabel" for="user-identification-idType">
                  Id Type
                </Label>
                <AvInput
                  id="user-identification-idType"
                  type="select"
                  className="form-control"
                  name="idType"
                  value={(!isNew && userIdentificationEntity.idType) || 'PASSPORT'}
                >
                  <option value="PASSPORT">PASSPORT</option>
                  <option value="DRIVER_LICENSE">DRIVER_LICENSE</option>
                  <option value="OTHER">OTHER</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="user-identification-name">
                  Name
                </Label>
                <AvField id="user-identification-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="expiredLabel" for="user-identification-expired">
                  Expired
                </Label>
                <AvField id="user-identification-expired" type="text" name="expired" />
              </AvGroup>
              <AvGroup>
                <Label id="idNumberLabel" for="user-identification-idNumber">
                  Id Number
                </Label>
                <AvField id="user-identification-idNumber" type="text" name="idNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="frontImageLabel" for="user-identification-frontImage">
                  Front Image
                </Label>
                <AvField id="user-identification-frontImage" type="text" name="frontImage" />
              </AvGroup>
              <AvGroup>
                <Label id="backImageLabel" for="user-identification-backImage">
                  Back Image
                </Label>
                <AvField id="user-identification-backImage" type="text" name="backImage" />
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="user-identification-note">
                  Note
                </Label>
                <AvField id="user-identification-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label id="verifierLabel" for="user-identification-verifier">
                  Verifier
                </Label>
                <AvField id="user-identification-verifier" type="text" name="verifier" />
              </AvGroup>
              <AvGroup>
                <Label id="verifiedDateLabel" for="user-identification-verifiedDate">
                  Verified Date
                </Label>
                <AvInput
                  id="user-identification-verifiedDate"
                  type="datetime-local"
                  className="form-control"
                  name="verifiedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.userIdentificationEntity.verifiedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="verificationStatusLabel" for="user-identification-verificationStatus">
                  Verification Status
                </Label>
                <AvInput
                  id="user-identification-verificationStatus"
                  type="select"
                  className="form-control"
                  name="verificationStatus"
                  value={(!isNew && userIdentificationEntity.verificationStatus) || 'ADDED'}
                >
                  <option value="ADDED">ADDED</option>
                  <option value="VERIFYING">VERIFYING</option>
                  <option value="ACCEPTED">ACCEPTED</option>
                  <option value="REJECTED">REJECTED</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="user-identification-user">User</Label>
                <AvInput id="user-identification-user" type="select" className="form-control" name="userId">
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
              <Button tag={Link} id="cancel-save" to="/user-identification" replace color="info">
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
  userIdentificationEntity: storeState.userIdentification.entity,
  loading: storeState.userIdentification.loading,
  updating: storeState.userIdentification.updating,
  updateSuccess: storeState.userIdentification.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(UserIdentificationUpdate);
