import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-identification.reducer';
import { IUserIdentification } from 'app/shared/model/user-identification.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserIdentificationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserIdentificationDetail = (props: IUserIdentificationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { userIdentificationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          UserIdentification [<b>{userIdentificationEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="idType">Id Type</span>
          </dt>
          <dd>{userIdentificationEntity.idType}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{userIdentificationEntity.name}</dd>
          <dt>
            <span id="expired">Expired</span>
          </dt>
          <dd>{userIdentificationEntity.expired}</dd>
          <dt>
            <span id="idNumber">Id Number</span>
          </dt>
          <dd>{userIdentificationEntity.idNumber}</dd>
          <dt>
            <span id="frontImage">Front Image</span>
          </dt>
          <dd>{userIdentificationEntity.frontImage}</dd>
          <dt>
            <span id="backImage">Back Image</span>
          </dt>
          <dd>{userIdentificationEntity.backImage}</dd>
          <dt>
            <span id="note">Note</span>
          </dt>
          <dd>{userIdentificationEntity.note}</dd>
          <dt>
            <span id="verifier">Verifier</span>
          </dt>
          <dd>{userIdentificationEntity.verifier}</dd>
          <dt>
            <span id="verifiedDate">Verified Date</span>
          </dt>
          <dd>
            {userIdentificationEntity.verifiedDate ? (
              <TextFormat value={userIdentificationEntity.verifiedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="verificationStatus">Verification Status</span>
          </dt>
          <dd>{userIdentificationEntity.verificationStatus}</dd>
          <dt>User</dt>
          <dd>{userIdentificationEntity.userLogin ? userIdentificationEntity.userLogin : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-identification" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-identification/${userIdentificationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ userIdentification }: IRootState) => ({
  userIdentificationEntity: userIdentification.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserIdentificationDetail);
