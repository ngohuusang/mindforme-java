import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ILanguage } from 'app/shared/model/language.model';
import { getEntities as getLanguages } from 'app/entities/language/language.reducer';
import { IPetBreed } from 'app/shared/model/pet-breed.model';
import { getEntities as getPetBreeds } from 'app/entities/pet-breed/pet-breed.reducer';
import { getEntity, updateEntity, createEntity, reset } from './pet-breed-data.reducer';
import { IPetBreedData } from 'app/shared/model/pet-breed-data.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPetBreedDataUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PetBreedDataUpdate = (props: IPetBreedDataUpdateProps) => {
  const [langId, setLangId] = useState('0');
  const [petBreedId, setPetBreedId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { petBreedDataEntity, languages, petBreeds, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/pet-breed-data' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getLanguages();
    props.getPetBreeds();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...petBreedDataEntity,
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
          <h2 id="mindformeApp.petBreedData.home.createOrEditLabel">Create or edit a PetBreedData</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : petBreedDataEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="pet-breed-data-id">ID</Label>
                  <AvInput id="pet-breed-data-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="contentLabel" for="pet-breed-data-content">
                  Content
                </Label>
                <AvField
                  id="pet-breed-data-content"
                  type="text"
                  name="content"
                  validate={{
                    maxLength: { value: 255, errorMessage: 'This field cannot be longer than 255 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="pet-breed-data-lang">Lang</Label>
                <AvInput id="pet-breed-data-lang" type="select" className="form-control" name="langId">
                  <option value="" key="0" />
                  {languages
                    ? languages.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="pet-breed-data-petBreed">Pet Breed</Label>
                <AvInput id="pet-breed-data-petBreed" type="select" className="form-control" name="petBreedId">
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
              <Button tag={Link} id="cancel-save" to="/pet-breed-data" replace color="info">
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
  languages: storeState.language.entities,
  petBreeds: storeState.petBreed.entities,
  petBreedDataEntity: storeState.petBreedData.entity,
  loading: storeState.petBreedData.loading,
  updating: storeState.petBreedData.updating,
  updateSuccess: storeState.petBreedData.updateSuccess,
});

const mapDispatchToProps = {
  getLanguages,
  getPetBreeds,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PetBreedDataUpdate);
