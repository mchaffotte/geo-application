import React, { useState, useEffect } from 'react';
import { Form, Field } from 'react-final-form';
import { useTranslation } from 'react-i18next';
import {
  Button,
  Card,
  CardContent,
  CardHeader,
  CardMedia,
  Grid,
  TextField,
  Typography,
  makeStyles,
} from '@material-ui/core';

import { answer } from '../../api/quizApi';

const useStyles = makeStyles(theme => ({
  card: {
    minWidth: 345,
    maxWidth: 500,
  },
  media: {
    width: 'initial',
    marginLeft: 'auto',
    marginRight: 'auto',
  },
  actions: {
    marginTop: 10,
  },
  choice: {
    marginTop: theme.spacing(1),
    marginBottom: theme.spacing(1),
    width: '100%',
  },
}));

const PlayQuiz = ({ quiz }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  const [index, setIndex] = useState(0);
  const [question, setQuestion] = useState(null);
  const [answers, setAnswers] = useState([]);

  const [result, setResult] = useState(null);

  useEffect(() => {
    if (quiz) {
      setAnswers([]);
      setIndex(0);
      setResult(null);
    }
  }, [quiz]);

  useEffect(() => {
    if (quiz) {
      setQuestion(quiz.questions[index]);
    }
  }, [index, quiz]);

  const handleChoice = choice => {
    const questionAnswers = [...answers, { answers: [choice] }];
    if (index + 1 < quiz.questions.length) {
      setAnswers(questionAnswers);
      setIndex(index + 1);
    } else {
      answer(quiz.id, { questionAnswers })
        .then(response => {
          setResult(response.data);
        })
        .catch(error => {
          if (error.response) {
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
          } else if (error.request) {
            console.log(error.request);
          } else {
            console.log('Error', error.message);
          }
        });
    }
  };

  const onSubmit = async values => {
    const choice = values.answer ? values.answer : '';
    handleChoice(choice);
  };

  if (!quiz || !question) {
    return null;
  }

  if (result) {
    return (
      <Card className={classes.card}>
        <CardHeader title={t('admin.quizzes.result')} />
        <CardContent>
          <Typography variant="body1" color="textSecondary" component="p">
            {result.nbOfCorrectAnswers} / {result.nbOfQuestions}
          </Typography>
        </CardContent>
      </Card>
    );
  }

  return (
    <Card className={classes.card}>
      <CardHeader title={t('admin.quizzes.question', { number: index + 1 })} />
      {question.imagePath && (
        <CardMedia
          className={classes.media}
          image={question.imagePath}
          component="img"
        />
      )}
      <CardContent>
        <Typography variant="body1" color="textSecondary" component="p">
          {question.wording}
        </Typography>
        {question.choices.map(choice => (
          <Button
            key={choice}
            size="large"
            variant="outlined"
            color="primary"
            className={classes.choice}
            onClick={() => {
              handleChoice(choice);
            }}
          >
            {choice}
          </Button>
        ))}
        {question.choices.length === 0 && (
          <Form
            onSubmit={onSubmit}
            render={({ handleSubmit }) => (
              <form onSubmit={handleSubmit} noValidate>
                <Grid container alignItems="flex-start" spacing={2}>
                  <Grid item xs={12}>
                    <Field
                      fullWidth
                      name="answer"
                      component={TextField}
                      type="text"
                      label={t('admin.quizzes.enter-answer')}
                    />
                  </Grid>
                  <Grid item className={classes.actions}>
                    <Button variant="contained" color="primary" type="submit">
                      {t('admin.quizzes.next')}
                    </Button>
                  </Grid>
                </Grid>
              </form>
            )}
          />
        )}
      </CardContent>
    </Card>
  );
};

export default PlayQuiz;