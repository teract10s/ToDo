package pet.proj.todo.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import pet.proj.todo.model.Task;

public record CreateTaskDto(
        @NotBlank String name,
        String description,
        Task.Status status,
        LocalDateTime deadline
) {
}
