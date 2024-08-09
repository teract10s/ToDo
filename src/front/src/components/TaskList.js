// src/components/TaskList.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';

const TaskList = ({ status }) => {
    const [tasks, setTasks] = useState([]);

    useEffect(() => {
        axios.get(`/tasks/${status}`)
            .then(response => setTasks(response.data))
            .catch(error => console.error('Error fetching tasks:', error));
    }, [status]);

    return (
        <div>
            <h2>{status.charAt(0).toUpperCase() + status.slice(1)} Tasks</h2>
            <ul>
                {tasks.map(task => (
                    <li key={task.id}>{task.name}</li>
                ))}
            </ul>
        </div>
    );
};

export default TaskList;
