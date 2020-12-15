import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './doctor.reducer';
import { IDoctor } from 'app/shared/model/doctor.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDoctorDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DoctorDetail = (props: IDoctorDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { doctorEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Doctor [<b>{doctorEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="doctorName">Doctor Name</span>
          </dt>
          <dd>{doctorEntity.doctorName}</dd>
          <dt>
            <span id="phoneNumber">Phone Number</span>
          </dt>
          <dd>{doctorEntity.phoneNumber}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{doctorEntity.email}</dd>
          <dt>
            <span id="medicalPractice">Medical Practice</span>
          </dt>
          <dd>{doctorEntity.medicalPractice}</dd>
          <dt>Address</dt>
          <dd>{doctorEntity.addressAddress ? doctorEntity.addressAddress : ''}</dd>
          <dt>Family</dt>
          <dd>{doctorEntity.familyName ? doctorEntity.familyName : ''}</dd>
        </dl>
        <Button tag={Link} to="/doctor" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/doctor/${doctorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ doctor }: IRootState) => ({
  doctorEntity: doctor.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DoctorDetail);
