package cnLabs.reactivedemo.Repo;

import cnLabs.reactivedemo.Model.Teacher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TeacherRepository extends ReactiveCrudRepository<Teacher, Integer> {

}