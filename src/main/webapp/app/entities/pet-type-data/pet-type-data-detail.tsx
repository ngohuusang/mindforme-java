import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './pet-type-data.reducer';
import { IPetTypeData } from 'app/shared/model/pet-type-data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPetTypeDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PetTypeDataDetail = (props: IPetTypeDataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { petTypeDataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          PetTypeData [<b>{petTypeDataEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>{petTypeDataEntity.content}</dd>
          <dt>Lang</dt>
          <dd>{petTypeDataEntity.langName ? petTypeDataEntity.langName : ''}</dd>
          <dt>Pet Type</dt>
          <dd>{petTypeDataEntity.petTypeName ? petTypeDataEntity.petTypeName : ''}</dd>
        </dl>
        <Button tag={Link} to="/pet-type-data" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pet-type-data/${petTypeDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ petTypeData }: IRootState) => ({
  petTypeDataEntity: petTypeData.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PetTypeDataDetail);
