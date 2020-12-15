import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IChildRelation } from 'app/shared/model/child-relation.model';
import { getEntities as getChildRelations } from 'app/entities/child-relation/child-relation.reducer';
import { IInterest } from 'app/shared/model/interest.model';
import { getEntities as getInterests } from 'app/entities/interest/interest.reducer';
import { IAllergy } from 'app/shared/model/allergy.model';
import { getEntities as getAllergies } from 'app/entities/allergy/allergy.reducer';
import { IFavouriteFood } from 'app/shared/model/favourite-food.model';
import { getEntities as getFavouriteFoods } from 'app/entities/favourite-food/favourite-food.reducer';
import { IMedicalCondition } from 'app/shared/model/medical-condition.model';
import { getEntities as getMedicalConditions } from 'app/entities/medical-condition/medical-condition.reducer';
import { IFamily } from 'app/shared/model/family.model';
import { getEntities as getFamilies } from 'app/entities/family/family.reducer';
import { IHelpRequest } from 'app/shared/model/help-request.model';
import { getEntities as getHelpRequests } from 'app/entities/help-request/help-request.reducer';
import { IChildHelpRequest } from 'app/shared/model/child-help-request.model';
import { getEntities as getChildHelpRequests } from 'app/entities/child-help-request/child-help-request.reducer';
import { getEntity, updateEntity, createEntity, reset } from './child.reducer';
import { IChild } from 'app/shared/model/child.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IChildUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ChildUpdate = (props: IChildUpdateProps) => {
  const [idsinterest, setIdsinterest] = useState([]);
  const [idsallergy, setIdsallergy] = useState([]);
  const [idsfavouriteFood, setIdsfavouriteFood] = useState([]);
  const [idsmedicalCondition, setIdsmedicalCondition] = useState([]);
  const [relationId, setRelationId] = useState('0');
  const [familyId, setFamilyId] = useState('0');
  const [requestId, setRequestId] = useState('0');
  const [helpRequestId, setHelpRequestId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const {
    childEntity,
    childRelations,
    interests,
    allergies,
    favouriteFoods,
    medicalConditions,
    families,
    helpRequests,
    childHelpRequests,
    loading,
    updating,
  } = props;

  const handleClose = () => {
    props.history.push('/child' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getChildRelations();
    props.getInterests();
    props.getAllergies();
    props.getFavouriteFoods();
    props.getMedicalConditions();
    props.getFamilies();
    props.getHelpRequests();
    props.getChildHelpRequests();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.birthday = convertDateTimeToServer(values.birthday);

    if (errors.length === 0) {
      const entity = {
        ...childEntity,
        ...values,
        interests: mapIdList(values.interests),
        allergies: mapIdList(values.allergies),
        favouriteFoods: mapIdList(values.favouriteFoods),
        medicalConditions: mapIdList(values.medicalConditions),
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
          <h2 id="mindformeApp.child.home.createOrEditLabel">Create or edit a Child</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : childEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="child-id">ID</Label>
                  <AvInput id="child-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="firstNameLabel" for="child-firstName">
                  First Name
                </Label>
                <AvField id="child-firstName" type="text" name="firstName" />
              </AvGroup>
              <AvGroup>
                <Label id="lastNameLabel" for="child-lastName">
                  Last Name
                </Label>
                <AvField id="child-lastName" type="text" name="lastName" />
              </AvGroup>
              <AvGroup>
                <Label id="imageUrlLabel" for="child-imageUrl">
                  Image Url
                </Label>
                <AvField id="child-imageUrl" type="text" name="imageUrl" />
              </AvGroup>
              <AvGroup>
                <Label id="birthdayLabel" for="child-birthday">
                  Birthday
                </Label>
                <AvInput
                  id="child-birthday"
                  type="datetime-local"
                  className="form-control"
                  name="birthday"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.childEntity.birthday)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="child-status">
                  Status
                </Label>
                <AvInput
                  id="child-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && childEntity.status) || 'Active'}
                >
                  <option value="Active">Active</option>
                  <option value="Inactive">Inactive</option>
                  <option value="Pending">Pending</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="child-relation">Relation</Label>
                <AvInput id="child-relation" type="select" className="form-control" name="relationId">
                  <option value="" key="0" />
                  {childRelations
                    ? childRelations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="child-interest">Interest</Label>
                <AvInput
                  id="child-interest"
                  type="select"
                  multiple
                  className="form-control"
                  name="interests"
                  value={childEntity.interests && childEntity.interests.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {interests
                    ? interests.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="child-allergy">Allergy</Label>
                <AvInput
                  id="child-allergy"
                  type="select"
                  multiple
                  className="form-control"
                  name="allergies"
                  value={childEntity.allergies && childEntity.allergies.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {allergies
                    ? allergies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="child-favouriteFood">Favourite Food</Label>
                <AvInput
                  id="child-favouriteFood"
                  type="select"
                  multiple
                  className="form-control"
                  name="favouriteFoods"
                  value={childEntity.favouriteFoods && childEntity.favouriteFoods.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {favouriteFoods
                    ? favouriteFoods.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="child-medicalCondition">Medical Condition</Label>
                <AvInput
                  id="child-medicalCondition"
                  type="select"
                  multiple
                  className="form-control"
                  name="medicalConditions"
                  value={childEntity.medicalConditions && childEntity.medicalConditions.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {medicalConditions
                    ? medicalConditions.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="child-family">Family</Label>
                <AvInput id="child-family" type="select" className="form-control" name="familyId">
                  <option value="" key="0" />
                  {families
                    ? families.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="child-request">Request</Label>
                <AvInput id="child-request" type="select" className="form-control" name="requestId">
                  <option value="" key="0" />
                  {helpRequests
                    ? helpRequests.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/child" replace color="info">
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
  childRelations: storeState.childRelation.entities,
  interests: storeState.interest.entities,
  allergies: storeState.allergy.entities,
  favouriteFoods: storeState.favouriteFood.entities,
  medicalConditions: storeState.medicalCondition.entities,
  families: storeState.family.entities,
  helpRequests: storeState.helpRequest.entities,
  childHelpRequests: storeState.childHelpRequest.entities,
  childEntity: storeState.child.entity,
  loading: storeState.child.loading,
  updating: storeState.child.updating,
  updateSuccess: storeState.child.updateSuccess,
});

const mapDispatchToProps = {
  getChildRelations,
  getInterests,
  getAllergies,
  getFavouriteFoods,
  getMedicalConditions,
  getFamilies,
  getHelpRequests,
  getChildHelpRequests,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ChildUpdate);
