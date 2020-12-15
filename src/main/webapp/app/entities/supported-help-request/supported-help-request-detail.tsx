import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './supported-help-request.reducer';
import { ISupportedHelpRequest } from 'app/shared/model/supported-help-request.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISupportedHelpRequestDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SupportedHelpRequestDetail = (props: ISupportedHelpRequestDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { supportedHelpRequestEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          SupportedHelpRequest [<b>{supportedHelpRequestEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="totalHelpTime">Total Help Time</span>
          </dt>
          <dd>{supportedHelpRequestEntity.totalHelpTime}</dd>
          <dt>
            <span id="dateFrom">Date From</span>
          </dt>
          <dd>
            {supportedHelpRequestEntity.dateFrom ? (
              <TextFormat value={supportedHelpRequestEntity.dateFrom} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dateTo">Date To</span>
          </dt>
          <dd>
            {supportedHelpRequestEntity.dateTo ? (
              <TextFormat value={supportedHelpRequestEntity.dateTo} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="timeFrom">Time From</span>
          </dt>
          <dd>{supportedHelpRequestEntity.timeFrom}</dd>
          <dt>
            <span id="timeTo">Time To</span>
          </dt>
          <dd>{supportedHelpRequestEntity.timeTo}</dd>
          <dt>
            <span id="timeType">Time Type</span>
            <UncontrolledTooltip target="timeType">1= hours, 2= specific time</UncontrolledTooltip>
          </dt>
          <dd>{supportedHelpRequestEntity.timeType}</dd>
          <dt>
            <span id="supportedHelpType">Supported Help Type</span>
          </dt>
          <dd>{supportedHelpRequestEntity.supportedHelpType}</dd>
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>{supportedHelpRequestEntity.content}</dd>
          <dt>
            <span id="otherTask">Other Task</span>
          </dt>
          <dd>{supportedHelpRequestEntity.otherTask}</dd>
          <dt>Supported</dt>
          <dd>
            {supportedHelpRequestEntity.supporteds
              ? supportedHelpRequestEntity.supporteds.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.firstName}</a>
                    {supportedHelpRequestEntity.supporteds && i === supportedHelpRequestEntity.supporteds.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Request</dt>
          <dd>{supportedHelpRequestEntity.requestId ? supportedHelpRequestEntity.requestId : ''}</dd>
        </dl>
        <Button tag={Link} to="/supported-help-request" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/supported-help-request/${supportedHelpRequestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ supportedHelpRequest }: IRootState) => ({
  supportedHelpRequestEntity: supportedHelpRequest.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SupportedHelpRequestDetail);
