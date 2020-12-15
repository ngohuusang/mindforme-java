import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './interest-data.reducer';
import { IInterestData } from 'app/shared/model/interest-data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInterestDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InterestDataDetail = (props: IInterestDataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { interestDataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          InterestData [<b>{interestDataEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>{interestDataEntity.content}</dd>
          <dt>Lang</dt>
          <dd>{interestDataEntity.langName ? interestDataEntity.langName : ''}</dd>
          <dt>Interest</dt>
          <dd>{interestDataEntity.interestName ? interestDataEntity.interestName : ''}</dd>
        </dl>
        <Button tag={Link} to="/interest-data" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/interest-data/${interestDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ interestData }: IRootState) => ({
  interestDataEntity: interestData.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InterestDataDetail);
