package com.student.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.student.model.Student;

public interface StudentRepository extends Neo4jRepository<Student, Long> {

}