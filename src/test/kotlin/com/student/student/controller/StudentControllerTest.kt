package com.student.student.controller

import com.student.student.model.Student
import com.student.student.service.StudentService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux

@WebFluxTest(StudentController::class)
@AutoConfigureWebTestClient
class StudentControllerTest {
    @Autowired
    lateinit var client: WebTestClient

    @Autowired
    lateinit var studentService: StudentService

    @Test
    fun `should return all the students` () {

        val student1= Student(111,"Aaaa A A","Class 7")
        val student2=Student(222,"Bbbbb B B","Class 8")

        val expectedResult= listOf(
            mapOf(
                "studentId" to 111,
                "studentName" to "Aaaa A A",
                "studentClass" to "Class 7"
            ),
            mapOf(
                "studentId" to 222,
                "studentName" to "Bbbb B B",
                "studentClass" to "Class *"
            )
        )

        every{
            studentService.findAllStudents()
        }returns Flux.just(student1, student2)
    }
}