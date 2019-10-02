import React from 'react';

import GenerateQuiz from './GenerateQuiz';
import { getQuiz } from '../../api/quizApi';

const Quizzes = () => {
  const handleNewQuiz = async id => {
    const responseQuiz = await getQuiz(id);
    console.log(responseQuiz);
  };

  return <GenerateQuiz onCreate={handleNewQuiz} />;
};

export default Quizzes;
