package pet.proj.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Builder;
import pet.proj.todo.model.Task;

@Builder
public record CreateTaskDto(
        @NotBlank String name,
        String description,
        Task.Status status,
        @JsonFormat(pattern = "dd:MM:yyyy' 'HH:mm:ss") LocalDateTime deadline
) {
}
