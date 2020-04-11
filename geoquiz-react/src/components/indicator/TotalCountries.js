import React, { useEffect, useState } from 'react';
import Public from '@material-ui/icons/Public';

import Indicator from './Indicator';
import useAlert from '../alert/useAlert';
import getCountries from '../../api/countriesApi';
import log from '../../service/logger';

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
          log(err);
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
