import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './working-with-children.reducer';
import { IWorkingWithChildren } from 'app/shared/model/working-with-children.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWorkingWithChildrenDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WorkingWithChildrenDetail = (props: IWorkingWithChildrenDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { workingWithChildrenEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          WorkingWithChildren [<b>{workingWithChildrenEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="firstName">First Name</span>
          </dt>
          <dd>{workingWithChildrenEntity.firstName}</dd>
          <dt>
            <span id="otherName">Other Name</span>
          </dt>
          <dd>{workingWithChildrenEntity.otherName}</dd>
          <dt>
            <span id="familyName">Family Name</span>
          </dt>
          <dd>{workingWithChildrenEntity.familyName}</dd>
          <dt>
            <span id="birthday">Birthday</span>
          </dt>
          <dd>
            {workingWithChildrenEntity.birthday ? (
              <TextFormat value={workingWithChildrenEntity.birthday} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="checkNumber">Check Number</span>
          </dt>
          <dd>{workingWithChildrenEntity.checkNumber}</dd>
          <dt>
            <span id="frontImage">Front Image</span>
          </dt>
          <dd>{workingWithChildrenEntity.frontImage}</dd>
          <dt>
            <span id="backImage">Back Image</span>
          </dt>
          <dd>{workingWithChildrenEntity.backImage}</dd>
          <dt>
            <span id="note">Note</span>
          </dt>
          <dd>{workingWithChildrenEntity.note}</dd>
          <dt>
            <span id="verifier">Verifier</span>
          </dt>
          <dd>{workingWithChildrenEntity.verifier}</dd>
          <dt>
            <span id="verifiedDate">Verified Date</span>
          </dt>
          <dd>
            {workingWithChildrenEntity.verifiedDate ? (
              <TextFormat value={workingWithChildrenEntity.verifiedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="verificationStatus">Verification Status</span>
          </dt>
          <dd>{workingWithChildrenEntity.verificationStatus}</dd>
          <dt>User</dt>
          <dd>{workingWithChildrenEntity.userLogin ? workingWithChildrenEntity.userLogin : ''}</dd>
        </dl>
        <Button tag={Link} to="/working-with-children" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/working-with-children/${workingWithChildrenEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ workingWithChildren }: IRootState) => ({
  workingWithChildrenEntity: workingWithChildren.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WorkingWithChildrenDetail);
