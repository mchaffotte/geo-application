import React, { useState, useEffect } from 'react';
import { Radio, Select } from 'final-form-material-ui';
import { Form, Field } from 'react-final-form';
import { OnChange } from 'react-final-form-listeners';
import { useTranslation } from 'react-i18next';
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
  makeStyles,
} from '@material-ui/core';

import getQuizTypes from '../../api/quizTypesApi';
import { createQuiz } from '../../api/quizApi';
import useAlert from '../../components/alert/useAlert';

const useStyles = makeStyles({
  card: {
    maxWidth: 345,
  },
  actions: {
    marginTop: 10,
  },
});

const getChoice = answerTypes => {
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
        .then(response => {
          if (!didCancel) {
            setQuizTypes(response.data);
            setChoice(getChoice(response.data[0].answerTypes));
            setStatus('success');
          }
        })
        .catch(err => {
          error(`${err}`);
          if (err.response) {
            console.log(err.response.data);
            console.log(err.response.status);
            console.log(err.response.headers);
          } else if (err.request) {
            console.log(err.request);
          } else {
            console.log('Error', err.message);
          }
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

  const onSubmit = async ({ questionType, answerType }) => {
    const response = await createQuiz({
      questionType: questionType.questionType,
      answerType: answerType,
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
        <Form
          onSubmit={onSubmit}
          initialValues={{
            questionType: quizTypes[0],
            answerType: 'MULTIPLE_CHOICE',
          }}
          mutators={{
            setDefaultAnswerType: (args, state, utils) => {
              utils.changeValue(state, 'answerType', () => 'MULTIPLE_CHOICE');
            },
          }}
          render={({
            handleSubmit,
            submitting,
            values,
            form: {
              mutators: { setDefaultAnswerType },
            },
          }) => (
            <form onSubmit={handleSubmit} noValidate>
              <Grid container alignItems="flex-start" spacing={2}>
                <Grid item xs={12}>
                  <Field
                    fullWidth
                    name="questionType"
                    component={Select}
                    label="Question type"
                    formControlProps={{ fullWidth: true }}
                  >
                    {quizTypes.map(type => (
                      <MenuItem key={type.questionType} value={type}>
                        {t(`model.question-type.${type.questionType}`)}
                      </MenuItem>
                    ))}
                  </Field>
                  <OnChange name="questionType">
                    {(value, previous) => {
                      const newChoice = getChoice(value.answerTypes);
                      setChoice(newChoice);
                      if (values.answerType === 'ANSWER' && !newChoice.answer) {
                        setDefaultAnswerType();
                      }
                    }}
                  </OnChange>
                </Grid>
                <Grid item xs={12}>
                  <FormControl component="fieldset">
                    <FormLabel component="legend">Answer type</FormLabel>
                    <RadioGroup row>
                      <FormControlLabel
                        label={t('admin.quizzes.answer')}
                        control={
                          <Field
                            name="answerType"
                            component={Radio}
                            type="radio"
                            value="ANSWER"
                            disabled={!choice.answer}
                          />
                        }
                      />
                      <FormControlLabel
                        label={t('admin.quizzes.multiple-choice')}
                        control={
                          <Field
                            name="answerType"
                            component={Radio}
                            type="radio"
                            value="MULTIPLE_CHOICE"
                            disabled={!choice.multipleChoice}
                          />
                        }
                      />
                    </RadioGroup>
                  </FormControl>
                </Grid>
                <Grid item className={classes.actions}>
                  <Button
                    variant="contained"
                    color="primary"
                    type="submit"
                    disabled={submitting}
                  >
                    {t('admin.quizzes.play')}
                  </Button>
                </Grid>
              </Grid>
            </form>
          )}
        />
      </CardContent>
    </Card>
  );
};

export default GenerateQuiz;
