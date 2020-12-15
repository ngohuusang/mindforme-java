import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './emergency-contact.reducer';
import { IEmergencyContact } from 'app/shared/model/emergency-contact.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmergencyContactDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmergencyContactDetail = (props: IEmergencyContactDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { emergencyContactEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          EmergencyContact [<b>{emergencyContactEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{emergencyContactEntity.name}</dd>
          <dt>
            <span id="relationToYou">Relation To You</span>
          </dt>
          <dd>{emergencyContactEntity.relationToYou}</dd>
          <dt>
            <span id="phoneNumber">Phone Number</span>
          </dt>
          <dd>{emergencyContactEntity.phoneNumber}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{emergencyContactEntity.email}</dd>
          <dt>Address</dt>
          <dd>{emergencyContactEntity.addressAddress ? emergencyContactEntity.addressAddress : ''}</dd>
          <dt>Family</dt>
          <dd>{emergencyContactEntity.familyName ? emergencyContactEntity.familyName : ''}</dd>
        </dl>
        <Button tag={Link} to="/emergency-contact" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/emergency-contact/${emergencyContactEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ emergencyContact }: IRootState) => ({
  emergencyContactEntity: emergencyContact.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmergencyContactDetail);
