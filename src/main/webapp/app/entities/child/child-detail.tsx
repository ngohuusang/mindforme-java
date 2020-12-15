import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './child.reducer';
import { IChild } from 'app/shared/model/child.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IChildDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ChildDetail = (props: IChildDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { childEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Child [<b>{childEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="firstName">First Name</span>
          </dt>
          <dd>{childEntity.firstName}</dd>
          <dt>
            <span id="lastName">Last Name</span>
          </dt>
          <dd>{childEntity.lastName}</dd>
          <dt>
            <span id="imageUrl">Image Url</span>
          </dt>
          <dd>{childEntity.imageUrl}</dd>
          <dt>
            <span id="birthday">Birthday</span>
          </dt>
          <dd>{childEntity.birthday ? <TextFormat value={childEntity.birthday} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{childEntity.status}</dd>
          <dt>Relation</dt>
          <dd>{childEntity.relationName ? childEntity.relationName : ''}</dd>
          <dt>Interest</dt>
          <dd>
            {childEntity.interests
              ? childEntity.interests.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {childEntity.interests && i === childEntity.interests.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Allergy</dt>
          <dd>
            {childEntity.allergies
              ? childEntity.allergies.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {childEntity.allergies && i === childEntity.allergies.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Favourite Food</dt>
          <dd>
            {childEntity.favouriteFoods
              ? childEntity.favouriteFoods.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {childEntity.favouriteFoods && i === childEntity.favouriteFoods.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Medical Condition</dt>
          <dd>
            {childEntity.medicalConditions
              ? childEntity.medicalConditions.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {childEntity.medicalConditions && i === childEntity.medicalConditions.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Family</dt>
          <dd>{childEntity.familyName ? childEntity.familyName : ''}</dd>
          <dt>Request</dt>
          <dd>{childEntity.requestId ? childEntity.requestId : ''}</dd>
        </dl>
        <Button tag={Link} to="/child" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/child/${childEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ child }: IRootState) => ({
  childEntity: child.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ChildDetail);
