import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './pet-breed.reducer';
import { IPetBreed } from 'app/shared/model/pet-breed.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPetBreedDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PetBreedDetail = (props: IPetBreedDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { petBreedEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          PetBreed [<b>{petBreedEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{petBreedEntity.name}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{petBreedEntity.status}</dd>
          <dt>Pet Type</dt>
          <dd>{petBreedEntity.petTypeName ? petBreedEntity.petTypeName : ''}</dd>
        </dl>
        <Button tag={Link} to="/pet-breed" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pet-breed/${petBreedEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ petBreed }: IRootState) => ({
  petBreedEntity: petBreed.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PetBreedDetail);
