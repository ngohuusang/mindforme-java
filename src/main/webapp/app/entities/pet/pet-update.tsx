import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPetBreed } from 'app/shared/model/pet-breed.model';
import { getEntities as getPetBreeds } from 'app/entities/pet-breed/pet-breed.reducer';
import { IPetType } from 'app/shared/model/pet-type.model';
import { getEntities as getPetTypes } from 'app/entities/pet-type/pet-type.reducer';
import { IFeeding } from 'app/shared/model/feeding.model';
import { getEntities as getFeedings } from 'app/entities/feeding/feeding.reducer';
import { IRule } from 'app/shared/model/rule.model';
import { getEntities as getRules } from 'app/entities/rule/rule.reducer';
import { IAllergy } from 'app/shared/model/allergy.model';
import { getEntities as getAllergies } from 'app/entities/allergy/allergy.reducer';
import { IWalkingOther } from 'app/shared/model/walking-other.model';
import { getEntities as getWalkingOthers } from 'app/entities/walking-other/walking-other.reducer';
import { IFamily } from 'app/shared/model/family.model';
import { getEntities as getFamilies } from 'app/entities/family/family.reducer';
import { IPetHelpRequest } from 'app/shared/model/pet-help-request.model';
import { getEntities as getPetHelpRequests } from 'app/entities/pet-help-request/pet-help-request.reducer';
import { getEntity, updateEntity, createEntity, reset } from './pet.reducer';
import { IPet } from 'app/shared/model/pet.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPetUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PetUpdate = (props: IPetUpdateProps) => {
  const [idsfeeding, setIdsfeeding] = useState([]);
  const [idsrule, setIdsrule] = useState([]);
  const [idsallergy, setIdsallergy] = useState([]);
  const [idswalking, setIdswalking] = useState([]);
  const [breedId, setBreedId] = useState('0');
  const [petTypeId, setPetTypeId] = useState('0');
  const [familyId, setFamilyId] = useState('0');
  const [helpRequestId, setHelpRequestId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { petEntity, petBreeds, petTypes, feedings, rules, allergies, walkingOthers, families, petHelpRequests, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/pet' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPetBreeds();
    props.getPetTypes();
    props.getFeedings();
    props.getRules();
    props.getAllergies();
    props.getWalkingOthers();
    props.getFamilies();
    props.getPetHelpRequests();
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
        ...petEntity,
        ...values,
        feedings: mapIdList(values.feedings),
        rules: mapIdList(values.rules),
        allergies: mapIdList(values.allergies),
        walkings: mapIdList(values.walkings),
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
          <h2 id="mindformeApp.pet.home.createOrEditLabel">Create or edit a Pet</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : petEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="pet-id">ID</Label>
                  <AvInput id="pet-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="pet-name">
                  Name
                </Label>
                <AvField id="pet-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="imageUrlLabel" for="pet-imageUrl">
                  Image Url
                </Label>
                <AvField id="pet-imageUrl" type="text" name="imageUrl" />
              </AvGroup>
              <AvGroup>
                <Label id="birthdayLabel" for="pet-birthday">
                  Birthday
                </Label>
                <AvInput
                  id="pet-birthday"
                  type="datetime-local"
                  className="form-control"
                  name="birthday"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.petEntity.birthday)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="pet-status">
                  Status
                </Label>
                <AvInput
                  id="pet-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && petEntity.status) || 'Active'}
                >
                  <option value="Active">Active</option>
                  <option value="Inactive">Inactive</option>
                  <option value="Pending">Pending</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="pet-breed">Breed</Label>
                <AvInput id="pet-breed" type="select" className="form-control" name="breedId">
                  <option value="" key="0" />
                  {petBreeds
                    ? petBreeds.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="pet-petType">Pet Type</Label>
                <AvInput id="pet-petType" type="select" className="form-control" name="petTypeId">
                  <option value="" key="0" />
                  {petTypes
                    ? petTypes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="pet-feeding">Feeding</Label>
                <AvInput
                  id="pet-feeding"
                  type="select"
                  multiple
                  className="form-control"
                  name="feedings"
                  value={petEntity.feedings && petEntity.feedings.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {feedings
                    ? feedings.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="pet-rule">Rule</Label>
                <AvInput
                  id="pet-rule"
                  type="select"
                  multiple
                  className="form-control"
                  name="rules"
                  value={petEntity.rules && petEntity.rules.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {rules
                    ? rules.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="pet-allergy">Allergy</Label>
                <AvInput
                  id="pet-allergy"
                  type="select"
                  multiple
                  className="form-control"
                  name="allergies"
                  value={petEntity.allergies && petEntity.allergies.map(e => e.id)}
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
                <Label for="pet-walking">Walking</Label>
                <AvInput
                  id="pet-walking"
                  type="select"
                  multiple
                  className="form-control"
                  name="walkings"
                  value={petEntity.walkings && petEntity.walkings.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {walkingOthers
                    ? walkingOthers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="pet-family">Family</Label>
                <AvInput id="pet-family" type="select" className="form-control" name="familyId">
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
              <Button tag={Link} id="cancel-save" to="/pet" replace color="info">
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
  petBreeds: storeState.petBreed.entities,
  petTypes: storeState.petType.entities,
  feedings: storeState.feeding.entities,
  rules: storeState.rule.entities,
  allergies: storeState.allergy.entities,
  walkingOthers: storeState.walkingOther.entities,
  families: storeState.family.entities,
  petHelpRequests: storeState.petHelpRequest.entities,
  petEntity: storeState.pet.entity,
  loading: storeState.pet.loading,
  updating: storeState.pet.updating,
  updateSuccess: storeState.pet.updateSuccess,
});

const mapDispatchToProps = {
  getPetBreeds,
  getPetTypes,
  getFeedings,
  getRules,
  getAllergies,
  getWalkingOthers,
  getFamilies,
  getPetHelpRequests,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PetUpdate);
