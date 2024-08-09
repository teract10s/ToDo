// src/App.js
import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Header from './components/Header';
import Home from './pages/Home';
import NewTasks from './pages/NewTasks';
import ActiveTasks from './pages/ActiveTasks';
import CompletedTasks from './pages/CompletedTasks';

const App = () => (
    <Router>
        <Header />
        <Switch>
            <Route exact path="/" component={Home} />
            <Route path="/tasks/new" component={NewTasks} />
            <Route path="/tasks/active" component={ActiveTasks} />
            <Route path="/tasks/completed" component={CompletedTasks} />
        </Switch>
    </Router>
);

export default App;