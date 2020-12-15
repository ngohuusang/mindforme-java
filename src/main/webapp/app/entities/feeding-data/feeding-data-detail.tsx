import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './feeding-data.reducer';
import { IFeedingData } from 'app/shared/model/feeding-data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFeedingDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FeedingDataDetail = (props: IFeedingDataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { feedingDataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          FeedingData [<b>{feedingDataEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>{feedingDataEntity.content}</dd>
          <dt>Lang</dt>
          <dd>{feedingDataEntity.langName ? feedingDataEntity.langName : ''}</dd>
          <dt>Feeding</dt>
          <dd>{feedingDataEntity.feedingName ? feedingDataEntity.feedingName : ''}</dd>
        </dl>
        <Button tag={Link} to="/feeding-data" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/feeding-data/${feedingDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ feedingData }: IRootState) => ({
  feedingDataEntity: feedingData.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FeedingDataDetail);
