package cnLabs.reactivedemo.Config;

import cnLabs.reactivedemo.Controller.StudentController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouteConfig {

    @Bean
    RouterFunction<ServerResponse> routes(StudentController studentController) {
        return route(
                GET("/students")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), studentController::getAllStudents)
                .andRoute(GET("/students/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), studentController::getStudent)
                .andRoute(POST("/students")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), studentController::addNewStudent)
                .andRoute(PUT("/students/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), studentController::updateStudent)
                .andRoute(DELETE("/students/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), studentController::deleteStudent);
    }
}