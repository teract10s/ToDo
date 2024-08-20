package pet.proj.todo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pet.proj.todo.dto.task.CreateTaskDto;
import pet.proj.todo.dto.task.TaskDto;
import pet.proj.todo.dto.task.UpdateTaskDto;
import pet.proj.todo.exception.PermissionDeniedException;
import pet.proj.todo.exception.WrongDeadlineException;
import pet.proj.todo.exception.WrongStatusException;
import pet.proj.todo.mapper.TaskMapper;
import pet.proj.todo.model.Task;
import pet.proj.todo.model.Task.Status;
import pet.proj.todo.model.User;
import pet.proj.todo.repository.TaskRepository;
import pet.proj.todo.service.TaskService;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final UserDetailsService userDetailsService;

    @Override
    public List<TaskDto> findAllTasks(Authentication authentication) {
        User user = (User) userDetailsService.loadUserByUsername(authentication.getName());
        return taskRepository.findAllByUserId(user.getId()).stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Override
    public List<TaskDto> findAllTasksByStatus(Authentication authentication, String status) {
        User user = (User) userDetailsService.loadUserByUsername(authentication.getName());
        try {
            Status taskStatus = Status.valueOf(status.toUpperCase());
            return taskRepository.findAllByStatusAndUserId(taskStatus, user.getId()).stream()
                    .map(taskMapper::toDto)
                    .toList();
        } catch (IllegalArgumentException ex) {
            throw new WrongStatusException(
                    String.format("Non-existent status: %s was requested", status
                    ));
        }
    }

    @Override
    public TaskDto createTask(Authentication authentication, CreateTaskDto taskDto) {
        User user = (User) userDetailsService.loadUserByUsername(authentication.getName());
        Task notSavedTask = taskMapper.toModel(taskDto);
        notSavedTask.setUser(user);
        if (taskDto.deadline() != null && LocalDateTime.now().isAfter(taskDto.deadline())) {
            throw new WrongDeadlineException("The deadline must be after the current time");
        }
        if (notSavedTask.getStatus() == null) {
            notSavedTask.setStatus(Status.NEW);
        }
        Task savedTask = taskRepository.save(notSavedTask);
        return taskMapper.toDto(savedTask);
    }

    @Override
    public void deleteTask(Authentication authentication, Long id) {
        User user = (User) userDetailsService.loadUserByUsername(authentication.getName());
        Task task = taskRepository.getReferenceById(id);
        if (!Objects.equals(task.getUser().getId(), user.getId())) {
            throw new PermissionDeniedException("Can't delete not your task");
        }
        taskRepository.deleteById(id);
    }

    @Override
    public TaskDto updateTask(Authentication authentication, Long id, UpdateTaskDto updateTaskDto) {
        User user = (User) userDetailsService.loadUserByUsername(authentication.getName());
        Task taskFromDb = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Can't find task by id: " + id));
        if (!Objects.equals(taskFromDb.getUser().getId(), user.getId())) {
            throw new PermissionDeniedException("Can't update not your task");
        }
        if (updateTaskDto.deadline() != null
                && LocalDateTime.now().isAfter(updateTaskDto.deadline())) {
            throw new WrongDeadlineException("The deadline must be after the current time");
        }
        taskMapper.updateBook(updateTaskDto, taskFromDb);
        return taskMapper.toDto(taskRepository.save(taskFromDb));
    }
}
