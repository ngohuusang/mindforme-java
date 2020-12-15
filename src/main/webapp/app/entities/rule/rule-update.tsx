import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPet } from 'app/shared/model/pet.model';
import { getEntities as getPets } from 'app/entities/pet/pet.reducer';
import { getEntity, updateEntity, createEntity, reset } from './rule.reducer';
import { IRule } from 'app/shared/model/rule.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRuleUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RuleUpdate = (props: IRuleUpdateProps) => {
  const [petId, setPetId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { ruleEntity, pets, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/rule' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPets();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...ruleEntity,
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
          <h2 id="mindformeApp.rule.home.createOrEditLabel">Create or edit a Rule</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : ruleEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="rule-id">ID</Label>
                  <AvInput id="rule-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="rule-name">
                  Name
                </Label>
                <AvField id="rule-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="rule-status">
                  Status
                </Label>
                <AvInput
                  id="rule-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && ruleEntity.status) || 'Active'}
                >
                  <option value="Active">Active</option>
                  <option value="Inactive">Inactive</option>
                  <option value="Pending">Pending</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/rule" replace color="info">
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
  pets: storeState.pet.entities,
  ruleEntity: storeState.rule.entity,
  loading: storeState.rule.loading,
  updating: storeState.rule.updating,
  updateSuccess: storeState.rule.updateSuccess,
});

const mapDispatchToProps = {
  getPets,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RuleUpdate);
