// src/pages/Home.js
import React from 'react';
import { Link } from 'react-router-dom';

const Home = () => (
    <div>
        <h1>Task Manager</h1>
        <ul>
            <li><Link to="/tasks/new">New Tasks</Link></li>
            <li><Link to="/tasks/active">Active Tasks</Link></li>
            <li><Link to="/tasks/completed">Completed Tasks</Link></li>
        </ul>
    </div>
);

export default Home;
