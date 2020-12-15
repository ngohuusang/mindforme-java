import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IChild } from 'app/shared/model/child.model';
import { getEntities as getChildren } from 'app/entities/child/child.reducer';
import { getEntity, updateEntity, createEntity, reset } from './child-help-request.reducer';
import { IChildHelpRequest } from 'app/shared/model/child-help-request.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IChildHelpRequestUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ChildHelpRequestUpdate = (props: IChildHelpRequestUpdateProps) => {
  const [idschild, setIdschild] = useState([]);
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { childHelpRequestEntity, children, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/child-help-request' + props.location.search);
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
    values.dateFrom = convertDateTimeToServer(values.dateFrom);
    values.dateTo = convertDateTimeToServer(values.dateTo);

    if (errors.length === 0) {
      const entity = {
        ...childHelpRequestEntity,
        ...values,
        children: mapIdList(values.children),
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
          <h2 id="mindformeApp.childHelpRequest.home.createOrEditLabel">Create or edit a ChildHelpRequest</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : childHelpRequestEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="child-help-request-id">ID</Label>
                  <AvInput id="child-help-request-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="totalHelpTimeLabel" for="child-help-request-totalHelpTime">
                  Total Help Time
                </Label>
                <AvField id="child-help-request-totalHelpTime" type="string" className="form-control" name="totalHelpTime" />
              </AvGroup>
              <AvGroup>
                <Label id="dateFromLabel" for="child-help-request-dateFrom">
                  Date From
                </Label>
                <AvInput
                  id="child-help-request-dateFrom"
                  type="datetime-local"
                  className="form-control"
                  name="dateFrom"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.childHelpRequestEntity.dateFrom)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateToLabel" for="child-help-request-dateTo">
                  Date To
                </Label>
                <AvInput
                  id="child-help-request-dateTo"
                  type="datetime-local"
                  className="form-control"
                  name="dateTo"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.childHelpRequestEntity.dateTo)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="timeFromLabel" for="child-help-request-timeFrom">
                  Time From
                </Label>
                <AvField
                  id="child-help-request-timeFrom"
                  type="text"
                  name="timeFrom"
                  validate={{
                    maxLength: { value: 10, errorMessage: 'This field cannot be longer than 10 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="timeToLabel" for="child-help-request-timeTo">
                  Time To
                </Label>
                <AvField
                  id="child-help-request-timeTo"
                  type="text"
                  name="timeTo"
                  validate={{
                    maxLength: { value: 10, errorMessage: 'This field cannot be longer than 10 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="timeTypeLabel" for="child-help-request-timeType">
                  Time Type
                </Label>
                <AvInput
                  id="child-help-request-timeType"
                  type="select"
                  className="form-control"
                  name="timeType"
                  value={(!isNew && childHelpRequestEntity.timeType) || 'HOUR'}
                >
                  <option value="HOUR">HOUR</option>
                  <option value="SPECIFIC_TIME">SPECIFIC_TIME</option>
                </AvInput>
                <UncontrolledTooltip target="timeTypeLabel">1= hours, 2= specific time</UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="contentLabel" for="child-help-request-content">
                  Content
                </Label>
                <AvField id="child-help-request-content" type="text" name="content" />
              </AvGroup>
              <AvGroup>
                <Label id="otherTaskLabel" for="child-help-request-otherTask">
                  Other Task
                </Label>
                <AvField id="child-help-request-otherTask" type="text" name="otherTask" />
              </AvGroup>
              <AvGroup>
                <Label for="child-help-request-child">Child</Label>
                <AvInput
                  id="child-help-request-child"
                  type="select"
                  multiple
                  className="form-control"
                  name="children"
                  value={childHelpRequestEntity.children && childHelpRequestEntity.children.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {children
                    ? children.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.firstName}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/child-help-request" replace color="info">
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
  childHelpRequestEntity: storeState.childHelpRequest.entity,
  loading: storeState.childHelpRequest.loading,
  updating: storeState.childHelpRequest.updating,
  updateSuccess: storeState.childHelpRequest.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(ChildHelpRequestUpdate);
