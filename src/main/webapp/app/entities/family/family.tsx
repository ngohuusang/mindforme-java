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
import { getSearchEntities, getEntities } from './family.reducer';
import { IFamily } from 'app/shared/model/family.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IFamilyProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Family = (props: IFamilyProps) => {
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

  const { familyList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="family-heading">
        Families
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Family
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
        {familyList && familyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  Name <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('karmaPoints')}>
                  Karma Points <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('overview')}>
                  Overview <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('rating')}>
                  Rating <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('imageUrl')}>
                  Image Url <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('planExpire')}>
                  Plan Expire <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('rule')}>
                  Rule <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('freeMonths')}>
                  Free Months <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('otherVerify')}>
                  Other Verify <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('kc25Paid')}>
                  Kc 25 Paid <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('kc75Paid')}>
                  Kc 75 Paid <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('privacyFamily')}>
                  Privacy Family <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('shareToFacebook')}>
                  Share To Facebook <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('privacyPersonal')}>
                  Privacy Personal <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addToCalendar')}>
                  Add To Calendar <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('remindEvents')}>
                  Remind Events <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('notifyFacebook')}>
                  Notify Facebook <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('distanceRequest')}>
                  Distance Request <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('distanceUnit')}>
                  Distance Unit <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mailRequestFriend')}>
                  Mail Request Friend <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mailRequestFriendOfFriend')}>
                  Mail Request Friend Of Friend <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mailRequest')}>
                  Mail Request <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Address <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Plan <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {familyList.map((family, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${family.id}`} color="link" size="sm">
                      {family.id}
                    </Button>
                  </td>
                  <td>{family.name}</td>
                  <td>{family.karmaPoints}</td>
                  <td>{family.overview}</td>
                  <td>{family.rating}</td>
                  <td>{family.imageUrl}</td>
                  <td>{family.planExpire ? <TextFormat type="date" value={family.planExpire} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{family.rule}</td>
                  <td>{family.freeMonths}</td>
                  <td>{family.otherVerify}</td>
                  <td>{family.kc25Paid ? 'true' : 'false'}</td>
                  <td>{family.kc75Paid ? 'true' : 'false'}</td>
                  <td>{family.privacyFamily}</td>
                  <td>{family.shareToFacebook ? 'true' : 'false'}</td>
                  <td>{family.privacyPersonal}</td>
                  <td>{family.addToCalendar ? 'true' : 'false'}</td>
                  <td>{family.remindEvents ? 'true' : 'false'}</td>
                  <td>{family.notifyFacebook ? 'true' : 'false'}</td>
                  <td>{family.distanceRequest}</td>
                  <td>{family.distanceUnit}</td>
                  <td>{family.mailRequestFriend}</td>
                  <td>{family.mailRequestFriendOfFriend}</td>
                  <td>{family.mailRequest}</td>
                  <td>{family.addressAddress ? <Link to={`address/${family.addressId}`}>{family.addressAddress}</Link> : ''}</td>
                  <td>{family.planName ? <Link to={`plan/${family.planId}`}>{family.planName}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${family.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${family.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${family.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Families found</div>
        )}
      </div>
      {props.totalItems ? (
        <div className={familyList && familyList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ family }: IRootState) => ({
  familyList: family.entities,
  loading: family.loading,
  totalItems: family.totalItems,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Family);
