import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './public-holiday.reducer';
import { IPublicHoliday } from 'app/shared/model/public-holiday.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPublicHolidayDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PublicHolidayDetail = (props: IPublicHolidayDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { publicHolidayEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          PublicHoliday [<b>{publicHolidayEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="day">Day</span>
          </dt>
          <dd>{publicHolidayEntity.day}</dd>
          <dt>
            <span id="month">Month</span>
          </dt>
          <dd>{publicHolidayEntity.month}</dd>
          <dt>
            <span id="year">Year</span>
          </dt>
          <dd>{publicHolidayEntity.year}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{publicHolidayEntity.name}</dd>
          <dt>Country</dt>
          <dd>{publicHolidayEntity.countryName ? publicHolidayEntity.countryName : ''}</dd>
        </dl>
        <Button tag={Link} to="/public-holiday" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/public-holiday/${publicHolidayEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ publicHoliday }: IRootState) => ({
  publicHolidayEntity: publicHoliday.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PublicHolidayDetail);
