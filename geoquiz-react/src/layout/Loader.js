import React from 'react';
import { CircularProgress, makeStyles } from '@material-ui/core';

const useStyles = makeStyles((theme) => ({
  progress: {
    margin: theme.spacing(2),
  },
}));

const Loader = () => {
  const classes = useStyles();
  return <CircularProgress className={classes.progress} />;
};

export default Loader;
