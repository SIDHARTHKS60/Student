package com.student.student.controller

import com.student.student.model.Student
import com.student.student.service.StudentService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("students")
class StudentController
    (val studentService : StudentService) {

    @GetMapping("lists")
    fun getAllStudents(): Flux<Student> {
        return studentService.findAllStudents()
    }

    @PostMapping("add")
    fun addStudent(@RequestBody student:Student): Mono<Student> {
        return studentService.addStudent(student)
    }

    @GetMapping("find/{studentId}")
    fun findStudentById(@PathVariable("studentId") studentId: Int):Mono<Student>{
        return studentService.findStudentById(studentId)
    }
    @DeleteMapping("delete/{studentId}")
    fun deleteStudentById(@PathVariable("studentId") studentId: Int): Mono<Void> {
        return studentService.deleteStudentById(studentId)
    }

    @PutMapping("update/{userId}")
    fun updateStudentById(@PathVariable("studentId") studentId: Int, @RequestBody student: Student): Mono<Student> {
        return studentService.updateStudent(studentId, student)
    }
}