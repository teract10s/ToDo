package pet.proj.todo.dto;

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
    private LocalDateTime deadline;
    private Status status;
}
