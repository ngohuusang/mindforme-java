import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './garden-help-request.reducer';
import { IGardenHelpRequest } from 'app/shared/model/garden-help-request.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGardenHelpRequestDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GardenHelpRequestDetail = (props: IGardenHelpRequestDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { gardenHelpRequestEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          GardenHelpRequest [<b>{gardenHelpRequestEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="totalHelpTime">Total Help Time</span>
          </dt>
          <dd>{gardenHelpRequestEntity.totalHelpTime}</dd>
          <dt>
            <span id="dateFrom">Date From</span>
          </dt>
          <dd>
            {gardenHelpRequestEntity.dateFrom ? (
              <TextFormat value={gardenHelpRequestEntity.dateFrom} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dateTo">Date To</span>
          </dt>
          <dd>
            {gardenHelpRequestEntity.dateTo ? (
              <TextFormat value={gardenHelpRequestEntity.dateTo} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="timeFrom">Time From</span>
          </dt>
          <dd>{gardenHelpRequestEntity.timeFrom}</dd>
          <dt>
            <span id="timeTo">Time To</span>
          </dt>
          <dd>{gardenHelpRequestEntity.timeTo}</dd>
          <dt>
            <span id="services">Services</span>
          </dt>
          <dd>{gardenHelpRequestEntity.services}</dd>
          <dt>
            <span id="edgeTrimming">Edge Trimming</span>
          </dt>
          <dd>{gardenHelpRequestEntity.edgeTrimming ? 'true' : 'false'}</dd>
          <dt>
            <span id="mowingTime">Mowing Time</span>
          </dt>
          <dd>{gardenHelpRequestEntity.mowingTime}</dd>
          <dt>
            <span id="mowingEquipment">Mowing Equipment</span>
          </dt>
          <dd>{gardenHelpRequestEntity.mowingEquipment}</dd>
          <dt>
            <span id="lawnTime">Lawn Time</span>
          </dt>
          <dd>{gardenHelpRequestEntity.lawnTime}</dd>
          <dt>
            <span id="lawnEquipment">Lawn Equipment</span>
          </dt>
          <dd>{gardenHelpRequestEntity.lawnEquipment}</dd>
          <dt>
            <span id="rubbishLoad">Rubbish Load</span>
          </dt>
          <dd>{gardenHelpRequestEntity.rubbishLoad}</dd>
          <dt>
            <span id="rubbishLoadType">Rubbish Load Type</span>
          </dt>
          <dd>{gardenHelpRequestEntity.rubbishLoadType}</dd>
          <dt>
            <span id="otherDescription">Other Description</span>
          </dt>
          <dd>{gardenHelpRequestEntity.otherDescription}</dd>
          <dt>
            <span id="otherHours">Other Hours</span>
          </dt>
          <dd>{gardenHelpRequestEntity.otherHours}</dd>
          <dt>
            <span id="otherEquipment">Other Equipment</span>
          </dt>
          <dd>{gardenHelpRequestEntity.otherEquipment}</dd>
          <dt>
            <span id="provideType">Provide Type</span>
          </dt>
          <dd>{gardenHelpRequestEntity.provideType}</dd>
          <dt>
            <span id="provideFor">Provide For</span>
          </dt>
          <dd>{gardenHelpRequestEntity.provideFor}</dd>
          <dt>Request</dt>
          <dd>{gardenHelpRequestEntity.requestId ? gardenHelpRequestEntity.requestId : ''}</dd>
        </dl>
        <Button tag={Link} to="/garden-help-request" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/garden-help-request/${gardenHelpRequestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ gardenHelpRequest }: IRootState) => ({
  gardenHelpRequestEntity: gardenHelpRequest.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GardenHelpRequestDetail);
