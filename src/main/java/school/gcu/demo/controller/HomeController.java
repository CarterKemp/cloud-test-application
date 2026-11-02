package school.gcu.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import school.gcu.demo.model.Task;
import school.gcu.demo.service.TaskService;

@Controller
public class HomeController {

    private final TaskService taskService;

    public HomeController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute(
                "tasks",
                taskService.getAllTasks());

        return "home";
    }

    @GetMapping("/tasks/new")
    public String showCreateForm(Model model) {

        model.addAttribute(
                "task",
                new Task());

        return "TaskForm";
    }

    @PostMapping("/tasks")
    public String createTask(Task task) {

        taskService.createTask(task);

        return "redirect:/";
    }

    @GetMapping("/tasks/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "task",
                taskService.getTaskById(id));

        return "TaskEdit";
    }

    @PostMapping("/tasks/update/{id}")
    public String updateTask(
            @PathVariable Long id,
            Task task) {

        taskService.updateTask(id, task);

        return "redirect:/";
    }

    @GetMapping("/tasks/delete/{id}")
    public String showDeletePage(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "task",
                taskService.getTaskById(id));

        return "TaskDelete";
    }

    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(
            @PathVariable Long id) {

        taskService.deleteTask(id);

        return "redirect:/";
    }
}