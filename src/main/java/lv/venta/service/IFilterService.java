package lv.venta.service;

import java.util.ArrayList;

import lv.venta.model.Course;
import lv.venta.model.Grade;
import lv.venta.model.Professor;
import lv.venta.model.Student;
import lv.venta.model.enums.Degree;

public interface IFilterService {
	
	public abstract ArrayList<Grade> selectGradesByStudentId(int id) throws Exception;
		
	public abstract ArrayList<Course> selectCoursesByStudentId(int id) throws Exception;

	public abstract ArrayList<Course> selectCoursesByProfessorId(int id) throws Exception;
	
	public abstract float calculateAVGGradeInCourseId(int id) throws Exception;
	
	public abstract ArrayList<Student> selectAllStudentsWithFailedGrades() throws Exception;
	
	public abstract ArrayList<Professor> selectAllProfessorsByDegree(Degree degree) throws Exception;

	
}
