import { useContext } from 'react';
import { AlertContext } from './AlertProvider';

const useAlert = () => {
  return useContext(AlertContext);
};

export default useAlert;
