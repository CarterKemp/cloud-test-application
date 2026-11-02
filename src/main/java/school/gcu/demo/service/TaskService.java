package school.gcu.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import school.gcu.demo.model.Task;
import school.gcu.demo.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid task ID: " + id));
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task submittedTask) {

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
        taskRepository.deleteById(id);
    }
}