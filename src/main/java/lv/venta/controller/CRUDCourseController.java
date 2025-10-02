package lv.venta.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lv.venta.model.Course;
import lv.venta.model.dto.CourseInfoDTO;
import lv.venta.service.ICRUDCourseService;

@RestController
@RequestMapping("/crud/course")
public class CRUDCourseController {
	
	@Autowired
	private ICRUDCourseService courseService;
	
	@PostMapping("/create/multiple")
	public ResponseEntity<?> postCreateMultipleCourses
					(@RequestBody @Valid ArrayList<CourseInfoDTO> coursesForSaving, BindingResult result){
		if(result.hasErrors()) {
			ResponseEntity<Integer> response =
					new ResponseEntity<Integer>(result.getErrorCount(), HttpStatusCode.valueOf(500));
			return response;
		}
		
		
		try {
			courseService.create(coursesForSaving);
			ArrayList<Course> coursesFromDb = courseService.retrieveAll();
			ResponseEntity<ArrayList<Course>> response = 
					new ResponseEntity<ArrayList<Course>>(coursesFromDb, HttpStatusCode.valueOf(200));
			return response;
			
		} catch (Exception e) {
			ResponseEntity<String> response =
					new ResponseEntity<String>(e.getMessage(), HttpStatusCode.valueOf(500));
			return response;
		}
		
	}

}