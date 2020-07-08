import React, { useState, useEffect, useRef } from 'react';
import { Formik } from 'formik';
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
import useAlert from '../../components/alert/useAlert';
import Countdown from '../../components/countdown/Countdown';
import log from '../../service/logger';

const useStyles = makeStyles((theme) => ({
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

const useFocus = () => {
  const inputRef = useRef(null);

  const setFocus = () => inputRef.current && inputRef.current.focus();

  return [inputRef, setFocus];
};

const PlayQuiz = ({ quiz }) => {
  const classes = useStyles();
  const { t } = useTranslation();
  const { error } = useAlert();

  const index = useRef(1);
  const [question, setQuestion] = useState(null);
  const answers = useRef([]);

  const [result, setResult] = useState(null);

  const [inputRef, setInputFocus] = useFocus();

  useEffect(() => {
    if (quiz) {
      answers.current = [];
      index.current = 1;
      setQuestion(quiz.questions[index.current - 1]);
      setResult(null);
    }
  }, [quiz]);

  const handleTimeOver = () => {
    handleChoice('');
  };

  const handleChoice = (choice) => {
    const questionAnswers = [...answers.current, { answers: [choice] }];
    if (index.current < quiz.questions.length) {
      answers.current = questionAnswers;
      index.current += 1;
      setQuestion(quiz.questions[index.current - 1]);
    } else {
      answer(quiz.id, { questionAnswers })
        .then((response) => {
          setResult(response.data);
        })
        .catch((err) => {
          error(`${err}`);
          log(err);
        });
    }
  };

  const handleResult = async ({ answer }) => {
    const choice = answer ? answer : '';
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
      <CardHeader
        title={t('admin.quizzes.question', { number: index.current })}
        action={
          <Countdown
            key={`${quiz.id}#${index.current}`}
            seconds={10}
            onOver={handleTimeOver}
          />
        }
      />
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
        {question.choices.map((choice) => (
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
          <Formik
            initialValues={{
              answer: '',
            }}
            onSubmit={(values, { setSubmitting, resetForm }) => {
              handleResult(values).then(() => {
                resetForm({ answer: '' });
                setSubmitting(false);
                setInputFocus();
              });
            }}
          >
            {({
              values,
              handleChange,
              handleBlur,
              handleSubmit,
              errors,
              touched,
              isSubmitting,
            }) => (
              <form onSubmit={handleSubmit} noValidate>
                <Grid container alignItems="flex-start" spacing={2}>
                  <Grid item xs={12}>
                    <TextField
                      label={t('admin.quizzes.enter-answer')}
                      name="answer"
                      value={values.answer}
                      onChange={handleChange}
                      onBlur={handleBlur}
                      error={errors.email && touched.email}
                      helperText={errors.email && touched.email && errors.email}
                      fullWidth
                      autoFocus
                      inputRef={inputRef}
                    />
                  </Grid>
                  <Grid item className={classes.actions}>
                    <Button
                      variant="contained"
                      color="primary"
                      type="submit"
                      disabled={isSubmitting}
                    >
                      {t('admin.quizzes.next')}
                    </Button>
                  </Grid>
                </Grid>
              </form>
            )}
          </Formik>
        )}
      </CardContent>
    </Card>
  );
};

export default PlayQuiz;
