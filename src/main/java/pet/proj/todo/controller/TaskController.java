package pet.proj.todo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pet.proj.todo.dto.task.CreateTaskDto;
import pet.proj.todo.dto.task.TaskDto;
import pet.proj.todo.dto.task.UpdateTaskDto;
import pet.proj.todo.exception.WrongDeadlineException;
import pet.proj.todo.service.TaskService;

@Tag(name = "Tasks management", description = "Endpoints for managing task")
@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    @Operation(summary = "Get all tasks",
            description = "Get a list of all tasks")
    @PreAuthorize("hasAuthority('USER')")
    public List<TaskDto> getAllTasks(Authentication authentication) {
        return taskService.findAllTasks(authentication);
    }

    @GetMapping("/{status}")
    @Operation(summary = "Get all tasks",
            description = "Get a list of all tasks by status")
    @PreAuthorize("hasAuthority('USER')")
    public List<TaskDto> getAllTasksByStatus(@PathVariable String status, Authentication authentication) {
        return taskService.findAllTasksByStatus(authentication, status);
    }

    @PostMapping
    @Operation(summary = "Create task", description = "Create task")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('USER')")
    public TaskDto createTask(Authentication authentication, @RequestBody @Valid CreateTaskDto taskDto)
            throws WrongDeadlineException {
        return taskService.createTask(authentication, taskDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task", description = "Delete task by id")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('USER')")
    public void deleteTask(Authentication authentication, @PathVariable Long id) {
        taskService.deleteTask(authentication, id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update task", description = "Update task by id")
    @PreAuthorize("hasAuthority('USER')")
    public TaskDto updateTask(Authentication authentication, @PathVariable Long id,
            @RequestBody UpdateTaskDto updateTaskDto) {
        return taskService.updateTask(authentication, id, updateTaskDto);
    }
}
