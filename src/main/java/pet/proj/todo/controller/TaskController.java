package pet.proj.todo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pet.proj.todo.dto.CreateTaskDto;
import pet.proj.todo.dto.TaskDto;
import pet.proj.todo.dto.UpdateTaskDto;
import pet.proj.todo.exciption.WrongDeadlineException;
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
    public List<TaskDto> getAllTasks() {
        return taskService.findAllTasks();
    }

    @GetMapping("/{status}")
    @Operation(summary = "Get all tasks",
            description = "Get a list of all tasks by status")
    public List<TaskDto> getAllTasksByStatus(@PathVariable String status) {
        return taskService.findAllTasksByStatus(status);
    }

    @PostMapping
    @Operation(summary = "Create task", description = "Create task")
    @ResponseStatus(code = HttpStatus.CREATED)
    public TaskDto createTask(@RequestBody @Valid CreateTaskDto taskDto)
            throws WrongDeadlineException {
        return taskService.createTask(taskDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task", description = "Delete task by id")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update task", description = "Update task by id")
    public TaskDto updateTask(@PathVariable Long id,
            @RequestBody UpdateTaskDto updateTaskDto) {
        return taskService.updateTask(id, updateTaskDto);
    }
}
