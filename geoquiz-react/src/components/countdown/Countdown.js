import React, { useEffect, useState } from 'react';
import { CircularProgress, makeStyles } from '@material-ui/core';

const INTERVAL = 200;
const TIMER_SIZE = 46;

const getRemainingSeconds = (time) => Math.ceil(time / 1000);

const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
    alignItems: 'center',
  },
  wrapper: {
    margin: theme.spacing(1),
    position: 'relative',
  },
  time: {
    fontWeight: 'bold',
    textAlign: 'center',
    verticalAlign: 'middle',
    lineHeight: `${TIMER_SIZE}px`,
    width: `${TIMER_SIZE}px`,
  },
  progress: {
    position: 'absolute',
    top: 0,
    left: 0,
    zIndex: 1,
  },
}));

const Countdown = ({ seconds, onOver }) => {
  const [timeLeft, setTimeLeft] = useState(seconds * 1000);

  const classes = useStyles();

  useEffect(() => {
    const intervalId = setInterval(() => {
      const distance = timeLeft - INTERVAL;
      if (distance >= 0) {
        setTimeLeft(distance);
      } else {
        onOver();
      }
    }, INTERVAL);
    return () => clearInterval(intervalId);
  }, [timeLeft, onOver]);

  return (
    <div className={classes.root}>
      <div className={classes.wrapper}>
        <div className={classes.time}>{getRemainingSeconds(timeLeft)}</div>
        <CircularProgress
          size={TIMER_SIZE}
          className={classes.progress}
          variant="static"
          value={timeLeft / (seconds * 10)}
        />
      </div>
    </div>
  );
};

export default Countdown;
