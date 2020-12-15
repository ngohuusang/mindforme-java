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
import { IWalkingOther } from 'app/shared/model/walking-other.model';
import { getEntities as getWalkingOthers } from 'app/entities/walking-other/walking-other.reducer';
import { getEntity, updateEntity, createEntity, reset } from './walking-other-data.reducer';
import { IWalkingOtherData } from 'app/shared/model/walking-other-data.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWalkingOtherDataUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WalkingOtherDataUpdate = (props: IWalkingOtherDataUpdateProps) => {
  const [langId, setLangId] = useState('0');
  const [walkingOtherId, setWalkingOtherId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { walkingOtherDataEntity, languages, walkingOthers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/walking-other-data' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getLanguages();
    props.getWalkingOthers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...walkingOtherDataEntity,
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
          <h2 id="mindformeApp.walkingOtherData.home.createOrEditLabel">Create or edit a WalkingOtherData</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : walkingOtherDataEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="walking-other-data-id">ID</Label>
                  <AvInput id="walking-other-data-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="contentLabel" for="walking-other-data-content">
                  Content
                </Label>
                <AvField
                  id="walking-other-data-content"
                  type="text"
                  name="content"
                  validate={{
                    maxLength: { value: 255, errorMessage: 'This field cannot be longer than 255 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="walking-other-data-lang">Lang</Label>
                <AvInput id="walking-other-data-lang" type="select" className="form-control" name="langId">
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
                <Label for="walking-other-data-walkingOther">Walking Other</Label>
                <AvInput id="walking-other-data-walkingOther" type="select" className="form-control" name="walkingOtherId">
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
              <Button tag={Link} id="cancel-save" to="/walking-other-data" replace color="info">
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
  walkingOthers: storeState.walkingOther.entities,
  walkingOtherDataEntity: storeState.walkingOtherData.entity,
  loading: storeState.walkingOtherData.loading,
  updating: storeState.walkingOtherData.updating,
  updateSuccess: storeState.walkingOtherData.updateSuccess,
});

const mapDispatchToProps = {
  getLanguages,
  getWalkingOthers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WalkingOtherDataUpdate);
