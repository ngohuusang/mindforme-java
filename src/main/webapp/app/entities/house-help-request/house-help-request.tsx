import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import {
  byteSize,
  ICrudSearchAction,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState,
  JhiPagination,
  JhiItemCount,
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './house-help-request.reducer';
import { IHouseHelpRequest } from 'app/shared/model/house-help-request.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IHouseHelpRequestProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const HouseHelpRequest = (props: IHouseHelpRequestProps) => {
  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    if (search) {
      props.getSearchEntities(
        search,
        paginationState.activePage - 1,
        paginationState.itemsPerPage,
        `${paginationState.sort},${paginationState.order}`
      );
    } else {
      props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
    }
  };

  const startSearching = () => {
    if (search) {
      setPaginationState({
        ...paginationState,
        activePage: 1,
      });
      props.getSearchEntities(
        search,
        paginationState.activePage - 1,
        paginationState.itemsPerPage,
        `${paginationState.sort},${paginationState.order}`
      );
    }
  };

  const clear = () => {
    setSearch('');
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    props.getEntities();
  };

  const handleSearch = event => setSearch(event.target.value);

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort, search]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { houseHelpRequestList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="house-help-request-heading">
        House Help Requests
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new House Help Request
        </Link>
      </h2>
      <Row>
        <Col sm="12">
          <AvForm onSubmit={startSearching}>
            <AvGroup>
              <InputGroup>
                <AvInput type="text" name="search" value={search} onChange={handleSearch} placeholder="Search" />
                <Button className="input-group-addon">
                  <FontAwesomeIcon icon="search" />
                </Button>
                <Button type="reset" className="input-group-addon" onClick={clear}>
                  <FontAwesomeIcon icon="trash" />
                </Button>
              </InputGroup>
            </AvGroup>
          </AvForm>
        </Col>
      </Row>
      <div className="table-responsive">
        {houseHelpRequestList && houseHelpRequestList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('services')}>
                  Services <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cleaningTime')}>
                  Cleaning Time <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cleaningFromTime')}>
                  Cleaning From Time <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cleaningToTime')}>
                  Cleaning To Time <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cleaningEquipment')}>
                  Cleaning Equipment <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cleaningDescription')}>
                  Cleaning Description <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cookingFromTime')}>
                  Cooking From Time <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cookingToTime')}>
                  Cooking To Time <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cookingServes')}>
                  Cooking Serves <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cookingData')}>
                  Cooking Data <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pickupType')}>
                  Pickup Type <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('houseMindingDetail')}>
                  House Minding Detail <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mailFromDate')}>
                  Mail From Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mailToDate')}>
                  Mail To Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mailAfter')}>
                  Mail After <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mailCollectionDays')}>
                  Mail Collection Days <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('otherDescription')}>
                  Other Description <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('otherHours')}>
                  Other Hours <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('otherFromTime')}>
                  Other From Time <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('otherToTime')}>
                  Other To Time <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('otherEquipment')}>
                  Other Equipment <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('provideFor')}>
                  Provide For <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('provideType')}>
                  Provide Type <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Request <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {houseHelpRequestList.map((houseHelpRequest, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${houseHelpRequest.id}`} color="link" size="sm">
                      {houseHelpRequest.id}
                    </Button>
                  </td>
                  <td>{houseHelpRequest.services}</td>
                  <td>{houseHelpRequest.cleaningTime}</td>
                  <td>
                    {houseHelpRequest.cleaningFromTime ? (
                      <TextFormat type="date" value={houseHelpRequest.cleaningFromTime} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {houseHelpRequest.cleaningToTime ? (
                      <TextFormat type="date" value={houseHelpRequest.cleaningToTime} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{houseHelpRequest.cleaningEquipment}</td>
                  <td>{houseHelpRequest.cleaningDescription}</td>
                  <td>
                    {houseHelpRequest.cookingFromTime ? (
                      <TextFormat type="date" value={houseHelpRequest.cookingFromTime} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {houseHelpRequest.cookingToTime ? (
                      <TextFormat type="date" value={houseHelpRequest.cookingToTime} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{houseHelpRequest.cookingServes}</td>
                  <td>{houseHelpRequest.cookingData}</td>
                  <td>{houseHelpRequest.pickupType}</td>
                  <td>{houseHelpRequest.houseMindingDetail}</td>
                  <td>
                    {houseHelpRequest.mailFromDate ? (
                      <TextFormat type="date" value={houseHelpRequest.mailFromDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {houseHelpRequest.mailToDate ? (
                      <TextFormat type="date" value={houseHelpRequest.mailToDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{houseHelpRequest.mailAfter}</td>
                  <td>{houseHelpRequest.mailCollectionDays}</td>
                  <td>{houseHelpRequest.otherDescription}</td>
                  <td>{houseHelpRequest.otherHours}</td>
                  <td>
                    {houseHelpRequest.otherFromTime ? (
                      <TextFormat type="date" value={houseHelpRequest.otherFromTime} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {houseHelpRequest.otherToTime ? (
                      <TextFormat type="date" value={houseHelpRequest.otherToTime} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{houseHelpRequest.otherEquipment}</td>
                  <td>{houseHelpRequest.provideFor}</td>
                  <td>{houseHelpRequest.provideType}</td>
                  <td>
                    {houseHelpRequest.requestId ? (
                      <Link to={`help-request/${houseHelpRequest.requestId}`}>{houseHelpRequest.requestId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${houseHelpRequest.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${houseHelpRequest.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${houseHelpRequest.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No House Help Requests found</div>
        )}
      </div>
      {props.totalItems ? (
        <div className={houseHelpRequestList && houseHelpRequestList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ houseHelpRequest }: IRootState) => ({
  houseHelpRequestList: houseHelpRequest.entities,
  loading: houseHelpRequest.loading,
  totalItems: houseHelpRequest.totalItems,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HouseHelpRequest);
