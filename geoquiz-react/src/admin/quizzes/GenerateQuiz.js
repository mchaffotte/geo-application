import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { Formik, Field } from 'formik';
import {
  Button,
  Card,
  CardContent,
  CardHeader,
  CircularProgress,
  FormControl,
  FormControlLabel,
  FormLabel,
  Grid,
  MenuItem,
  RadioGroup,
  Select,
  Radio,
  InputLabel,
  makeStyles,
} from '@material-ui/core';

import getQuizTypes from '../../api/quizTypesApi';
import { createQuiz } from '../../api/quizApi';
import useAlert from '../../components/alert/useAlert';
import log from '../../service/logger';

const useStyles = makeStyles({
  card: {
    maxWidth: 345,
  },
  actions: {
    marginTop: 10,
  },
  empty: {
    height: 36,
  },
});

const getChoice = (answerTypes) => {
  const multipleChoice = answerTypes.includes('MULTIPLE_CHOICE');
  const answer = answerTypes.includes('ANSWER');
  return { answer, multipleChoice };
};

const GenerateQuiz = ({ onCreate }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  const [quizTypes, setQuizTypes] = useState([]);
  const [status, setStatus] = useState('loading');
  const [choice, setChoice] = useState({ answer: true, multipleChoice: true });

  const { error } = useAlert();

  useEffect(() => {
    const fetchData = () => {
      let didCancel = false;
      setStatus('loading');

      getQuizTypes()
        .then((response) => {
          if (!didCancel) {
            setQuizTypes(response.data);
            setChoice(getChoice(response.data[0].answerTypes));
            setStatus('success');
          }
        })
        .catch((err) => {
          error(`${err}`);
          log(err);
          if (!didCancel) {
            setStatus('error');
          }
        });
      return () => {
        didCancel = true;
      };
    };
    fetchData();
  }, [error]);

  const handleQuizCreation = async ({ questionType, answerType, filter }) => {
    const response = await createQuiz({
      questionType: questionType.questionType,
      answerType,
      filter: filter ? { name: questionType.filter.name, value: filter } : null,
    });

    const location = response.headers['location'];
    const index = location.lastIndexOf('/');
    const id = Number(location.substring(index + 1));

    onCreate(id);
  };

  if (status === 'loading') {
    return <CircularProgress />;
  }

  if (status === 'error') {
    return <div>Something went wrong ...</div>;
  }

  return (
    <Card className={classes.card}>
      <CardHeader title="Configuration" />
      <CardContent>
        <Formik
          initialValues={{
            questionType: quizTypes[0],
            answerType: 'MULTIPLE_CHOICE',
            filter: '',
          }}
          onSubmit={(values, { setSubmitting }) => {
            handleQuizCreation(values).then(() => setSubmitting(false));
          }}
        >
          {({ values, handleChange, handleSubmit, isSubmitting }) => (
            <form onSubmit={handleSubmit}>
              <Grid container alignItems="flex-start" spacing={2}>
                <Grid item xs={12}>
                  <Field name="questionType">
                    {({ field }) => (
                      <FormControl fullWidth>
                        <InputLabel htmlFor="question-type-select">
                          {t('admin.quizzes.question-type')}
                        </InputLabel>
                        <Select
                          id="question-type-select"
                          {...field}
                          onChange={(e) => {
                            const newChoice = getChoice(
                              e.target.value.answerTypes
                            );
                            setChoice(newChoice);
                            if (
                              values.answerType === 'ANSWER' &&
                              !newChoice.answer
                            ) {
                              values.answerType = 'MULTIPLE_CHOICE';
                            }
                            handleChange(e);
                          }}
                        >
                          {quizTypes.map((type) => (
                            <MenuItem key={type.questionType} value={type}>
                              {t(`model.question-type.${type.questionType}`)}
                            </MenuItem>
                          ))}
                        </Select>
                      </FormControl>
                    )}
                  </Field>
                </Grid>
                <Grid item xs={12}>
                  <Field name="answerType">
                    {({ field }) => (
                      <FormControl fullWidth>
                        <FormLabel htmlFor="anwser-type-select">
                          {t('admin.quizzes.answer-type')}
                        </FormLabel>
                        <RadioGroup
                          row
                          {...field}
                          name="answerType"
                          id="anwser-type-select"
                        >
                          <FormControlLabel
                            label={t('admin.quizzes.answer')}
                            value="ANSWER"
                            disabled={!choice.answer}
                            control={<Radio />}
                          />
                          <FormControlLabel
                            label={t('admin.quizzes.multiple-choice')}
                            value="MULTIPLE_CHOICE"
                            disabled={!choice.multipleChoice}
                            control={<Radio />}
                          />
                        </RadioGroup>
                      </FormControl>
                    )}
                  </Field>
                </Grid>
                <Grid item xs={12}>
                  <Field name="filter">
                    {({ field }) => (
                      <FormControl fullWidth>
                        <InputLabel htmlFor="filter-select">
                          {quizTypes[0].filter.label}
                        </InputLabel>
                        <Select id="filter-select" {...field}>
                          <MenuItem
                            value=""
                            className={classes.empty}
                          ></MenuItem>
                          {quizTypes[0].filter.values.map((possibility) => (
                            <MenuItem
                              key={possibility.label}
                              value={possibility.value}
                            >
                              {possibility.label}
                            </MenuItem>
                          ))}
                        </Select>
                      </FormControl>
                    )}
                  </Field>
                </Grid>
                <Grid item className={classes.actions}>
                  <Button
                    variant="contained"
                    color="primary"
                    type="submit"
                    disabled={isSubmitting}
                  >
                    {t('admin.quizzes.play')}
                  </Button>
                </Grid>
              </Grid>
            </form>
          )}
        </Formik>
      </CardContent>
    </Card>
  );
};

export default GenerateQuiz;
