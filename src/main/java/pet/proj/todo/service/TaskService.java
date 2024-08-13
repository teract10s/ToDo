package pet.proj.todo.service;

import java.util.List;
import pet.proj.todo.dto.CreateTaskDto;
import pet.proj.todo.dto.TaskDto;
import pet.proj.todo.dto.UpdateTaskDto;
import pet.proj.todo.model.Task.Status;

public interface TaskService {

    List<TaskDto> findAllTasksByStatus(String status);

    TaskDto createTask(CreateTaskDto taskDto);

    void deleteTask(Long id);

    TaskDto updateTask(Long id, UpdateTaskDto updateTaskDto);

    List<TaskDto> findAllTasks();
}
