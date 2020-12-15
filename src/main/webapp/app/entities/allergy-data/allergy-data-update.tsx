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
import { IAllergy } from 'app/shared/model/allergy.model';
import { getEntities as getAllergies } from 'app/entities/allergy/allergy.reducer';
import { getEntity, updateEntity, createEntity, reset } from './allergy-data.reducer';
import { IAllergyData } from 'app/shared/model/allergy-data.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAllergyDataUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AllergyDataUpdate = (props: IAllergyDataUpdateProps) => {
  const [langId, setLangId] = useState('0');
  const [allergyId, setAllergyId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { allergyDataEntity, languages, allergies, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/allergy-data' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getLanguages();
    props.getAllergies();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...allergyDataEntity,
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
          <h2 id="mindformeApp.allergyData.home.createOrEditLabel">Create or edit a AllergyData</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : allergyDataEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="allergy-data-id">ID</Label>
                  <AvInput id="allergy-data-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="contentLabel" for="allergy-data-content">
                  Content
                </Label>
                <AvField
                  id="allergy-data-content"
                  type="text"
                  name="content"
                  validate={{
                    maxLength: { value: 255, errorMessage: 'This field cannot be longer than 255 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="allergy-data-lang">Lang</Label>
                <AvInput id="allergy-data-lang" type="select" className="form-control" name="langId">
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
                <Label for="allergy-data-allergy">Allergy</Label>
                <AvInput id="allergy-data-allergy" type="select" className="form-control" name="allergyId">
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
              <Button tag={Link} id="cancel-save" to="/allergy-data" replace color="info">
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
  allergies: storeState.allergy.entities,
  allergyDataEntity: storeState.allergyData.entity,
  loading: storeState.allergyData.loading,
  updating: storeState.allergyData.updating,
  updateSuccess: storeState.allergyData.updateSuccess,
});

const mapDispatchToProps = {
  getLanguages,
  getAllergies,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AllergyDataUpdate);
