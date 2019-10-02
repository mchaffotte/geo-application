import axios from 'axios';

const createQuiz = async configuration => {
  return axios.post('api/quizzes', configuration, {
    headers: {
      Accept: 'application/json',
    },
  });
};

const getQuiz = async id => {
  return axios.get(`api/quizzes/${id}`, {
    headers: {
      Accept: 'application/json',
    },
  });
};

export { createQuiz, getQuiz };
