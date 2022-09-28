package com.student.student.controller

import com.student.student.model.Student
import com.student.student.service.StudentService
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(StudentController::class)
@AutoConfigureWebTestClient
class StudentControllerTest {
    @Autowired
    lateinit var client: WebTestClient

    @Autowired
    lateinit var studentService: StudentService

    @Test
    fun `should return all the students` () {

        val student1 = Student(111,"Aaaa A A","Class 7")
        val student2 = Student(222,"Bbbbb B B","Class 8")

        val expectedResult= listOf(
            mapOf(
                "studentId" to 111,
                "studentName" to "Aaaa A A",
                "studentClass" to "Class 7"
            ),
            mapOf(
                "studentId" to 222,
                "studentName" to "Bbbb B B",
                "studentClass" to "Class 8"
            ),
        )

        every{
            studentService.findAllStudents()
        }returns Flux.just(student1, student2)

        val response=client.get()
            .uri("/students/lists")
            .accept(MediaType.APPLICATION_JSON)
            .exchange() //invoking the end point
            .expectStatus().is2xxSuccessful
            .returnResult<Any>()
            .responseBody

        response.blockFirst() shouldBe expectedResult[0]
        //response.blockLast() shouldBe expectedResult[1]

        verify(exactly = 1) {
            studentService.findAllStudents()
        }
    }
    @Test
    fun `should create student when create api is being called`() {

        val exepectedResponse = mapOf(
            "studentId" to 999,
            "studentName" to "Rahul",
            "studentClass" to "Class 7")

        val student = Student(999, "Rahul","Class 7")

        every {
            studentService.addStudent(student)
        } returns Mono.just(student)

        val response = client.post()
            .uri("/students/add")
            .bodyValue(student)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<Any>().responseBody

        response.blockFirst() shouldBe exepectedResponse

        verify(exactly = 1) {
            studentService.addStudent(student)
        }
    }

    @Test
    fun `should be able to update the student`() {

        val expectedResult = mapOf(
            "studentId" to 999,
            "studentName" to "Rahul",
            "studentClass" to "Class 7")

        val student = Student(999, "Rahul","Class 7")

        every {
            studentService.updateStudent(999,student)
        } returns Mono.just(student)

        val response = client.put()
            .uri("/students/update/999")
            .bodyValue(student)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<Any>()
            .responseBody

        response.blockFirst() shouldBe expectedResult

        verify(exactly = 1) {
            studentService.updateStudent(999,student)
        }
    }

    @Test
    fun `should be able to delete the user`() {
        val expectedResult = mapOf(
            "studentId" to 999,
            "studentName" to "Rahul",
            "studentClass" to "Class 7")

        val student = Student(999, "Rahul","Class 7")

        every {
            studentService.deleteStudentById(999) }returns  Mono.empty()

        val response = client.delete()
            .uri("/students/delete/999")
            .exchange()
            .expectStatus().is2xxSuccessful

        verify(exactly = 1) {
            studentService.deleteStudentById(999)
        }
    }

    @Test
    fun `should return a single student`() {
        val expectedResult = mapOf(
            "studentId" to 999,
            "studentName" to "Rahul",
            "studentClass" to "Class 7")

        val student = Student(999, "Rahul","Class 7")

        every {
            studentService.findStudentById(999)
        } returns Mono.just(student)

        val response = client.get()
            .uri("/students/find/999")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<Any>().responseBody

        response.blockFirst() shouldBe expectedResult

        verify(exactly = 1) {
            studentService.findStudentById(999)
        }

    }
    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun userService() = mockk<StudentService>()
    }
}