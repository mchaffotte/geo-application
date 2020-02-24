import React, { Suspense } from 'react';
import { BrowserRouter as Router } from 'react-router-dom';

import Loader from './layout/Loader';
import Menu from './layout/Menu';
import AlertProvider from './components/alert/AlertProvider';
import AlertNotification from './components/alert/AlertNotification';

const App = () => (
  <AlertProvider>
    <Router>
      <Suspense fallback={<Loader />}>
        <Menu />
        <AlertNotification />
      </Suspense>
    </Router>
  </AlertProvider>
);

export default App;
