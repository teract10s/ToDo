package pet.proj.todo.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pet.proj.todo.model.Task.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private String name;
    private String description;
    @JsonFormat(pattern = "dd:MM:yyyy' 'HH:mm")
    private LocalDateTime deadline;
    private Status status;
}
