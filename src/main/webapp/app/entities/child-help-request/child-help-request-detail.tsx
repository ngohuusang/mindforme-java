import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './child-help-request.reducer';
import { IChildHelpRequest } from 'app/shared/model/child-help-request.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IChildHelpRequestDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ChildHelpRequestDetail = (props: IChildHelpRequestDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { childHelpRequestEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          ChildHelpRequest [<b>{childHelpRequestEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="totalHelpTime">Total Help Time</span>
          </dt>
          <dd>{childHelpRequestEntity.totalHelpTime}</dd>
          <dt>
            <span id="dateFrom">Date From</span>
          </dt>
          <dd>
            {childHelpRequestEntity.dateFrom ? (
              <TextFormat value={childHelpRequestEntity.dateFrom} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dateTo">Date To</span>
          </dt>
          <dd>
            {childHelpRequestEntity.dateTo ? (
              <TextFormat value={childHelpRequestEntity.dateTo} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="timeFrom">Time From</span>
          </dt>
          <dd>{childHelpRequestEntity.timeFrom}</dd>
          <dt>
            <span id="timeTo">Time To</span>
          </dt>
          <dd>{childHelpRequestEntity.timeTo}</dd>
          <dt>
            <span id="timeType">Time Type</span>
            <UncontrolledTooltip target="timeType">1= hours, 2= specific time</UncontrolledTooltip>
          </dt>
          <dd>{childHelpRequestEntity.timeType}</dd>
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>{childHelpRequestEntity.content}</dd>
          <dt>
            <span id="otherTask">Other Task</span>
          </dt>
          <dd>{childHelpRequestEntity.otherTask}</dd>
          <dt>Child</dt>
          <dd>
            {childHelpRequestEntity.children
              ? childHelpRequestEntity.children.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.firstName}</a>
                    {childHelpRequestEntity.children && i === childHelpRequestEntity.children.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/child-help-request" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/child-help-request/${childHelpRequestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ childHelpRequest }: IRootState) => ({
  childHelpRequestEntity: childHelpRequest.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ChildHelpRequestDetail);
