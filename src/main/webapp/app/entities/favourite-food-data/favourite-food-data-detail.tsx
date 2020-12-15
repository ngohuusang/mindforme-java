import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './favourite-food-data.reducer';
import { IFavouriteFoodData } from 'app/shared/model/favourite-food-data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFavouriteFoodDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FavouriteFoodDataDetail = (props: IFavouriteFoodDataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { favouriteFoodDataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          FavouriteFoodData [<b>{favouriteFoodDataEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>{favouriteFoodDataEntity.content}</dd>
          <dt>Lang</dt>
          <dd>{favouriteFoodDataEntity.langName ? favouriteFoodDataEntity.langName : ''}</dd>
          <dt>Favourite Food</dt>
          <dd>{favouriteFoodDataEntity.favouriteFoodName ? favouriteFoodDataEntity.favouriteFoodName : ''}</dd>
        </dl>
        <Button tag={Link} to="/favourite-food-data" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/favourite-food-data/${favouriteFoodDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ favouriteFoodData }: IRootState) => ({
  favouriteFoodDataEntity: favouriteFoodData.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FavouriteFoodDataDetail);
