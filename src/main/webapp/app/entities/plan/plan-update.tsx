import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './plan.reducer';
import { IPlan } from 'app/shared/model/plan.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPlanUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PlanUpdate = (props: IPlanUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { planEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/plan' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...planEntity,
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
          <h2 id="mindformeApp.plan.home.createOrEditLabel">Create or edit a Plan</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : planEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="plan-id">ID</Label>
                  <AvInput id="plan-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="plan-name">
                  Name
                </Label>
                <AvField id="plan-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="amountLabel" for="plan-amount">
                  Amount
                </Label>
                <AvField
                  id="plan-amount"
                  type="text"
                  name="amount"
                  validate={{
                    maxLength: { value: 10, errorMessage: 'This field cannot be longer than 10 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="monthsLabel" for="plan-months">
                  Months
                </Label>
                <AvField id="plan-months" type="string" className="form-control" name="months" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="plan-status">
                  Status
                </Label>
                <AvInput
                  id="plan-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && planEntity.status) || 'Active'}
                >
                  <option value="Active">Active</option>
                  <option value="Inactive">Inactive</option>
                  <option value="Pending">Pending</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="typeLabel" for="plan-type">
                  Type
                </Label>
                <AvInput id="plan-type" type="select" className="form-control" name="type" value={(!isNew && planEntity.type) || 'FREE'}>
                  <option value="FREE">FREE</option>
                  <option value="PAID">PAID</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/plan" replace color="info">
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
  planEntity: storeState.plan.entity,
  loading: storeState.plan.loading,
  updating: storeState.plan.updating,
  updateSuccess: storeState.plan.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PlanUpdate);
