package pet.proj.todo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pet.proj.todo.dto.CreateTaskDto;
import pet.proj.todo.dto.TaskDto;
import pet.proj.todo.exciption.WrongDeadlineException;
import pet.proj.todo.model.Task.Status;
import pet.proj.todo.service.TaskService;

@Tag(name = "Tasks management", description = "Endpoints for managing task")
@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/new")
    @Operation(summary = "Get all tasks",
            description = "Get a list of all new available tasks")
    public List<TaskDto> getAllNewTasks() {
        return taskService.findAllTasksByStatus(Status.NEW);
    }

    @GetMapping("/active")
    @Operation(summary = "Get all tasks",
            description = "Get a list of all available tasks in progress")
    public List<TaskDto> getAllActiveTasks() {
        return taskService.findAllTasksByStatus(Status.IN_PROGRESS);
    }

    @GetMapping("/completed")
    @Operation(summary = "Get all tasks",
            description = "Get a list of all competed available tasks")
    public List<TaskDto> getAllCompletedTasks() {
        return taskService.findAllTasksByStatus(Status.COMPLETED);
    }

    @PostMapping
    @Operation(summary = "Create task", description = "Create randomly generated task for testing")
    @ResponseStatus(code = HttpStatus.CREATED)
    public TaskDto createTask(@RequestBody @Valid CreateTaskDto taskDto) throws WrongDeadlineException {
        return taskService.createTask(taskDto);
    }
}
