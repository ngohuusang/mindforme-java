import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './pet.reducer';
import { IPet } from 'app/shared/model/pet.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPetDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PetDetail = (props: IPetDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { petEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Pet [<b>{petEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{petEntity.name}</dd>
          <dt>
            <span id="imageUrl">Image Url</span>
          </dt>
          <dd>{petEntity.imageUrl}</dd>
          <dt>
            <span id="birthday">Birthday</span>
          </dt>
          <dd>{petEntity.birthday ? <TextFormat value={petEntity.birthday} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{petEntity.status}</dd>
          <dt>Breed</dt>
          <dd>{petEntity.breedName ? petEntity.breedName : ''}</dd>
          <dt>Pet Type</dt>
          <dd>{petEntity.petTypeName ? petEntity.petTypeName : ''}</dd>
          <dt>Feeding</dt>
          <dd>
            {petEntity.feedings
              ? petEntity.feedings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {petEntity.feedings && i === petEntity.feedings.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Rule</dt>
          <dd>
            {petEntity.rules
              ? petEntity.rules.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {petEntity.rules && i === petEntity.rules.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Allergy</dt>
          <dd>
            {petEntity.allergies
              ? petEntity.allergies.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {petEntity.allergies && i === petEntity.allergies.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Walking</dt>
          <dd>
            {petEntity.walkings
              ? petEntity.walkings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {petEntity.walkings && i === petEntity.walkings.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Family</dt>
          <dd>{petEntity.familyName ? petEntity.familyName : ''}</dd>
        </dl>
        <Button tag={Link} to="/pet" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pet/${petEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ pet }: IRootState) => ({
  petEntity: pet.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PetDetail);
