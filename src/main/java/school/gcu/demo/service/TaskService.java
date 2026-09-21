package school.gcu.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import school.gcu.demo.model.Task;
import school.gcu.demo.repository.TaskRepository;

@Service
public class TaskService {

    private static final Logger logger =
            LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        logger.info("Retrieving all tasks");
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        logger.info("Retrieving task with ID: {}", id);

        return taskRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid task ID: " + id));
    }

    public Task createTask(Task task) {
        logger.info("Creating task: {}", task.getTitle());
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task submittedTask) {

        logger.info("Updating task with ID: {}", id);

        Task existingTask = getTaskById(id);

        existingTask.setTitle(submittedTask.getTitle());
        existingTask.setDescription(submittedTask.getDescription());
        existingTask.setStatus(submittedTask.getStatus());

        if (submittedTask.getDueDate() != null) {
            existingTask.setDueDate(submittedTask.getDueDate());
        }

        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {
        logger.warn("Deleting task with ID: {}", id);
        taskRepository.deleteById(id);
    }
}