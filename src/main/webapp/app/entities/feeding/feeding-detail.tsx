import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './feeding.reducer';
import { IFeeding } from 'app/shared/model/feeding.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFeedingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FeedingDetail = (props: IFeedingDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { feedingEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Feeding [<b>{feedingEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{feedingEntity.name}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{feedingEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/feeding" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/feeding/${feedingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ feeding }: IRootState) => ({
  feedingEntity: feeding.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FeedingDetail);
