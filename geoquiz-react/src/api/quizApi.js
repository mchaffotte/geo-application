import axios from 'axios';

const createQuiz = async configuration => {
  return axios.post('api/quizzes', configuration, {
    headers: {
      Accept: 'application/json',
      'Content-type': 'application/json',
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

const answer = (quizId, quizAnswer) => {
  return axios.put(`api/quizzes/${quizId}`, quizAnswer, {
    headers: {
      Accept: 'application/json',
      'Content-type': 'application/json',
    },
  });
};

export { createQuiz, getQuiz, answer };
