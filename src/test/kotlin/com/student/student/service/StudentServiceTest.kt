package com.student.student.service

import com.student.student.model.Student
import com.student.student.repository.StudentRepository
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class StudentServiceTest {

    val student1 = Student(999, "Aaaa A A", "Class 7")
    val student2 = Student(888, "Bbbb B B", "Class 8")

    private val studentRepository = mockk<StudentRepository>() {

        every {
            findAll()
        } returns Flux.just(student1,student2)

        every {
            findById(999)
        }returns Mono.just(student1)
    }

    private val studentService = StudentService(studentRepository)

    @Test
    fun `test adding Student`() {

        every{
             studentRepository.save(student1)
        } returns Mono.just(student1)

        val addedStudent = studentService.addStudent(student1).block()

        addedStudent shouldBe student1
    }
    @Test
    fun `should return student when findAllStudents  method is called`() {

        val firstStudent = studentService.findAllStudents().blockFirst()
        val secondStudent = studentService.findAllStudents().blockLast()

        if (firstStudent != null) {
            firstStudent shouldBe student1
        }
        if (secondStudent != null) {
            secondStudent shouldBe student2
        }
    }
    @Test
    fun `test Find Student By Id`() {

        val result=studentRepository.findById(999).block()

        result shouldBe student1

    }

    @Test
    fun `delete Student By Id`() {

        every{
            studentRepository.deleteById(999)
        }returns Mono.empty()
    }

    @Test
    fun `test update Student`() {

        every{
            studentRepository.save(student1)
        }returns Mono.just(student1)

        val updatedStudent = studentService.updateStudent(999,student1).block()

        updatedStudent shouldBe student1
    }
}