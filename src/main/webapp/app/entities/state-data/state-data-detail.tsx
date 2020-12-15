import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './state-data.reducer';
import { IStateData } from 'app/shared/model/state-data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStateDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const StateDataDetail = (props: IStateDataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { stateDataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          StateData [<b>{stateDataEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>{stateDataEntity.content}</dd>
          <dt>Lang</dt>
          <dd>{stateDataEntity.langName ? stateDataEntity.langName : ''}</dd>
          <dt>State</dt>
          <dd>{stateDataEntity.stateName ? stateDataEntity.stateName : ''}</dd>
        </dl>
        <Button tag={Link} to="/state-data" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/state-data/${stateDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ stateData }: IRootState) => ({
  stateDataEntity: stateData.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(StateDataDetail);
