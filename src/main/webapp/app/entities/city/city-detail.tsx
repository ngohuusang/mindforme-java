import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './city.reducer';
import { ICity } from 'app/shared/model/city.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICityDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CityDetail = (props: ICityDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { cityEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          City [<b>{cityEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{cityEntity.name}</dd>
          <dt>
            <span id="latitude">Latitude</span>
          </dt>
          <dd>{cityEntity.latitude}</dd>
          <dt>
            <span id="longitude">Longitude</span>
          </dt>
          <dd>{cityEntity.longitude}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{cityEntity.status}</dd>
          <dt>State</dt>
          <dd>{cityEntity.stateName ? cityEntity.stateName : ''}</dd>
        </dl>
        <Button tag={Link} to="/city" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/city/${cityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ city }: IRootState) => ({
  cityEntity: city.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CityDetail);
