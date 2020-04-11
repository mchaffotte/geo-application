import React, { useState, useEffect } from 'react';
import {
  CircularProgress,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  TableSortLabel,
  TablePagination,
  makeStyles,
} from '@material-ui/core';
import { useTranslation } from 'react-i18next';

import TablePaginationActions from './TablePaginationActions';
import getCountries from '../api/countriesApi';
import useAlert from '../components/alert/useAlert';

const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
  },
  paper: {
    marginTop: theme.spacing(3),
    width: '100%',
    overflowX: 'auto',
    marginBottom: theme.spacing(2),
  },
  table: {
    minWidth: 650,
  },
  visuallyHidden: {
    border: 0,
    clip: 'rect(0 0 0 0)',
    height: 1,
    margin: -1,
    overflow: 'hidden',
    padding: 0,
    position: 'absolute',
    top: 20,
    width: 1,
  },
}));

const Countries = () => {
  const [countries, setCountries] = useState([]);
  const [total, setTotal] = useState(0);
  const [status, setStatus] = useState('loading');

  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [order, setOrder] = useState('asc');
  const [orderBy, setOrderBy] = useState('code');

  const { error } = useAlert();

  useEffect(() => {
    const fetchData = () => {
      let didCancel = false;
      setStatus('loading');
      const offset = page * rowsPerPage + 1;
      const direction = order === 'desc' ? '-' : '';
      const sort = `${direction}${orderBy}`;

      getCountries(offset, rowsPerPage, sort)
        .then((response) => {
          if (!didCancel) {
            setCountries(response.data.resources);
            setTotal(response.data.total);
            setStatus('success');
          }
        })
        .catch((err) => {
          error(`${err}`);
          if (err.response) {
            console.log(err.response.data);
            console.log(err.response.status);
            console.log(err.response.headers);
          } else if (err.request) {
            console.log(err.request);
          } else {
            console.log('Error', err.message);
          }
          if (!didCancel) {
            setStatus('error');
          }
        });
      return () => {
        didCancel = true;
      };
    };
    fetchData();
  }, [error, order, orderBy, page, rowsPerPage]);

  const classes = useStyles();
  const { t } = useTranslation();

  const handleRequestSort = (_, property) => {
    const isDesc = orderBy === property && order === 'desc';
    setOrder(isDesc ? 'asc' : 'desc');
    setOrderBy(property);
  };

  const handleChangePage = (_, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  const createSortHandler = (property) => (event) => {
    handleRequestSort(event, property);
  };

  if (status === 'loading') {
    return <CircularProgress />;
  }

  if (status === 'error') {
    return <div>Something went wrong ...</div>;
  }

  return (
    <div className={classes.root}>
      <Paper className={classes.paper}>
        <Table className={classes.table} size="small">
          <TableHead>
            <TableRow>
              <TableCell
                key="code"
                sortDirection={orderBy === 'code' ? order : false}
              >
                <TableSortLabel
                  active={orderBy === 'code'}
                  direction={order}
                  onClick={createSortHandler('code')}
                >
                  {t('model.country.code')}
                  {orderBy === 'code' ? (
                    <span className={classes.visuallyHidden}>
                      {order === 'desc'
                        ? 'sorted descending'
                        : 'sorted ascending'}
                    </span>
                  ) : null}
                </TableSortLabel>
              </TableCell>
              <TableCell
                key="name"
                sortDirection={orderBy === 'name' ? order : false}
              >
                <TableSortLabel
                  active={orderBy === 'name'}
                  direction={order}
                  onClick={createSortHandler('name')}
                >
                  {t('model.country.name')}
                  {orderBy === 'name' ? (
                    <span className={classes.visuallyHidden}>
                      {order === 'desc'
                        ? 'sorted descending'
                        : 'sorted ascending'}
                    </span>
                  ) : null}
                </TableSortLabel>
              </TableCell>
              <TableCell key={'total-area'} align="right">
                {t('model.country.total-area')}
              </TableCell>
              <TableCell key={'capital-name'}>
                {t('model.country.capital')}
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {countries.map((country) => (
              <TableRow key={country.code}>
                <TableCell>{country.code}</TableCell>
                <TableCell>{country.name}</TableCell>
                <TableCell align="right">
                  {country.area.land + country.area.water}
                </TableCell>
                <TableCell>
                  {country.capital ? country.capital.name : ''}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
        <TablePagination
          rowsPerPageOptions={[10, 25, 50]}
          colSpan={3}
          count={total}
          rowsPerPage={rowsPerPage}
          page={page}
          labelRowsPerPage={t('table.page.rows')}
          labelDisplayedRows={({ from, to, count }) =>
            t('table.pagination', { from, to, count })
          }
          SelectProps={{
            inputProps: { 'aria-label': 'rows per page' },
            native: true,
          }}
          onChangePage={handleChangePage}
          onChangeRowsPerPage={handleChangeRowsPerPage}
          ActionsComponent={TablePaginationActions}
          component="div"
        />
      </Paper>
    </div>
  );
};

export default Countries;
