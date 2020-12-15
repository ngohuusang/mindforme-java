import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './coupon-type.reducer';
import { ICouponType } from 'app/shared/model/coupon-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICouponTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CouponTypeDetail = (props: ICouponTypeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { couponTypeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          CouponType [<b>{couponTypeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{couponTypeEntity.name}</dd>
          <dt>
            <span id="value">Value</span>
          </dt>
          <dd>{couponTypeEntity.value}</dd>
        </dl>
        <Button tag={Link} to="/coupon-type" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/coupon-type/${couponTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ couponType }: IRootState) => ({
  couponTypeEntity: couponType.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CouponTypeDetail);
