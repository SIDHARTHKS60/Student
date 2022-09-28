package com.student.student.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Student (
    @Id
    var studentId: Int?,
    var studentName: String,
    var studentClass: String
)