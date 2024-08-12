package pet.proj.todo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pet.proj.todo.dto.CreateTaskDto;
import pet.proj.todo.dto.TaskDto;
import pet.proj.todo.exciption.WrongDeadlineException;
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
    public List<TaskDto> findAllTasksByStatus(Status status) {
        return taskRepository.findAllByStatus(status).stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Override
    public TaskDto createTask(CreateTaskDto taskDto) {
        Task notSavedTask = taskMapper.toModel(taskDto);
        if (LocalDateTime.now().isAfter(taskDto.deadline())) {
            throw new WrongDeadlineException("The deadline must be after the current time");
        }
        if (notSavedTask.getStatus() == null) {
            notSavedTask.setStatus(Status.NEW);
        }
        Task savedTask = taskRepository.save(notSavedTask);
        return taskMapper.toDto(savedTask);
    }
}
