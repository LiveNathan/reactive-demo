package cnLabs.reactivedemo.Service;

import cnLabs.reactivedemo.Model.Student;
import cnLabs.reactivedemo.Repo.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final TransactionalOperator transactionalOperator;

    public Mono<Student> saveStudent(Student student) {
        return transactionalOperator.transactional(studentRepository.save(student));
    }

    // saveAll() takes in String args (names) and uses them to create/persist new Students to the database.
    public Flux<Student> saveAll(String... names) {
        final Flux<Student> reservationFlux = Flux.fromArray(names)
                .map(name -> new Student(null, name))
                .doOnNext(r -> Assert.isTrue(isNameValid(r), "the first letter must be uppercase"))
                .flatMap(this.studentRepository::save);

        // TransactionalOperator enables transaction management, and is used by returning transactionalOperator.transactional(reservationFlux)
        return transactionalOperator.transactional(reservationFlux);
    }

    public Flux<Student> getAllStudents() {
        return transactionalOperator.transactional(studentRepository.findAll());
    }

    public Mono<Student> getStudent(Integer id) {
        return transactionalOperator.transactional(studentRepository.findById(id));
    }

    public Mono<Void> deleteStudent(Student student) {
        return transactionalOperator.transactional(studentRepository.delete(student));
    }

    public Mono<Student> updateStudent(Integer id, Mono<Student> updatedStudent) {
        return getStudent(id)
                .flatMap(student -> updatedStudent.flatMap(student1 -> {
                    student.setName(student1.getName());
                    return saveStudent(student);
                }));
    }

    // isNameValid() checks if the first letter of name field of the Student object is capitalized or not. If the first letter is not capitalized, then Assert.isTrue() will throw an IllegalArgumentException.
    private boolean isNameValid(Student student) {
        String name = student.getName();
        return name != null && Character.isUpperCase(name.charAt(0));
    }
}