import React, { useEffect, useState } from 'react';
import Public from '@material-ui/icons/Public';

import Indicator from './Indicator';
import useAlert from '../alert/useAlert';
import getCountries from '../../api/countriesApi';

const TotalCountries = () => {
  const [total, setTotal] = useState(null);

  const { error } = useAlert();

  useEffect(() => {
    const fetchData = () => {
      let didCancel = false;

      getCountries(1, 1, null)
        .then((response) => {
          if (!didCancel) {
            setTotal(response.data.total);
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
          setTotal('?');
        });
      return () => {
        didCancel = true;
      };
    };
    fetchData();
  }, [error]);

  return <Indicator label="TOTAL COUNTRIES" value={total} Icon={Public} />;
};

export default TotalCountries;
