package com.student.student.repository

import com.student.student.model.Student
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : ReactiveCrudRepository<Student,Int>{
}