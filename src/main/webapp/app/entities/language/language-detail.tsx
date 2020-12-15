import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './language.reducer';
import { ILanguage } from 'app/shared/model/language.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILanguageDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const LanguageDetail = (props: ILanguageDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { languageEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Language [<b>{languageEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{languageEntity.code}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{languageEntity.name}</dd>
          <dt>
            <span id="isDefault">Is Default</span>
          </dt>
          <dd>{languageEntity.isDefault ? 'true' : 'false'}</dd>
          <dt>
            <span id="active">Active</span>
          </dt>
          <dd>{languageEntity.active ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/language" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/language/${languageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ language }: IRootState) => ({
  languageEntity: language.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(LanguageDetail);
