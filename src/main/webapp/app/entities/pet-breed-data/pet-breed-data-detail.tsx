import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './pet-breed-data.reducer';
import { IPetBreedData } from 'app/shared/model/pet-breed-data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPetBreedDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PetBreedDataDetail = (props: IPetBreedDataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { petBreedDataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          PetBreedData [<b>{petBreedDataEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>{petBreedDataEntity.content}</dd>
          <dt>Lang</dt>
          <dd>{petBreedDataEntity.langName ? petBreedDataEntity.langName : ''}</dd>
          <dt>Pet Breed</dt>
          <dd>{petBreedDataEntity.petBreedName ? petBreedDataEntity.petBreedName : ''}</dd>
        </dl>
        <Button tag={Link} to="/pet-breed-data" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pet-breed-data/${petBreedDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ petBreedData }: IRootState) => ({
  petBreedDataEntity: petBreedData.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PetBreedDataDetail);
