import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './supported.reducer';
import { ISupported } from 'app/shared/model/supported.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISupportedDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SupportedDetail = (props: ISupportedDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { supportedEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Supported [<b>{supportedEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="firstName">First Name</span>
          </dt>
          <dd>{supportedEntity.firstName}</dd>
          <dt>
            <span id="lastName">Last Name</span>
          </dt>
          <dd>{supportedEntity.lastName}</dd>
          <dt>
            <span id="imageUrl">Image Url</span>
          </dt>
          <dd>{supportedEntity.imageUrl}</dd>
          <dt>
            <span id="birthday">Birthday</span>
          </dt>
          <dd>{supportedEntity.birthday ? <TextFormat value={supportedEntity.birthday} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{supportedEntity.status}</dd>
          <dt>Relation</dt>
          <dd>{supportedEntity.relationName ? supportedEntity.relationName : ''}</dd>
          <dt>Family</dt>
          <dd>{supportedEntity.familyName ? supportedEntity.familyName : ''}</dd>
        </dl>
        <Button tag={Link} to="/supported" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/supported/${supportedEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ supported }: IRootState) => ({
  supportedEntity: supported.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SupportedDetail);
