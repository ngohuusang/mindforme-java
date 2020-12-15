import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './walking-other-data.reducer';
import { IWalkingOtherData } from 'app/shared/model/walking-other-data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWalkingOtherDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WalkingOtherDataDetail = (props: IWalkingOtherDataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { walkingOtherDataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          WalkingOtherData [<b>{walkingOtherDataEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>{walkingOtherDataEntity.content}</dd>
          <dt>Lang</dt>
          <dd>{walkingOtherDataEntity.langName ? walkingOtherDataEntity.langName : ''}</dd>
          <dt>Walking Other</dt>
          <dd>{walkingOtherDataEntity.walkingOtherName ? walkingOtherDataEntity.walkingOtherName : ''}</dd>
        </dl>
        <Button tag={Link} to="/walking-other-data" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/walking-other-data/${walkingOtherDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ walkingOtherData }: IRootState) => ({
  walkingOtherDataEntity: walkingOtherData.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WalkingOtherDataDetail);
