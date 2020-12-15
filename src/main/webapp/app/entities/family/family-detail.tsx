import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './family.reducer';
import { IFamily } from 'app/shared/model/family.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFamilyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FamilyDetail = (props: IFamilyDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { familyEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Family [<b>{familyEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{familyEntity.name}</dd>
          <dt>
            <span id="karmaPoints">Karma Points</span>
          </dt>
          <dd>{familyEntity.karmaPoints}</dd>
          <dt>
            <span id="overview">Overview</span>
          </dt>
          <dd>{familyEntity.overview}</dd>
          <dt>
            <span id="rating">Rating</span>
          </dt>
          <dd>{familyEntity.rating}</dd>
          <dt>
            <span id="imageUrl">Image Url</span>
          </dt>
          <dd>{familyEntity.imageUrl}</dd>
          <dt>
            <span id="planExpire">Plan Expire</span>
          </dt>
          <dd>{familyEntity.planExpire ? <TextFormat value={familyEntity.planExpire} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="rule">Rule</span>
          </dt>
          <dd>{familyEntity.rule}</dd>
          <dt>
            <span id="freeMonths">Free Months</span>
          </dt>
          <dd>{familyEntity.freeMonths}</dd>
          <dt>
            <span id="otherVerify">Other Verify</span>
          </dt>
          <dd>{familyEntity.otherVerify}</dd>
          <dt>
            <span id="kc25Paid">Kc 25 Paid</span>
          </dt>
          <dd>{familyEntity.kc25Paid ? 'true' : 'false'}</dd>
          <dt>
            <span id="kc75Paid">Kc 75 Paid</span>
          </dt>
          <dd>{familyEntity.kc75Paid ? 'true' : 'false'}</dd>
          <dt>
            <span id="privacyFamily">Privacy Family</span>
          </dt>
          <dd>{familyEntity.privacyFamily}</dd>
          <dt>
            <span id="shareToFacebook">Share To Facebook</span>
          </dt>
          <dd>{familyEntity.shareToFacebook ? 'true' : 'false'}</dd>
          <dt>
            <span id="privacyPersonal">Privacy Personal</span>
          </dt>
          <dd>{familyEntity.privacyPersonal}</dd>
          <dt>
            <span id="addToCalendar">Add To Calendar</span>
          </dt>
          <dd>{familyEntity.addToCalendar ? 'true' : 'false'}</dd>
          <dt>
            <span id="remindEvents">Remind Events</span>
          </dt>
          <dd>{familyEntity.remindEvents ? 'true' : 'false'}</dd>
          <dt>
            <span id="notifyFacebook">Notify Facebook</span>
          </dt>
          <dd>{familyEntity.notifyFacebook ? 'true' : 'false'}</dd>
          <dt>
            <span id="distanceRequest">Distance Request</span>
          </dt>
          <dd>{familyEntity.distanceRequest}</dd>
          <dt>
            <span id="distanceUnit">Distance Unit</span>
          </dt>
          <dd>{familyEntity.distanceUnit}</dd>
          <dt>
            <span id="mailRequestFriend">Mail Request Friend</span>
          </dt>
          <dd>{familyEntity.mailRequestFriend}</dd>
          <dt>
            <span id="mailRequestFriendOfFriend">Mail Request Friend Of Friend</span>
          </dt>
          <dd>{familyEntity.mailRequestFriendOfFriend}</dd>
          <dt>
            <span id="mailRequest">Mail Request</span>
          </dt>
          <dd>{familyEntity.mailRequest}</dd>
          <dt>Address</dt>
          <dd>{familyEntity.addressAddress ? familyEntity.addressAddress : ''}</dd>
          <dt>Plan</dt>
          <dd>{familyEntity.planName ? familyEntity.planName : ''}</dd>
        </dl>
        <Button tag={Link} to="/family" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/family/${familyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ family }: IRootState) => ({
  familyEntity: family.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FamilyDetail);
