package com.student.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.exception.StudentNotFoundException;
import com.student.model.Student;
import com.student.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentRestController {

	@Autowired
	private StudentService studentService;
	
	private Logger logger = LoggerFactory.getLogger(StudentRestController.class);
	
	@GetMapping("/{sId}")
	public ResponseEntity<Student> getStudent(@PathVariable("sId") Long sId) {
		
		logger.info("Inside of getStudent function of STUDENT REST CONTROLLER"
				+ " trying to fetch student with ID: {}", sId);
		
		try {
			Student student = studentService.getStudent(sId);
			logger.info("Student found with ID: {}", student.toString());
			return ResponseEntity.ok().body(student);
		}catch(StudentNotFoundException studentNotFoundException) {
			logger.error("Student not found with Id: {}",sId);
			return ResponseEntity.ok().body(null);
		}
	}
	
	@PostMapping
	public Student saveStudent(@RequestBody Student student) {
		
		logger.info("Inside of saveStudent function of STUDENT REST CONTROLLER"
				+ " trying to save student data : {}", student.toString());
		
		student.toString();
		return studentService.saveStudent(student);
	}
	
	@PutMapping("/{sId}")
	public ResponseEntity<Student> updateStudent(@PathVariable("sId") Long sId, @RequestBody Student studentDetails) {
		
		logger.info("Inside of updateStudent function of STUDENT REST CONTROLLER"
				+ " trying to update student data: {} of id : {}", studentDetails.toString(),sId);
		
		try {
			Student student = studentService.updateStudent(sId, studentDetails);
			logger.info("Student updated : {}", student.toString());
			return ResponseEntity.ok().body(student);
		}catch(StudentNotFoundException studentNotFoundException) {
			logger.error("Student not found with Id: {}",sId);
			return ResponseEntity.ok().body(null);
		}
	}
	
	@DeleteMapping("/{sId}")
	public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable("sId") Long sId) {
		
		logger.info("Inside of deleteStudent function of STUDENT REST CONTROLLER"
				+ " trying to delete student with id : {}", sId);
		
		Map<String, Boolean>response = new HashMap<>();
		try{
			response.put("value Deleted", studentService.deleteStudent(sId));
			logger.info("Student deleted successfully ? : {}",response.get("value Deleted"));
		}catch(StudentNotFoundException studentNotFoundException) {
			logger.error("Student not found with Id: {} cannot delete",sId);
			response.put("value Deleted", false);
		}
		return ResponseEntity.ok().body(response); 	
	}
	
	@GetMapping("/getAllStudents")
	public ResponseEntity<List<Student>> getAllStudents(){
		return ResponseEntity.ok().body(studentService.getAllStudents());
	}
	
}