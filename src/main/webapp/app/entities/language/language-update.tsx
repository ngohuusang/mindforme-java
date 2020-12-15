import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './language.reducer';
import { ILanguage } from 'app/shared/model/language.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ILanguageUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const LanguageUpdate = (props: ILanguageUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { languageEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/language' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...languageEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mindformeApp.language.home.createOrEditLabel">Create or edit a Language</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : languageEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="language-id">ID</Label>
                  <AvInput id="language-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codeLabel" for="language-code">
                  Code
                </Label>
                <AvField id="language-code" type="text" name="code" />
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="language-name">
                  Name
                </Label>
                <AvField id="language-name" type="text" name="name" />
              </AvGroup>
              <AvGroup check>
                <Label id="isDefaultLabel">
                  <AvInput id="language-isDefault" type="checkbox" className="form-check-input" name="isDefault" />
                  Is Default
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="activeLabel">
                  <AvInput id="language-active" type="checkbox" className="form-check-input" name="active" />
                  Active
                </Label>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/language" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  languageEntity: storeState.language.entity,
  loading: storeState.language.loading,
  updating: storeState.language.updating,
  updateSuccess: storeState.language.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(LanguageUpdate);
