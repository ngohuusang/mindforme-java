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
import { IInterest } from 'app/shared/model/interest.model';
import { getEntities as getInterests } from 'app/entities/interest/interest.reducer';
import { getEntity, updateEntity, createEntity, reset } from './interest-data.reducer';
import { IInterestData } from 'app/shared/model/interest-data.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IInterestDataUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InterestDataUpdate = (props: IInterestDataUpdateProps) => {
  const [langId, setLangId] = useState('0');
  const [interestId, setInterestId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { interestDataEntity, languages, interests, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/interest-data' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getLanguages();
    props.getInterests();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...interestDataEntity,
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
          <h2 id="mindformeApp.interestData.home.createOrEditLabel">Create or edit a InterestData</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : interestDataEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="interest-data-id">ID</Label>
                  <AvInput id="interest-data-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="contentLabel" for="interest-data-content">
                  Content
                </Label>
                <AvField
                  id="interest-data-content"
                  type="text"
                  name="content"
                  validate={{
                    maxLength: { value: 255, errorMessage: 'This field cannot be longer than 255 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="interest-data-lang">Lang</Label>
                <AvInput id="interest-data-lang" type="select" className="form-control" name="langId">
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
                <Label for="interest-data-interest">Interest</Label>
                <AvInput id="interest-data-interest" type="select" className="form-control" name="interestId">
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
              <Button tag={Link} id="cancel-save" to="/interest-data" replace color="info">
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
  interests: storeState.interest.entities,
  interestDataEntity: storeState.interestData.entity,
  loading: storeState.interestData.loading,
  updating: storeState.interestData.updating,
  updateSuccess: storeState.interestData.updateSuccess,
});

const mapDispatchToProps = {
  getLanguages,
  getInterests,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InterestDataUpdate);
