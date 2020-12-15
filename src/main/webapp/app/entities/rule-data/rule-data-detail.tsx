import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './rule-data.reducer';
import { IRuleData } from 'app/shared/model/rule-data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRuleDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RuleDataDetail = (props: IRuleDataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { ruleDataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          RuleData [<b>{ruleDataEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>{ruleDataEntity.content}</dd>
          <dt>Lang</dt>
          <dd>{ruleDataEntity.langName ? ruleDataEntity.langName : ''}</dd>
          <dt>Rule</dt>
          <dd>{ruleDataEntity.ruleName ? ruleDataEntity.ruleName : ''}</dd>
        </dl>
        <Button tag={Link} to="/rule-data" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rule-data/${ruleDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ ruleData }: IRootState) => ({
  ruleDataEntity: ruleData.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RuleDataDetail);
