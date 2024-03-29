package com.khaledsaleh.studentsapp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    final private StudentService studentService;
    @PostMapping
    public Mono<Student> save(@RequestBody Student student){
        return studentService.save(student);
    }

    @GetMapping
    public Flux<Student> findAll(){
        return studentService.findAll().delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/{id}")
    public Mono<Student> findById(@PathVariable Integer id){
        return studentService.findById(id);
    }
}
