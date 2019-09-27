import axios from 'axios';

const getCountries = async (offset, limit, sort) => {
  const params = {};
  if (offset) {
    params.offset = offset;
  }
  if (limit) {
    params.limit = limit;
  }
  if (sort) {
    params.sort = sort;
  }
  return axios.get('api/countries', {
    params,
    headers: {
      Accept: 'application/json',
    },
  });
};

export default getCountries;
