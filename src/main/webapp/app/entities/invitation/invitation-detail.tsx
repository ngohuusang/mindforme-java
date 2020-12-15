import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './invitation.reducer';
import { IInvitation } from 'app/shared/model/invitation.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInvitationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InvitationDetail = (props: IInvitationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { invitationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Invitation [<b>{invitationEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{invitationEntity.email}</dd>
          <dt>
            <span id="facebook">Facebook</span>
          </dt>
          <dd>{invitationEntity.facebook}</dd>
          <dt>
            <span id="freePeriod">Free Period</span>
          </dt>
          <dd>
            {invitationEntity.freePeriod ? <TextFormat value={invitationEntity.freePeriod} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>User</dt>
          <dd>{invitationEntity.userLogin ? invitationEntity.userLogin : ''}</dd>
        </dl>
        <Button tag={Link} to="/invitation" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/invitation/${invitationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ invitation }: IRootState) => ({
  invitationEntity: invitation.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InvitationDetail);
