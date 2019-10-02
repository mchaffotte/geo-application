import axios from 'axios';

const getQuizTypes = async () => {
  return axios.get('api/quiz-types', {
    headers: {
      Accept: 'application/json',
    },
  });
};

export default getQuizTypes;
