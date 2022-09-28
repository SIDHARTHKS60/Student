package com.student.student.service

import com.student.student.model.Student
import com.student.student.repository.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Flux

class StudentService (
    @Autowired
    val studentRepository: StudentRepository
){
    fun findAllStudents() : Flux<Student> {
        return studentRepository.findAll()
        }

}