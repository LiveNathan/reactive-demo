package cnLabs.reactivedemo.Config;

import cnLabs.reactivedemo.Repo.StudentRepository;
import cnLabs.reactivedemo.Repo.TeacherRepository;
import cnLabs.reactivedemo.Service.StudentService;
import cnLabs.reactivedemo.Service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final TeacherRepository teacherRepository;
    private final TeacherService teacherService;

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        this.studentRepository
                .deleteAll()
                .thenMany(studentService.saveAll("Evan", "Ji", "Harry", "Ryan", "Rick", "Chuck"))
                .thenMany(studentService.getAllStudents())
                .subscribe(s -> System.out.println(s.getName()));

        this.teacherRepository
                .deleteAll()
                .thenMany(teacherService.saveAll("Eleri", "Xander", "Rosie", "Darcy", "Trinity", "Alexis"))
                .thenMany(teacherService.getAllTeachers())
                .subscribe(teacher -> System.out.println(teacher.getName()));
    }
}