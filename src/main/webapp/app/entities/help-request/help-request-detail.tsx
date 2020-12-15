import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './help-request.reducer';
import { IHelpRequest } from 'app/shared/model/help-request.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHelpRequestDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HelpRequestDetail = (props: IHelpRequestDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { helpRequestEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          HelpRequest [<b>{helpRequestEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{helpRequestEntity.title}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{helpRequestEntity.type}</dd>
          <dt>
            <span id="acceptance">Acceptance</span>
          </dt>
          <dd>{helpRequestEntity.acceptance}</dd>
          <dt>
            <span id="requestStatus">Request Status</span>
            <UncontrolledTooltip target="requestStatus">
              N for New , A for accepted, AP for approved and c for complete.,R-rated/Completelu done
            </UncontrolledTooltip>
          </dt>
          <dd>{helpRequestEntity.requestStatus}</dd>
          <dt>
            <span id="isOffer">Is Offer</span>
          </dt>
          <dd>{helpRequestEntity.isOffer ? 'true' : 'false'}</dd>
          <dt>
            <span id="offerTo">Offer To</span>
          </dt>
          <dd>{helpRequestEntity.offerTo}</dd>
          <dt>
            <span id="message">Message</span>
          </dt>
          <dd>{helpRequestEntity.message}</dd>
          <dt>
            <span id="instruction">Instruction</span>
          </dt>
          <dd>{helpRequestEntity.instruction}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{helpRequestEntity.status}</dd>
          <dt>
            <span id="locationType">Location Type</span>
            <UncontrolledTooltip target="locationType">
              0 for at our home or your home1 for at our home2 for at your home3 for Other
            </UncontrolledTooltip>
          </dt>
          <dd>{helpRequestEntity.locationType}</dd>
          <dt>
            <span id="realStart">Real Start</span>
          </dt>
          <dd>
            {helpRequestEntity.realStart ? <TextFormat value={helpRequestEntity.realStart} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="realEnd">Real End</span>
          </dt>
          <dd>
            {helpRequestEntity.realEnd ? <TextFormat value={helpRequestEntity.realEnd} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="rating">Rating</span>
          </dt>
          <dd>{helpRequestEntity.rating}</dd>
          <dt>
            <span id="requesterComment">Requester Comment</span>
          </dt>
          <dd>{helpRequestEntity.requesterComment}</dd>
          <dt>
            <span id="helperComment">Helper Comment</span>
          </dt>
          <dd>{helpRequestEntity.helperComment}</dd>
          <dt>
            <span id="flagged">Flagged</span>
          </dt>
          <dd>{helpRequestEntity.flagged}</dd>
          <dt>
            <span id="coins">Coins</span>
          </dt>
          <dd>{helpRequestEntity.coins}</dd>
          <dt>
            <span id="bonus">Bonus</span>
          </dt>
          <dd>{helpRequestEntity.bonus}</dd>
          <dt>Help Location</dt>
          <dd>{helpRequestEntity.helpLocationId ? helpRequestEntity.helpLocationId : ''}</dd>
        </dl>
        <Button tag={Link} to="/help-request" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/help-request/${helpRequestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ helpRequest }: IRootState) => ({
  helpRequestEntity: helpRequest.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HelpRequestDetail);
