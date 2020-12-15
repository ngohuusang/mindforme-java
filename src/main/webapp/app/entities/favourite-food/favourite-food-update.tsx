import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IChild } from 'app/shared/model/child.model';
import { getEntities as getChildren } from 'app/entities/child/child.reducer';
import { getEntity, updateEntity, createEntity, reset } from './favourite-food.reducer';
import { IFavouriteFood } from 'app/shared/model/favourite-food.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFavouriteFoodUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FavouriteFoodUpdate = (props: IFavouriteFoodUpdateProps) => {
  const [childId, setChildId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { favouriteFoodEntity, children, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/favourite-food' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getChildren();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...favouriteFoodEntity,
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
          <h2 id="mindformeApp.favouriteFood.home.createOrEditLabel">Create or edit a FavouriteFood</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : favouriteFoodEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="favourite-food-id">ID</Label>
                  <AvInput id="favourite-food-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="favourite-food-name">
                  Name
                </Label>
                <AvField id="favourite-food-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="favourite-food-status">
                  Status
                </Label>
                <AvInput
                  id="favourite-food-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && favouriteFoodEntity.status) || 'Active'}
                >
                  <option value="Active">Active</option>
                  <option value="Inactive">Inactive</option>
                  <option value="Pending">Pending</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/favourite-food" replace color="info">
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
  children: storeState.child.entities,
  favouriteFoodEntity: storeState.favouriteFood.entity,
  loading: storeState.favouriteFood.loading,
  updating: storeState.favouriteFood.updating,
  updateSuccess: storeState.favouriteFood.updateSuccess,
});

const mapDispatchToProps = {
  getChildren,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FavouriteFoodUpdate);
