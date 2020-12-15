import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './house-help-request.reducer';
import { IHouseHelpRequest } from 'app/shared/model/house-help-request.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHouseHelpRequestDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HouseHelpRequestDetail = (props: IHouseHelpRequestDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { houseHelpRequestEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          HouseHelpRequest [<b>{houseHelpRequestEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="services">Services</span>
          </dt>
          <dd>{houseHelpRequestEntity.services}</dd>
          <dt>
            <span id="cleaningTime">Cleaning Time</span>
          </dt>
          <dd>{houseHelpRequestEntity.cleaningTime}</dd>
          <dt>
            <span id="cleaningFromTime">Cleaning From Time</span>
          </dt>
          <dd>
            {houseHelpRequestEntity.cleaningFromTime ? (
              <TextFormat value={houseHelpRequestEntity.cleaningFromTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="cleaningToTime">Cleaning To Time</span>
          </dt>
          <dd>
            {houseHelpRequestEntity.cleaningToTime ? (
              <TextFormat value={houseHelpRequestEntity.cleaningToTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="cleaningEquipment">Cleaning Equipment</span>
          </dt>
          <dd>{houseHelpRequestEntity.cleaningEquipment}</dd>
          <dt>
            <span id="cleaningDescription">Cleaning Description</span>
          </dt>
          <dd>{houseHelpRequestEntity.cleaningDescription}</dd>
          <dt>
            <span id="cookingFromTime">Cooking From Time</span>
          </dt>
          <dd>
            {houseHelpRequestEntity.cookingFromTime ? (
              <TextFormat value={houseHelpRequestEntity.cookingFromTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="cookingToTime">Cooking To Time</span>
          </dt>
          <dd>
            {houseHelpRequestEntity.cookingToTime ? (
              <TextFormat value={houseHelpRequestEntity.cookingToTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="cookingServes">Cooking Serves</span>
          </dt>
          <dd>{houseHelpRequestEntity.cookingServes}</dd>
          <dt>
            <span id="cookingData">Cooking Data</span>
          </dt>
          <dd>{houseHelpRequestEntity.cookingData}</dd>
          <dt>
            <span id="pickupType">Pickup Type</span>
          </dt>
          <dd>{houseHelpRequestEntity.pickupType}</dd>
          <dt>
            <span id="houseMindingDetail">House Minding Detail</span>
          </dt>
          <dd>{houseHelpRequestEntity.houseMindingDetail}</dd>
          <dt>
            <span id="mailFromDate">Mail From Date</span>
          </dt>
          <dd>
            {houseHelpRequestEntity.mailFromDate ? (
              <TextFormat value={houseHelpRequestEntity.mailFromDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="mailToDate">Mail To Date</span>
          </dt>
          <dd>
            {houseHelpRequestEntity.mailToDate ? (
              <TextFormat value={houseHelpRequestEntity.mailToDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="mailAfter">Mail After</span>
          </dt>
          <dd>{houseHelpRequestEntity.mailAfter}</dd>
          <dt>
            <span id="mailCollectionDays">Mail Collection Days</span>
          </dt>
          <dd>{houseHelpRequestEntity.mailCollectionDays}</dd>
          <dt>
            <span id="otherDescription">Other Description</span>
          </dt>
          <dd>{houseHelpRequestEntity.otherDescription}</dd>
          <dt>
            <span id="otherHours">Other Hours</span>
          </dt>
          <dd>{houseHelpRequestEntity.otherHours}</dd>
          <dt>
            <span id="otherFromTime">Other From Time</span>
          </dt>
          <dd>
            {houseHelpRequestEntity.otherFromTime ? (
              <TextFormat value={houseHelpRequestEntity.otherFromTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="otherToTime">Other To Time</span>
          </dt>
          <dd>
            {houseHelpRequestEntity.otherToTime ? (
              <TextFormat value={houseHelpRequestEntity.otherToTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="otherEquipment">Other Equipment</span>
          </dt>
          <dd>{houseHelpRequestEntity.otherEquipment}</dd>
          <dt>
            <span id="provideFor">Provide For</span>
          </dt>
          <dd>{houseHelpRequestEntity.provideFor}</dd>
          <dt>
            <span id="provideType">Provide Type</span>
          </dt>
          <dd>{houseHelpRequestEntity.provideType}</dd>
          <dt>Request</dt>
          <dd>{houseHelpRequestEntity.requestId ? houseHelpRequestEntity.requestId : ''}</dd>
        </dl>
        <Button tag={Link} to="/house-help-request" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/house-help-request/${houseHelpRequestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ houseHelpRequest }: IRootState) => ({
  houseHelpRequestEntity: houseHelpRequest.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HouseHelpRequestDetail);
