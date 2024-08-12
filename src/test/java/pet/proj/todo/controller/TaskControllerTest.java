package pet.proj.todo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pet.proj.todo.dto.CreateTaskDto;
import pet.proj.todo.dto.TaskDto;
import pet.proj.todo.model.Task.Status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private static ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Get new tasks")
    @Sql(scripts = {"classpath:database/tasks/delete-all-tasks.sql",
            "classpath:database/tasks/insert-tasks.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getNewTasks_Success() throws Exception {
        MvcResult result = mockMvc.perform(get("/tasks/new"))
                .andExpect(status().isOk())
                .andReturn();

        List<TaskDto> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        assertThat(response.size()).isEqualTo(1);
        assertThat(response.get(0))
                .hasFieldOrPropertyWithValue("name", "New task 2")
                .hasFieldOrPropertyWithValue("description", "Description for new task 2")
                .hasFieldOrPropertyWithValue("status", Status.NEW)
                .hasFieldOrPropertyWithValue("deadline", null);
    }

    @Test
    @DisplayName("Get active tasks")
    @Sql(scripts = {"classpath:database/tasks/delete-all-tasks.sql",
            "classpath:database/tasks/insert-tasks.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getActiveTasks_Success() throws Exception {
        MvcResult result = mockMvc.perform(get("/tasks/active"))
                .andExpect(status().isOk())
                .andReturn();

        List<TaskDto> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        assertThat(response.size()).isEqualTo(1);
        assertThat(response.get(0))
                .hasFieldOrPropertyWithValue("name", "Active task 1")
                .hasFieldOrPropertyWithValue("description", "Description for active task 1")
                .hasFieldOrPropertyWithValue("status", Status.IN_PROGRESS)
                .hasFieldOrPropertyWithValue("deadline", null);
    }

    @Test
    @DisplayName("Get completed tasks")
    @Sql(scripts = {"classpath:database/tasks/delete-all-tasks.sql",
            "classpath:database/tasks/insert-tasks.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getCompletedTasks_Success() throws Exception {
        MvcResult result = mockMvc.perform(get("/tasks/completed"))
                .andExpect(status().isOk())
                .andReturn();

        List<TaskDto> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        assertThat(response.size()).isEqualTo(2);
        assertThat(response.get(0))
                .hasFieldOrPropertyWithValue("name", "Completed task 3")
                .hasFieldOrPropertyWithValue("description", "Description for completed task 3")
                .hasFieldOrPropertyWithValue("status", Status.COMPLETED)
                .hasFieldOrPropertyWithValue("deadline", null);
        assertThat(response.get(1))
                .hasFieldOrPropertyWithValue("name", "Completed task 4")
                .hasFieldOrPropertyWithValue("description", "Description for completed task 4")
                .hasFieldOrPropertyWithValue("status", Status.COMPLETED)
                .hasFieldOrPropertyWithValue("deadline", null);
    }

    @Test
    @DisplayName("Successfully create task")
    @Sql(scripts = {"classpath:database/tasks/delete-all-tasks.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void createTask_ValidInput_Success() throws Exception {
        LocalDateTime deadline = LocalDateTime.now().plusMinutes(1);
        CreateTaskDto request = CreateTaskDto.builder()
                .name("Test task")
                .description("New test task")
                .status(Status.NEW)
                .deadline(deadline)
                .build();

        MvcResult result = mockMvc.perform(post("/tasks")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        TaskDto response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TaskDto.class
        );
        assertThat(response)
                .hasFieldOrPropertyWithValue("name", "Test task")
                .hasFieldOrPropertyWithValue("description", "New test task")
                .hasFieldOrPropertyWithValue("status", Status.NEW)
                .hasFieldOrPropertyWithValue("deadline", deadline);
    }

    @Test
    @DisplayName("Unsuccessfully create task")
    @Sql(scripts = {"classpath:database/tasks/delete-all-tasks.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void createTask_InvalidInput_BadRequest() throws Exception {
        CreateTaskDto request = CreateTaskDto.builder()
                .name("Test task")
                .description("New test task")
                .status(Status.NEW)
                .deadline(LocalDateTime.now().minusDays(1))
                .build();
        var result = mockMvc.perform(post("/tasks")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo("The deadline must be after the current time");
    }
}