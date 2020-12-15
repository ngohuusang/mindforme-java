import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './pet-help-request.reducer';
import { IPetHelpRequest } from 'app/shared/model/pet-help-request.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPetHelpRequestDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PetHelpRequestDetail = (props: IPetHelpRequestDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { petHelpRequestEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          PetHelpRequest [<b>{petHelpRequestEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="totalHelpTime">Total Help Time</span>
          </dt>
          <dd>{petHelpRequestEntity.totalHelpTime}</dd>
          <dt>
            <span id="dateFrom">Date From</span>
          </dt>
          <dd>
            {petHelpRequestEntity.dateFrom ? (
              <TextFormat value={petHelpRequestEntity.dateFrom} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dateTo">Date To</span>
          </dt>
          <dd>
            {petHelpRequestEntity.dateTo ? <TextFormat value={petHelpRequestEntity.dateTo} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="timeFrom">Time From</span>
          </dt>
          <dd>{petHelpRequestEntity.timeFrom}</dd>
          <dt>
            <span id="timeTo">Time To</span>
          </dt>
          <dd>{petHelpRequestEntity.timeTo}</dd>
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>{petHelpRequestEntity.content}</dd>
          <dt>Pet</dt>
          <dd>
            {petHelpRequestEntity.pets
              ? petHelpRequestEntity.pets.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {petHelpRequestEntity.pets && i === petHelpRequestEntity.pets.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Request</dt>
          <dd>{petHelpRequestEntity.requestId ? petHelpRequestEntity.requestId : ''}</dd>
        </dl>
        <Button tag={Link} to="/pet-help-request" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pet-help-request/${petHelpRequestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ petHelpRequest }: IRootState) => ({
  petHelpRequestEntity: petHelpRequest.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PetHelpRequestDetail);
