package com.student.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.student.exception.StudentNotFoundException;
import com.student.model.Student;
import com.student.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	private Logger logger = LoggerFactory.getLogger(StudentService.class);
	
	
	/*
	 * this function will throw an exception if student id is not present in the database
	 * else it will successfully fetch the student information and will return it
	*/
	public Student getStudent(Long sId) throws StudentNotFoundException{
		
		logger.info("Inside of getStudent function of STUDENT SERVICE CLASS"
				+ " trying to fetch student with ID: {}", sId);
		
		return studentRepository.findById(sId)
				.orElseThrow(()-> new StudentNotFoundException("Student not found on :"+sId));
	}


	/*
	 * this function will throw an exception is student obj is null
	 * else it will success fully save the student data into DB will return the saved object
	*/
	public Student saveStudent(Student student) throws StudentNotFoundException{
		
		
		if(student == null)
			throw new StudentNotFoundException("Invalid Student Details");
		
		logger.info("Inside of saveStudent function of STUDENT SERVICE CLASS"
				+ " trying to save student data : {}", student.toString());

		
		return studentRepository.save(student);
	}

	
	/*
	 * this function will throw an exception if either student detail object is null
	 * else it will return the updated value 
	*/
	public Student updateStudent(Long sId, Student studentDetails) throws StudentNotFoundException{
		
		if(studentDetails==null)
			throw new StudentNotFoundException("Invalid Student Details");
		
		logger.info("Inside of updateStudent function of STUDENT SERVICE CLASS"
				+ " trying to update student data: {} of id : {}", studentDetails.toString(),sId);
		
		Student student = studentRepository.findById(sId)
				.orElseThrow(()-> new StudentNotFoundException("Student not found on :"+sId));
		
		student.copyPropertiesExceptId(studentDetails);
		return studentRepository.save(student);
	}

	
	/*
	 * this function will throw an exception if student id not found in the DB
	 * else it will success fully delete the student object
	 */
	public Boolean deleteStudent(Long sId) throws StudentNotFoundException{
		
		logger.info("Inside of deleteStudent function of STUDENT SERVICE CLASS"
				+ " trying to delete student with id : {}", sId);
		
		if(!studentRepository.existsById(sId))
			throw new StudentNotFoundException("Student not found on :"+sId);
			
		studentRepository.deleteById(sId);
		return true;
	}


	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	
}