import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './family-gallery.reducer';
import { IFamilyGallery } from 'app/shared/model/family-gallery.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFamilyGalleryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FamilyGalleryDetail = (props: IFamilyGalleryDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { familyGalleryEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          FamilyGallery [<b>{familyGalleryEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="imageUrl">Image Url</span>
          </dt>
          <dd>{familyGalleryEntity.imageUrl}</dd>
          <dt>
            <span id="sortOrder">Sort Order</span>
          </dt>
          <dd>{familyGalleryEntity.sortOrder}</dd>
          <dt>
            <span id="isDefault">Is Default</span>
          </dt>
          <dd>{familyGalleryEntity.isDefault ? 'true' : 'false'}</dd>
          <dt>
            <span id="privacy">Privacy</span>
          </dt>
          <dd>{familyGalleryEntity.privacy}</dd>
          <dt>Family</dt>
          <dd>{familyGalleryEntity.familyName ? familyGalleryEntity.familyName : ''}</dd>
        </dl>
        <Button tag={Link} to="/family-gallery" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/family-gallery/${familyGalleryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ familyGallery }: IRootState) => ({
  familyGalleryEntity: familyGallery.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FamilyGalleryDetail);
