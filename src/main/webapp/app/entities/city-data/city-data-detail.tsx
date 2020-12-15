import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './city-data.reducer';
import { ICityData } from 'app/shared/model/city-data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICityDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CityDataDetail = (props: ICityDataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { cityDataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          CityData [<b>{cityDataEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>{cityDataEntity.content}</dd>
          <dt>Lang</dt>
          <dd>{cityDataEntity.langName ? cityDataEntity.langName : ''}</dd>
          <dt>City</dt>
          <dd>{cityDataEntity.cityName ? cityDataEntity.cityName : ''}</dd>
        </dl>
        <Button tag={Link} to="/city-data" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/city-data/${cityDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ cityData }: IRootState) => ({
  cityDataEntity: cityData.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CityDataDetail);
