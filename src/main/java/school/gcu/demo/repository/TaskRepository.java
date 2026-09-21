package school.gcu.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.gcu.demo.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}