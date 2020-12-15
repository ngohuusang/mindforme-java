import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICountry } from 'app/shared/model/country.model';
import { getEntities as getCountries } from 'app/entities/country/country.reducer';
import { getEntity, updateEntity, createEntity, reset } from './public-holiday.reducer';
import { IPublicHoliday } from 'app/shared/model/public-holiday.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPublicHolidayUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PublicHolidayUpdate = (props: IPublicHolidayUpdateProps) => {
  const [countryId, setCountryId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { publicHolidayEntity, countries, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/public-holiday' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCountries();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...publicHolidayEntity,
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
          <h2 id="mindformeApp.publicHoliday.home.createOrEditLabel">Create or edit a PublicHoliday</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : publicHolidayEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="public-holiday-id">ID</Label>
                  <AvInput id="public-holiday-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dayLabel" for="public-holiday-day">
                  Day
                </Label>
                <AvField id="public-holiday-day" type="string" className="form-control" name="day" />
              </AvGroup>
              <AvGroup>
                <Label id="monthLabel" for="public-holiday-month">
                  Month
                </Label>
                <AvField id="public-holiday-month" type="string" className="form-control" name="month" />
              </AvGroup>
              <AvGroup>
                <Label id="yearLabel" for="public-holiday-year">
                  Year
                </Label>
                <AvField id="public-holiday-year" type="string" className="form-control" name="year" />
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="public-holiday-name">
                  Name
                </Label>
                <AvField id="public-holiday-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label for="public-holiday-country">Country</Label>
                <AvInput id="public-holiday-country" type="select" className="form-control" name="countryId">
                  <option value="" key="0" />
                  {countries
                    ? countries.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/public-holiday" replace color="info">
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
  countries: storeState.country.entities,
  publicHolidayEntity: storeState.publicHoliday.entity,
  loading: storeState.publicHoliday.loading,
  updating: storeState.publicHoliday.updating,
  updateSuccess: storeState.publicHoliday.updateSuccess,
});

const mapDispatchToProps = {
  getCountries,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PublicHolidayUpdate);
