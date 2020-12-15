import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './allergy-data.reducer';
import { IAllergyData } from 'app/shared/model/allergy-data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAllergyDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AllergyDataDetail = (props: IAllergyDataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { allergyDataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          AllergyData [<b>{allergyDataEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>{allergyDataEntity.content}</dd>
          <dt>Lang</dt>
          <dd>{allergyDataEntity.langName ? allergyDataEntity.langName : ''}</dd>
          <dt>Allergy</dt>
          <dd>{allergyDataEntity.allergyName ? allergyDataEntity.allergyName : ''}</dd>
        </dl>
        <Button tag={Link} to="/allergy-data" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/allergy-data/${allergyDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ allergyData }: IRootState) => ({
  allergyDataEntity: allergyData.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AllergyDataDetail);
