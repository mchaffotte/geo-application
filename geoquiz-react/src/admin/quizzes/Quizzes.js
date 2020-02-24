import React, { useState } from 'react';
import { Grid } from '@material-ui/core';

import GenerateQuiz from './GenerateQuiz';
import PlayQuiz from './PlayQuiz';
import { getQuiz } from '../../api/quizApi';

const Quizzes = () => {
  const [quiz, setQuiz] = useState(null);

  const handleNewQuiz = async id => {
    const response = await getQuiz(id);
    setQuiz(response.data);
  };

  return (
    <Grid container spacing={4}>
      <Grid item>
        <GenerateQuiz onCreate={handleNewQuiz} />
      </Grid>
      <Grid item>
        <PlayQuiz quiz={quiz} />
      </Grid>
    </Grid>
  );
};

export default Quizzes;
