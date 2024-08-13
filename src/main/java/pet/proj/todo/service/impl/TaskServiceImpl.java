package pet.proj.todo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import pet.proj.todo.dto.CreateTaskDto;
import pet.proj.todo.dto.TaskDto;
import pet.proj.todo.dto.UpdateTaskDto;
import pet.proj.todo.exciption.WrongDeadlineException;
import pet.proj.todo.exciption.WrongStatusException;
import pet.proj.todo.mapper.TaskMapper;
import pet.proj.todo.model.Task;
import pet.proj.todo.model.Task.Status;
import pet.proj.todo.repository.TaskRepository;
import pet.proj.todo.service.TaskService;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    @Override
    public List<TaskDto> findAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Override
    public List<TaskDto> findAllTasksByStatus(String status) {
        try {
            Status taskStatus = Status.valueOf(status.toUpperCase());
            return taskRepository.findAllByStatus(taskStatus).stream()
                    .map(taskMapper::toDto)
                    .toList();
        } catch (IllegalArgumentException ex) {
            throw new WrongStatusException(String.format("Non-existent status: %s was requested", status));
        }
    }

    @Override
    public TaskDto createTask(CreateTaskDto taskDto) {
        Task notSavedTask = taskMapper.toModel(taskDto);
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
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public TaskDto updateTask(Long id, UpdateTaskDto updateTaskDto) {
        Task taskFromDb = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Can't find task by id: " + id));
        if (updateTaskDto.deadline() != null && LocalDateTime.now().isAfter(updateTaskDto.deadline())) {
            throw new WrongDeadlineException("The deadline must be after the current time");
        }
        taskMapper.updateBook(updateTaskDto, taskFromDb);
        return taskMapper.toDto(taskRepository.save(taskFromDb));
    }
}
