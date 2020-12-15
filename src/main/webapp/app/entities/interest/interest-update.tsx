import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IChild } from 'app/shared/model/child.model';
import { getEntities as getChildren } from 'app/entities/child/child.reducer';
import { getEntity, updateEntity, createEntity, reset } from './interest.reducer';
import { IInterest } from 'app/shared/model/interest.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IInterestUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InterestUpdate = (props: IInterestUpdateProps) => {
  const [childId, setChildId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { interestEntity, children, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/interest' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getChildren();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...interestEntity,
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
          <h2 id="mindformeApp.interest.home.createOrEditLabel">Create or edit a Interest</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : interestEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="interest-id">ID</Label>
                  <AvInput id="interest-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="interest-name">
                  Name
                </Label>
                <AvField id="interest-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="interest-status">
                  Status
                </Label>
                <AvInput
                  id="interest-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && interestEntity.status) || 'Active'}
                >
                  <option value="Active">Active</option>
                  <option value="Inactive">Inactive</option>
                  <option value="Pending">Pending</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/interest" replace color="info">
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
  children: storeState.child.entities,
  interestEntity: storeState.interest.entity,
  loading: storeState.interest.loading,
  updating: storeState.interest.updating,
  updateSuccess: storeState.interest.updateSuccess,
});

const mapDispatchToProps = {
  getChildren,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InterestUpdate);
