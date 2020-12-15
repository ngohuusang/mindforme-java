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
import { IRule } from 'app/shared/model/rule.model';
import { getEntities as getRules } from 'app/entities/rule/rule.reducer';
import { getEntity, updateEntity, createEntity, reset } from './rule-data.reducer';
import { IRuleData } from 'app/shared/model/rule-data.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRuleDataUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RuleDataUpdate = (props: IRuleDataUpdateProps) => {
  const [langId, setLangId] = useState('0');
  const [ruleId, setRuleId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { ruleDataEntity, languages, rules, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/rule-data' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getLanguages();
    props.getRules();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...ruleDataEntity,
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
          <h2 id="mindformeApp.ruleData.home.createOrEditLabel">Create or edit a RuleData</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : ruleDataEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="rule-data-id">ID</Label>
                  <AvInput id="rule-data-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="contentLabel" for="rule-data-content">
                  Content
                </Label>
                <AvField
                  id="rule-data-content"
                  type="text"
                  name="content"
                  validate={{
                    maxLength: { value: 255, errorMessage: 'This field cannot be longer than 255 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="rule-data-lang">Lang</Label>
                <AvInput id="rule-data-lang" type="select" className="form-control" name="langId">
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
                <Label for="rule-data-rule">Rule</Label>
                <AvInput id="rule-data-rule" type="select" className="form-control" name="ruleId">
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
              <Button tag={Link} id="cancel-save" to="/rule-data" replace color="info">
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
  rules: storeState.rule.entities,
  ruleDataEntity: storeState.ruleData.entity,
  loading: storeState.ruleData.loading,
  updating: storeState.ruleData.updating,
  updateSuccess: storeState.ruleData.updateSuccess,
});

const mapDispatchToProps = {
  getLanguages,
  getRules,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RuleDataUpdate);
