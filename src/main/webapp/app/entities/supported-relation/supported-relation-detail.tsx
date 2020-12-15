import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './supported-relation.reducer';
import { ISupportedRelation } from 'app/shared/model/supported-relation.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISupportedRelationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SupportedRelationDetail = (props: ISupportedRelationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { supportedRelationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          SupportedRelation [<b>{supportedRelationEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{supportedRelationEntity.name}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{supportedRelationEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/supported-relation" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/supported-relation/${supportedRelationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ supportedRelation }: IRootState) => ({
  supportedRelationEntity: supportedRelation.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SupportedRelationDetail);
