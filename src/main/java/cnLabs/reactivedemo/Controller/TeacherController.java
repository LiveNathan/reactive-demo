package cnLabs.reactivedemo.Controller;

import cnLabs.reactivedemo.Model.Teacher;
import cnLabs.reactivedemo.Service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @Bean
    public RouterFunction<ServerResponse> getAllTeachers() {
        return route(GET("/teachers"),
                req -> ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(teacherService.getAllTeachers(), Teacher.class));
    }

    // endpoint and route in one go
    @Bean
    public RouterFunction<ServerResponse> getTeacher() {
        return route(GET("/teachers/{id}"),
                req -> ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(teacherService.getTeacher(Integer.parseInt(req.pathVariable("id"))), Teacher.class));
    }

    @Bean
    public RouterFunction<ServerResponse> addNewTeacher() {
        return route(POST("/teachers"), request -> {
            Mono<Teacher> teacherMono = request.bodyToMono(Teacher.class);
            return teacherService.saveTeacher(teacherMono.block())
                    .flatMap(teacher -> ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(Mono.just(teacher), Teacher.class));
        });
    }


    @Bean
    RouterFunction<ServerResponse> updateTeacherRoute() {
        return route(PUT("/update-teachers-route/{id}"),
                req -> {
                    Integer teacherId = Integer.parseInt(req.pathVariable("id"));
                    Mono<Teacher> teacherMono = req.bodyToMono(Teacher.class);
                    return teacherService.updateTeacher(teacherId, teacherMono)
                            .flatMap(teacher -> ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(Mono.just(teacher), Teacher.class))
                            .switchIfEmpty(ServerResponse.notFound()
                                    .build());
                });
    }
//    public Mono<ServerResponse> getAllTeachers(ServerRequest request) {
//        Flux<Teacher> teachers = teacherService.getAllTeachers();
//        return ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(teachers, Teacher.class);

//    }


    public Mono<ServerResponse> updateTeacher(ServerRequest request) {
        String teacherId = request.pathVariable("id");
        Mono<Teacher> teacherMono = request.bodyToMono(Teacher.class);
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(teacherService.updateTeacher(Integer.valueOf(teacherId), teacherMono), Teacher.class);
    }

    public Mono<ServerResponse> deleteTeacher(ServerRequest request) {
        String teacherId = request.pathVariable("id");
        return teacherService.getTeacher(Integer.valueOf(teacherId))
                .flatMap(teacher -> teacherService
                        .deleteTeacher(teacher)
                        .then(ok().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}