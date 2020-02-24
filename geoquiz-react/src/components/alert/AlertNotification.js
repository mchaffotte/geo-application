import React from 'react';

import useAlert from './useAlert';
import Alert from './Alert';

const AlertNotification = () => {
  const { alert, removeAlert } = useAlert();
  if (!alert) {
    return null;
  }
  return <Alert alert={alert} onClose={removeAlert} />;
};

export default AlertNotification;
