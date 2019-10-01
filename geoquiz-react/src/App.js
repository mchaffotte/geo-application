import React, { Suspense } from 'react';
import { BrowserRouter as Router } from 'react-router-dom';

import Loader from './layout/Loader';
import Menu from './layout/Menu';

const App = () => (
  <Router>
    <Suspense fallback={<Loader />}>
      <Menu />
    </Suspense>
  </Router>
);

export default App;
