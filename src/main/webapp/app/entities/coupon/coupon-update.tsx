import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICouponType } from 'app/shared/model/coupon-type.model';
import { getEntities as getCouponTypes } from 'app/entities/coupon-type/coupon-type.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './coupon.reducer';
import { ICoupon } from 'app/shared/model/coupon.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICouponUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CouponUpdate = (props: ICouponUpdateProps) => {
  const [typeId, setTypeId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { couponEntity, couponTypes, loading, updating } = props;

  const { message } = couponEntity;

  const handleClose = () => {
    props.history.push('/coupon' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCouponTypes();
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.expireDate = convertDateTimeToServer(values.expireDate);

    if (errors.length === 0) {
      const entity = {
        ...couponEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mindformeApp.coupon.home.createOrEditLabel">Create or edit a Coupon</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : couponEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="coupon-id">ID</Label>
                  <AvInput id="coupon-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="coupon-name">
                  Name
                </Label>
                <AvField id="coupon-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="expireDateLabel" for="coupon-expireDate">
                  Expire Date
                </Label>
                <AvInput
                  id="coupon-expireDate"
                  type="datetime-local"
                  className="form-control"
                  name="expireDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.couponEntity.expireDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="coupon-email">
                  Email
                </Label>
                <AvField id="coupon-email" type="text" name="email" />
              </AvGroup>
              <AvGroup>
                <Label id="messageLabel" for="coupon-message">
                  Message
                </Label>
                <AvInput id="coupon-message" type="textarea" name="message" />
              </AvGroup>
              <AvGroup>
                <Label id="codeLabel" for="coupon-code">
                  Code
                </Label>
                <AvField id="coupon-code" type="text" name="code" />
              </AvGroup>
              <AvGroup>
                <Label id="usedByLabel" for="coupon-usedBy">
                  Used By
                </Label>
                <AvField id="coupon-usedBy" type="string" className="form-control" name="usedBy" />
              </AvGroup>
              <AvGroup>
                <Label for="coupon-type">Type</Label>
                <AvInput id="coupon-type" type="select" className="form-control" name="typeId">
                  <option value="" key="0" />
                  {couponTypes
                    ? couponTypes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/coupon" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  couponTypes: storeState.couponType.entities,
  couponEntity: storeState.coupon.entity,
  loading: storeState.coupon.loading,
  updating: storeState.coupon.updating,
  updateSuccess: storeState.coupon.updateSuccess,
});

const mapDispatchToProps = {
  getCouponTypes,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CouponUpdate);
