import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './child-relation.reducer';
import { IChildRelation } from 'app/shared/model/child-relation.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IChildRelationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ChildRelationDetail = (props: IChildRelationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { childRelationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          ChildRelation [<b>{childRelationEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{childRelationEntity.name}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{childRelationEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/child-relation" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/child-relation/${childRelationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ childRelation }: IRootState) => ({
  childRelationEntity: childRelation.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ChildRelationDetail);
