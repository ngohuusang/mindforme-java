import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './medical-condition.reducer';
import { IMedicalCondition } from 'app/shared/model/medical-condition.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMedicalConditionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MedicalConditionDetail = (props: IMedicalConditionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { medicalConditionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          MedicalCondition [<b>{medicalConditionEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{medicalConditionEntity.name}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{medicalConditionEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/medical-condition" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/medical-condition/${medicalConditionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ medicalCondition }: IRootState) => ({
  medicalConditionEntity: medicalCondition.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MedicalConditionDetail);
