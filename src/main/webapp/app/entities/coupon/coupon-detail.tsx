import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './coupon.reducer';
import { ICoupon } from 'app/shared/model/coupon.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICouponDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CouponDetail = (props: ICouponDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { couponEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Coupon [<b>{couponEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{couponEntity.name}</dd>
          <dt>
            <span id="expireDate">Expire Date</span>
          </dt>
          <dd>{couponEntity.expireDate ? <TextFormat value={couponEntity.expireDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{couponEntity.email}</dd>
          <dt>
            <span id="message">Message</span>
          </dt>
          <dd>{couponEntity.message}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{couponEntity.code}</dd>
          <dt>
            <span id="usedBy">Used By</span>
          </dt>
          <dd>{couponEntity.usedBy}</dd>
          <dt>Type</dt>
          <dd>{couponEntity.typeName ? couponEntity.typeName : ''}</dd>
        </dl>
        <Button tag={Link} to="/coupon" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/coupon/${couponEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ coupon }: IRootState) => ({
  couponEntity: coupon.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CouponDetail);
