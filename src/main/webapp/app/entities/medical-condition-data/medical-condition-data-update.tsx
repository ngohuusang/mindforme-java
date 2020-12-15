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
import { IMedicalCondition } from 'app/shared/model/medical-condition.model';
import { getEntities as getMedicalConditions } from 'app/entities/medical-condition/medical-condition.reducer';
import { getEntity, updateEntity, createEntity, reset } from './medical-condition-data.reducer';
import { IMedicalConditionData } from 'app/shared/model/medical-condition-data.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMedicalConditionDataUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MedicalConditionDataUpdate = (props: IMedicalConditionDataUpdateProps) => {
  const [langId, setLangId] = useState('0');
  const [medicalConditionId, setMedicalConditionId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { medicalConditionDataEntity, languages, medicalConditions, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/medical-condition-data' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getLanguages();
    props.getMedicalConditions();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...medicalConditionDataEntity,
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
          <h2 id="mindformeApp.medicalConditionData.home.createOrEditLabel">Create or edit a MedicalConditionData</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : medicalConditionDataEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="medical-condition-data-id">ID</Label>
                  <AvInput id="medical-condition-data-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="contentLabel" for="medical-condition-data-content">
                  Content
                </Label>
                <AvField
                  id="medical-condition-data-content"
                  type="text"
                  name="content"
                  validate={{
                    maxLength: { value: 255, errorMessage: 'This field cannot be longer than 255 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="medical-condition-data-lang">Lang</Label>
                <AvInput id="medical-condition-data-lang" type="select" className="form-control" name="langId">
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
                <Label for="medical-condition-data-medicalCondition">Medical Condition</Label>
                <AvInput id="medical-condition-data-medicalCondition" type="select" className="form-control" name="medicalConditionId">
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
              <Button tag={Link} id="cancel-save" to="/medical-condition-data" replace color="info">
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
  medicalConditions: storeState.medicalCondition.entities,
  medicalConditionDataEntity: storeState.medicalConditionData.entity,
  loading: storeState.medicalConditionData.loading,
  updating: storeState.medicalConditionData.updating,
  updateSuccess: storeState.medicalConditionData.updateSuccess,
});

const mapDispatchToProps = {
  getLanguages,
  getMedicalConditions,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MedicalConditionDataUpdate);
