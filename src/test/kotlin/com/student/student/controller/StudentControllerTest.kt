package com.student.student.controller

import com.student.student.service.StudentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest(StudentController::class)
@AutoConfigureWebTestClient
class StudentControllerTest {
    @Autowired
    lateinit var client: WebTestClient

    @Autowired
    lateinit var studentService: StudentService


}