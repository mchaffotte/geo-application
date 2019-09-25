import React, { Component } from 'react';
import { BrowserRouter as Router } from 'react-router-dom';

import Menu from './layout/Menu';

class App extends Component {
  render() {
    return (
      <Router>
        <Menu />
      </Router>
    );
  }
}

export default App;
