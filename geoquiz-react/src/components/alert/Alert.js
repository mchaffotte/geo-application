import React, { useState } from 'react';
import clsx from 'clsx';
import {
  IconButton,
  Snackbar,
  SnackbarContent,
  makeStyles,
} from '@material-ui/core';
import { amber, green } from '@material-ui/core/colors';
import { CheckCircle, Close, Error, Info, Warning } from '@material-ui/icons';

const variantIcon = {
  success: CheckCircle,
  warning: Warning,
  error: Error,
  info: Info,
};

const useStyles1 = makeStyles((theme) => ({
  success: {
    backgroundColor: green[600],
  },
  error: {
    backgroundColor: theme.palette.error.dark,
  },
  info: {
    backgroundColor: theme.palette.primary.main,
  },
  warning: {
    backgroundColor: amber[700],
  },
  icon: {
    fontSize: 20,
  },
  iconVariant: {
    opacity: 0.9,
    marginRight: theme.spacing(1),
  },
  message: {
    display: 'flex',
    alignItems: 'center',
  },
}));

const AlertContent = ({ className, alert, onClose, ...other }) => {
  const classes = useStyles1();
  const Icon = variantIcon[alert.level];

  return (
    <SnackbarContent
      className={clsx(classes[alert.level], className)}
      aria-describedby="client-snackbar"
      message={
        <span id="client-snackbar" className={classes.message}>
          <Icon className={clsx(classes.icon, classes.iconVariant)} />
          {alert.message}
        </span>
      }
      action={[
        <IconButton
          key="close"
          aria-label="close"
          color="inherit"
          onClick={onClose}
        >
          <Close className={classes.icon} />
        </IconButton>,
      ]}
      {...other}
    />
  );
};

const Alert = ({ alert, onClose }) => {
  const [open, setOpen] = useState(true);

  const handleClose = (_, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setOpen(false);
    onClose();
  };

  if (!alert) {
    return null;
  }

  return (
    <Snackbar
      anchorOrigin={{
        vertical: 'bottom',
        horizontal: 'center',
      }}
      open={open}
      autoHideDuration={6000}
      onClose={handleClose}
    >
      <AlertContent onClose={handleClose} alert={alert} />
    </Snackbar>
  );
};

export default Alert;
