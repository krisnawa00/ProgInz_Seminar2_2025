
package lv.venta.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lv.venta.model.Course;
import lv.venta.model.Grade;
import lv.venta.model.Professor;
import lv.venta.model.Student;
import lv.venta.model.dto.GradeInfoDTO;
import lv.venta.model.enums.Degree;
import lv.venta.repo.ICourseRepo;
import lv.venta.repo.IGradeRepo;
import lv.venta.repo.IProfessorRepo;
import lv.venta.repo.IStudentRepo;
import lv.venta.service.IFilterService;

@Service
public class FilterServiceImpl implements IFilterService {

	@Autowired
	private IStudentRepo studRepo;
	
	@Autowired
	private IGradeRepo grRepo;
	
	@Autowired
	private ICourseRepo couRepo;
	
	@Autowired
	private IProfessorRepo profRepo;
	
	//Added @Cacheable annotation - caches grades by student id
	@Override
	@Cacheable(value = "studentGrades", key = "#id")
	public ArrayList<GradeInfoDTO> selectGradesByStudentId(int id) throws Exception {
		if(id <= 0){
			throw new Exception("Id nevar būt negatīvs");
		}
		
		if(!studRepo.existsById(id)) {
			throw new Exception("Students ar id: " + id + " neeksistē");
		}
		
		ArrayList<Grade> gradesForStudent = grRepo.findByStudentStid(id);

		if(gradesForStudent.isEmpty()) {
			throw new Exception("Studentam ar id: " + id + " nav piesaistīta ne viena atzīme");
		}
		
		System.out.println("Fetching grades for student " + id + " from database...");
		ArrayList<GradeInfoDTO> gradesForSending = new ArrayList<>();
		for(Grade tempG : gradesForStudent) {
			GradeInfoDTO info = new GradeInfoDTO(tempG.getGrvalue(),
					tempG.getStudent().getName(), tempG.getStudent().getSurname(),
					tempG.getCourse().getTitle());
			
			gradesForSending.add(info);
		}
		
		return gradesForSending;
	}

	//Added @Cacheable annotation - caches courses by student id
	@Override
	@Cacheable(value = "studentCourses", key = "#id")
	public ArrayList<Course> selectCoursesByStudentId(int id) throws Exception {
		if(id <= 0){
			throw new Exception("Id nevar būt negatīvs");
		}
		
		if(!studRepo.existsById(id)) {
			throw new Exception("Students ar id: " + id + " neeksistē");
		}
		
		System.out.println("Fetching courses for student " + id + " from database...");
		ArrayList<Course> result = couRepo.findByGradesStudentStid(id);
		if(result.isEmpty()) {
			throw new Exception("Studentam ar id: " + id + " nav piesaistīts ne viens kurss");
		}
		
		return result;
	}

	//Added @Cacheable annotation - caches courses by professor id
	@Override
	@Cacheable(value = "professorCourses", key = "#id")
	public ArrayList<Course> selectCoursesByProfessorId(int id) throws Exception {
		if(id <= 0){
			throw new Exception("Id nevar būt negatīvs");
		}
		
		if(!profRepo.existsById(id)) {
			throw new Exception("Professors ar id: " + id + " neeksistē");
		}
		
		System.out.println("Fetching courses for professor " + id + " from database...");
		ArrayList<Course> result = couRepo.findByProfessorPid(id);
		
		if(result.isEmpty()) {
			throw new Exception("Professoram ar id: " + id + " nav piesaistīts neviens kurss");
		}
		
		return result;
	}

	//Added @Cacheable annotation - caches average grade by course id
	@Override
	@Cacheable(value = "courseAvgGrade", key = "#id")
	public float calculateAVGGradeInCourseId(int id) throws Exception {
		if(id <= 0){
			throw new Exception("Id nevar būt negatīvs");
		}
		
		if(!couRepo.existsById(id)) {
			throw new Exception("Kurss ar id: " + id + " neeksistē");
		}
		
		System.out.println("Calculating average grade for course " + id + " from database...");
		float result = grRepo.calculateAVGgradeInCourse(id);
		if(result == 0)
		{
			throw new Exception("Kursam ar id: " + id + " nav piesaistīts neviena atzīme");
		}
		
		return result;
	}

	//Added @Cacheable annotation - caches failed students
	@Override
	@Cacheable(value = "failedStudents")
	public ArrayList<Student> selectAllStudentsWithFailedGrades() throws Exception {
		System.out.println("Fetching failed students from database...");
		ArrayList<Student> result = studRepo.findByGradesGrvalueLessThan(4);
		if(result.isEmpty()) {
			throw new Exception("Datubāze nav neviens stuents ar nesekmīgu atzīmi");
		}
		
		return result;
	}

	//Added @Cacheable annotation - caches professors by degree
	@Override
	@Cacheable(value = "professorsByDegree", key = "#degree")
	public ArrayList<Professor> selectAllProfessorsByDegree(Degree degree) throws Exception {
		System.out.println("Fetching professors with degree " + degree + " from database...");
		ArrayList<Professor> result = profRepo.findByDegree(degree);
		if(result.isEmpty()) {
			throw new Exception("Datubāze nav neviens professors ar " + degree + " grādu");
		}
		
		return result;
	}

}