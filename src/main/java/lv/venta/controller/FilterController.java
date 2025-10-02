
package lv.venta.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lv.venta.model.Course;
import lv.venta.model.Grade;
import lv.venta.model.Professor;
import lv.venta.model.Student;
import lv.venta.model.dto.GradeInfoDTO;
import lv.venta.model.enums.Degree;
import lv.venta.service.IFilterService;

@RestController
@RequestMapping("/filter")
public class FilterController {

	@Autowired
	private IFilterService filtService;
	
	@GetMapping("/grades/student/{id}")//localhost:8080/filter/grades/student/1
	public ResponseEntity<?> getControllerGetAllGradesForStudent(
			@PathVariable (name = "id") int id)
	{
		try
		{
			ArrayList<GradeInfoDTO> filteredGrades = filtService.selectGradesByStudentId(id);
			ResponseEntity<ArrayList<GradeInfoDTO>> response = 
					new ResponseEntity<ArrayList<GradeInfoDTO>>(filteredGrades, HttpStatusCode.valueOf(200));
			return response;
		}catch (Exception e) {
			ResponseEntity<String> response =
					new ResponseEntity<String>(e.getMessage(), HttpStatusCode.valueOf(500));
			return response;
		}
	}
//TODO rediģēt pāŗejās kontrolieru funkcijas uz ResponseEntity
	@GetMapping("/courses/student/{id}")//localhost:8080/filter/courses/student/1
	public String getControllerGetAllCoursesForStudent(@PathVariable(name = "id") int id, Model model)
	{
		try
		{
			ArrayList<Course> filteredCourses = filtService.selectCoursesByStudentId(id);
			model.addAttribute("package", filteredCourses);
			return "show-courses-page";//parādīs show-courses-page.html lapu ar izfiltrētime kursiem
		}
		catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error-page";//parādīt show-error-page.html lapu, kura būs kļudas ziņojums

		}
		
	}
	@GetMapping("/courses/professor/{id}")//localhost:8080/filter/courses/professor/1
	public String getControllerGetAllCoursesForprofessor(@PathVariable(name = "id") int id, Model model)
	{
		try
		{
			ArrayList<Course> filteredCourses = filtService.selectCoursesByProfessorId(id);
			model.addAttribute("package", filteredCourses);
			return "show-courses-page";//parādīs show-courses-page.html lapu ar izfiltrētime kursiem
		}
		catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error-page";//parādīt show-error-page.html lapu, kura būs kļudas ziņojums

		}
		
	}
	
	@GetMapping("/grades/avg/course/{id}")//localhost:8080/filter/grades/avg/course/1
	public String getControllerGetAvgGradeInCourse(@PathVariable(name = "id") int id, Model model)
	{
		try
		{
			float avgGrade = filtService.calculateAVGGradeInCourseId(id);
			model.addAttribute("package", ("Vidējā atzīmju vērtība ir " + avgGrade));
			return "data-page";//parādīs data-page.html lapu ar vidējo vērtību konkrētajā kursā
		}
		catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error-page";//parādīt show-error-page.html lapu, kura būs kļudas ziņojums

		}
		
	}
	
	@GetMapping("/students/failed")//localhost:8080/filter/students/failed
	public String getControllergetFailedStudents(Model model)
	{
		try
		{
			ArrayList<Student> failedStudents = filtService.selectAllStudentsWithFailedGrades();
			model.addAttribute("package", failedStudents);
			return "show-students-page";//parādīs show-students-page.html lapu ar izfiltrētajiem studentiem
		}
		catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error-page";//parādīt show-error-page.html lapu, kura būs kļudas ziņojums
		}
	}
	
	@GetMapping("/professors/degree/phd")//localhost:8080/filter/professors/degree/phd
	public String getControllerGetProfessorsWithPHD(Model model) {
		try
		{
		ArrayList<Professor> professorWithPHD = filtService.selectAllProfessorsByDegree(Degree.doktora);
		model.addAttribute("professors", professorWithPHD);
		return "show-all-professors-page";
		
		}
		catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error-page";//parādīt show-error-page.html lapu, kura būs kļudas ziņojums
		}
	}
	
}
