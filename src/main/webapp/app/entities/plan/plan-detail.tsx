import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './plan.reducer';
import { IPlan } from 'app/shared/model/plan.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPlanDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PlanDetail = (props: IPlanDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { planEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Plan [<b>{planEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{planEntity.name}</dd>
          <dt>
            <span id="amount">Amount</span>
          </dt>
          <dd>{planEntity.amount}</dd>
          <dt>
            <span id="months">Months</span>
          </dt>
          <dd>{planEntity.months}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{planEntity.status}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{planEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/plan" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plan/${planEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ plan }: IRootState) => ({
  planEntity: plan.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PlanDetail);
