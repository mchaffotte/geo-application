import React, { createContext, useCallback, useState } from 'react';

export const AlertContext = createContext({
  alert: null,
  info: () => {},
  success: () => {},
  warn: () => {},
  error: () => {},
  removeAlert: () => {},
});

const AlertProvider = ({ children }) => {
  const [alert, setAlert] = useState(null);

  const addAlert = (level, message) => {
    setAlert({ level, message });
  };

  const removeAlert = () => {
    setAlert(null);
  };

  const contextValue = {
    alert,
    info: useCallback(message => addAlert('info', message), []),
    success: useCallback(message => addAlert('success', message), []),
    warn: useCallback(message => addAlert('warning', message), []),
    error: useCallback(message => addAlert('error', message), []),
    removeAlert,
  };

  return (
    <AlertContext.Provider value={contextValue}>
      {children}
    </AlertContext.Provider>
  );
};

export default AlertProvider;
