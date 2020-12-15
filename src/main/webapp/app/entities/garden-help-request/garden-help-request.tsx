import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import {
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
import { getSearchEntities, getEntities } from './garden-help-request.reducer';
import { IGardenHelpRequest } from 'app/shared/model/garden-help-request.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IGardenHelpRequestProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const GardenHelpRequest = (props: IGardenHelpRequestProps) => {
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

  const { gardenHelpRequestList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="garden-help-request-heading">
        Garden Help Requests
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Garden Help Request
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
        {gardenHelpRequestList && gardenHelpRequestList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('totalHelpTime')}>
                  Total Help Time <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dateFrom')}>
                  Date From <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dateTo')}>
                  Date To <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('timeFrom')}>
                  Time From <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('timeTo')}>
                  Time To <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('services')}>
                  Services <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('edgeTrimming')}>
                  Edge Trimming <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mowingTime')}>
                  Mowing Time <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mowingEquipment')}>
                  Mowing Equipment <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lawnTime')}>
                  Lawn Time <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lawnEquipment')}>
                  Lawn Equipment <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('rubbishLoad')}>
                  Rubbish Load <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('rubbishLoadType')}>
                  Rubbish Load Type <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('otherDescription')}>
                  Other Description <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('otherHours')}>
                  Other Hours <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('otherEquipment')}>
                  Other Equipment <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('provideType')}>
                  Provide Type <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('provideFor')}>
                  Provide For <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Request <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {gardenHelpRequestList.map((gardenHelpRequest, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${gardenHelpRequest.id}`} color="link" size="sm">
                      {gardenHelpRequest.id}
                    </Button>
                  </td>
                  <td>{gardenHelpRequest.totalHelpTime}</td>
                  <td>
                    {gardenHelpRequest.dateFrom ? (
                      <TextFormat type="date" value={gardenHelpRequest.dateFrom} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {gardenHelpRequest.dateTo ? <TextFormat type="date" value={gardenHelpRequest.dateTo} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{gardenHelpRequest.timeFrom}</td>
                  <td>{gardenHelpRequest.timeTo}</td>
                  <td>{gardenHelpRequest.services}</td>
                  <td>{gardenHelpRequest.edgeTrimming ? 'true' : 'false'}</td>
                  <td>{gardenHelpRequest.mowingTime}</td>
                  <td>{gardenHelpRequest.mowingEquipment}</td>
                  <td>{gardenHelpRequest.lawnTime}</td>
                  <td>{gardenHelpRequest.lawnEquipment}</td>
                  <td>{gardenHelpRequest.rubbishLoad}</td>
                  <td>{gardenHelpRequest.rubbishLoadType}</td>
                  <td>{gardenHelpRequest.otherDescription}</td>
                  <td>{gardenHelpRequest.otherHours}</td>
                  <td>{gardenHelpRequest.otherEquipment}</td>
                  <td>{gardenHelpRequest.provideType}</td>
                  <td>{gardenHelpRequest.provideFor}</td>
                  <td>
                    {gardenHelpRequest.requestId ? (
                      <Link to={`help-request/${gardenHelpRequest.requestId}`}>{gardenHelpRequest.requestId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${gardenHelpRequest.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${gardenHelpRequest.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${gardenHelpRequest.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Garden Help Requests found</div>
        )}
      </div>
      {props.totalItems ? (
        <div className={gardenHelpRequestList && gardenHelpRequestList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ gardenHelpRequest }: IRootState) => ({
  gardenHelpRequestList: gardenHelpRequest.entities,
  loading: gardenHelpRequest.loading,
  totalItems: gardenHelpRequest.totalItems,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GardenHelpRequest);
