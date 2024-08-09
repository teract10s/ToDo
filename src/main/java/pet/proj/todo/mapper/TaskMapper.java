package pet.proj.todo.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import pet.proj.todo.dto.CreateTaskDto;
import pet.proj.todo.dto.TaskDto;
import pet.proj.todo.model.Task;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl")
public interface TaskMapper {

    TaskDto toDto(Task task);

    Task toModel(TaskDto taskDto);

    Task toModel(CreateTaskDto taskDto);
}
