package pet.proj.todo.service;

import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import pet.proj.todo.dto.task.CreateTaskDto;
import pet.proj.todo.dto.task.TaskDto;
import pet.proj.todo.dto.task.UpdateTaskDto;

public interface TaskService {

    List<TaskDto> findAllTasksByStatus(Authentication authentication, String status);

    TaskDto createTask(Authentication authentication, CreateTaskDto taskDto);

    void deleteTask(Authentication authentication, Long id);

    TaskDto updateTask(Authentication authentication, Long id, UpdateTaskDto updateTaskDto);

    List<TaskDto> findAllTasks(Authentication authentication);
}
