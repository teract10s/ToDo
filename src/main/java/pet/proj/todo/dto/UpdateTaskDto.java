package pet.proj.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import pet.proj.todo.model.Task.Status;

@Builder
public record UpdateTaskDto(
        String name,
        String description,
        Status status,
        @JsonFormat(pattern = "dd:MM:yyyy' 'HH:mm:ss") LocalDateTime deadline
) {
}
