package cnLabs.reactivedemo.Repo;

import cnLabs.reactivedemo.Model.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface StudentRepository extends ReactiveCrudRepository<Student, Integer> {

}