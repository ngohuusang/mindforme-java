import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './payment.reducer';
import { IPayment } from 'app/shared/model/payment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPaymentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PaymentDetail = (props: IPaymentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { paymentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Payment [<b>{paymentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="lastFour">Last Four</span>
          </dt>
          <dd>{paymentEntity.lastFour}</dd>
          <dt>
            <span id="expireDate">Expire Date</span>
          </dt>
          <dd>{paymentEntity.expireDate}</dd>
          <dt>
            <span id="tripeCustomerId">Tripe Customer Id</span>
          </dt>
          <dd>{paymentEntity.tripeCustomerId}</dd>
          <dt>
            <span id="cardType">Card Type</span>
          </dt>
          <dd>{paymentEntity.cardType}</dd>
          <dt>Family</dt>
          <dd>{paymentEntity.familyName ? paymentEntity.familyName : ''}</dd>
          <dt>User</dt>
          <dd>{paymentEntity.userLogin ? paymentEntity.userLogin : ''}</dd>
        </dl>
        <Button tag={Link} to="/payment" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/payment/${paymentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ payment }: IRootState) => ({
  paymentEntity: payment.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PaymentDetail);
