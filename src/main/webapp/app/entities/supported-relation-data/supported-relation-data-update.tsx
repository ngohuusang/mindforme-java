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
import { ISupportedRelation } from 'app/shared/model/supported-relation.model';
import { getEntities as getSupportedRelations } from 'app/entities/supported-relation/supported-relation.reducer';
import { getEntity, updateEntity, createEntity, reset } from './supported-relation-data.reducer';
import { ISupportedRelationData } from 'app/shared/model/supported-relation-data.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISupportedRelationDataUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SupportedRelationDataUpdate = (props: ISupportedRelationDataUpdateProps) => {
  const [langId, setLangId] = useState('0');
  const [relationId, setRelationId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { supportedRelationDataEntity, languages, supportedRelations, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/supported-relation-data' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getLanguages();
    props.getSupportedRelations();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...supportedRelationDataEntity,
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
          <h2 id="mindformeApp.supportedRelationData.home.createOrEditLabel">Create or edit a SupportedRelationData</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : supportedRelationDataEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="supported-relation-data-id">ID</Label>
                  <AvInput id="supported-relation-data-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="contentLabel" for="supported-relation-data-content">
                  Content
                </Label>
                <AvField
                  id="supported-relation-data-content"
                  type="text"
                  name="content"
                  validate={{
                    maxLength: { value: 255, errorMessage: 'This field cannot be longer than 255 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="supported-relation-data-lang">Lang</Label>
                <AvInput id="supported-relation-data-lang" type="select" className="form-control" name="langId">
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
                <Label for="supported-relation-data-relation">Relation</Label>
                <AvInput id="supported-relation-data-relation" type="select" className="form-control" name="relationId">
                  <option value="" key="0" />
                  {supportedRelations
                    ? supportedRelations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/supported-relation-data" replace color="info">
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
  supportedRelations: storeState.supportedRelation.entities,
  supportedRelationDataEntity: storeState.supportedRelationData.entity,
  loading: storeState.supportedRelationData.loading,
  updating: storeState.supportedRelationData.updating,
  updateSuccess: storeState.supportedRelationData.updateSuccess,
});

const mapDispatchToProps = {
  getLanguages,
  getSupportedRelations,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SupportedRelationDataUpdate);
