package com.student.student.service

import com.student.student.model.Student
import com.student.student.repository.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class StudentService (
    @Autowired
    val studentRepository: StudentRepository
){
    fun findAllStudents() : Flux<Student> {
        return studentRepository.findAll()
    }

    fun addStudent(student:Student): Mono<Student> {
        return studentRepository.save(student)
    }
    fun findStudentById(studentId: Int):Mono<Student>{
        return studentRepository.findById(studentId)
    }

    fun deleteStudentById(studentId: Int): Mono<Void> {
        return studentRepository.deleteById(studentId)
    }

    fun updateStudent(studentId: Int, student: Student): Mono<Student> {
        return studentRepository.findById(studentId)
            .flatMap {
                it.studentId = student.studentId
                it.studentName = student.studentName
                it.studentClass = student.studentClass
                studentRepository.save(it)
            }
    }
}