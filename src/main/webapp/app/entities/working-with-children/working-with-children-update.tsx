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
import { getEntity, updateEntity, createEntity, reset } from './working-with-children.reducer';
import { IWorkingWithChildren } from 'app/shared/model/working-with-children.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWorkingWithChildrenUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WorkingWithChildrenUpdate = (props: IWorkingWithChildrenUpdateProps) => {
  const [userId, setUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { workingWithChildrenEntity, users, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/working-with-children' + props.location.search);
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
    values.birthday = convertDateTimeToServer(values.birthday);
    values.verifiedDate = convertDateTimeToServer(values.verifiedDate);

    if (errors.length === 0) {
      const entity = {
        ...workingWithChildrenEntity,
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
          <h2 id="mindformeApp.workingWithChildren.home.createOrEditLabel">Create or edit a WorkingWithChildren</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : workingWithChildrenEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="working-with-children-id">ID</Label>
                  <AvInput id="working-with-children-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="firstNameLabel" for="working-with-children-firstName">
                  First Name
                </Label>
                <AvField id="working-with-children-firstName" type="text" name="firstName" />
              </AvGroup>
              <AvGroup>
                <Label id="otherNameLabel" for="working-with-children-otherName">
                  Other Name
                </Label>
                <AvField id="working-with-children-otherName" type="text" name="otherName" />
              </AvGroup>
              <AvGroup>
                <Label id="familyNameLabel" for="working-with-children-familyName">
                  Family Name
                </Label>
                <AvField id="working-with-children-familyName" type="text" name="familyName" />
              </AvGroup>
              <AvGroup>
                <Label id="birthdayLabel" for="working-with-children-birthday">
                  Birthday
                </Label>
                <AvInput
                  id="working-with-children-birthday"
                  type="datetime-local"
                  className="form-control"
                  name="birthday"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.workingWithChildrenEntity.birthday)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="checkNumberLabel" for="working-with-children-checkNumber">
                  Check Number
                </Label>
                <AvField id="working-with-children-checkNumber" type="text" name="checkNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="frontImageLabel" for="working-with-children-frontImage">
                  Front Image
                </Label>
                <AvField id="working-with-children-frontImage" type="text" name="frontImage" />
              </AvGroup>
              <AvGroup>
                <Label id="backImageLabel" for="working-with-children-backImage">
                  Back Image
                </Label>
                <AvField id="working-with-children-backImage" type="text" name="backImage" />
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="working-with-children-note">
                  Note
                </Label>
                <AvField id="working-with-children-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label id="verifierLabel" for="working-with-children-verifier">
                  Verifier
                </Label>
                <AvField id="working-with-children-verifier" type="text" name="verifier" />
              </AvGroup>
              <AvGroup>
                <Label id="verifiedDateLabel" for="working-with-children-verifiedDate">
                  Verified Date
                </Label>
                <AvInput
                  id="working-with-children-verifiedDate"
                  type="datetime-local"
                  className="form-control"
                  name="verifiedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.workingWithChildrenEntity.verifiedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="verificationStatusLabel" for="working-with-children-verificationStatus">
                  Verification Status
                </Label>
                <AvInput
                  id="working-with-children-verificationStatus"
                  type="select"
                  className="form-control"
                  name="verificationStatus"
                  value={(!isNew && workingWithChildrenEntity.verificationStatus) || 'ADDED'}
                >
                  <option value="ADDED">ADDED</option>
                  <option value="VERIFYING">VERIFYING</option>
                  <option value="ACCEPTED">ACCEPTED</option>
                  <option value="REJECTED">REJECTED</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="working-with-children-user">User</Label>
                <AvInput id="working-with-children-user" type="select" className="form-control" name="userId">
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
              <Button tag={Link} id="cancel-save" to="/working-with-children" replace color="info">
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
  workingWithChildrenEntity: storeState.workingWithChildren.entity,
  loading: storeState.workingWithChildren.loading,
  updating: storeState.workingWithChildren.updating,
  updateSuccess: storeState.workingWithChildren.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(WorkingWithChildrenUpdate);
