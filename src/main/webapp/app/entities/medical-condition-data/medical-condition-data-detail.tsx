import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './medical-condition-data.reducer';
import { IMedicalConditionData } from 'app/shared/model/medical-condition-data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMedicalConditionDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MedicalConditionDataDetail = (props: IMedicalConditionDataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { medicalConditionDataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          MedicalConditionData [<b>{medicalConditionDataEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>{medicalConditionDataEntity.content}</dd>
          <dt>Lang</dt>
          <dd>{medicalConditionDataEntity.langName ? medicalConditionDataEntity.langName : ''}</dd>
          <dt>Medical Condition</dt>
          <dd>{medicalConditionDataEntity.medicalConditionName ? medicalConditionDataEntity.medicalConditionName : ''}</dd>
        </dl>
        <Button tag={Link} to="/medical-condition-data" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/medical-condition-data/${medicalConditionDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ medicalConditionData }: IRootState) => ({
  medicalConditionDataEntity: medicalConditionData.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MedicalConditionDataDetail);
