import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IFavouriteFoodData } from 'app/shared/model/favourite-food-data.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './favourite-food-data.reducer';

export interface IFavouriteFoodDataDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FavouriteFoodDataDeleteDialog = (props: IFavouriteFoodDataDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/favourite-food-data' + props.location.search);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.favouriteFoodDataEntity.id);
  };

  const { favouriteFoodDataEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>Confirm delete operation</ModalHeader>
      <ModalBody id="mindformeApp.favouriteFoodData.delete.question">Are you sure you want to delete this FavouriteFoodData?</ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button id="m-4-m-confirm-delete-favouriteFoodData" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Delete
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ favouriteFoodData }: IRootState) => ({
  favouriteFoodDataEntity: favouriteFoodData.entity,
  updateSuccess: favouriteFoodData.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FavouriteFoodDataDeleteDialog);
