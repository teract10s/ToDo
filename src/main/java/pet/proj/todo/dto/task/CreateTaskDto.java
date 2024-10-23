package pet.proj.todo.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import pet.proj.todo.model.Task;

@Builder
@Data
public class CreateTaskDto {
        @NotBlank
        private String name;
        private String description;
        private Task.Status status;
        @JsonFormat(pattern = "dd:MM:yyyy HH:mm:ss")
        private LocalDateTime deadline;
}
