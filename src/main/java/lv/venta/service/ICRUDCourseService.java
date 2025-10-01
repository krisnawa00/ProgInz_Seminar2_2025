package lv.venta.service;

import java.util.ArrayList;

import lv.venta.model.Course;
import lv.venta.model.Professor;
import lv.venta.model.dto.CourseInfoDTO;

public interface ICRUDCourseService extends ICRUDBase<Course> {
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE 
	
	//C - create
	public abstract void create(String title, int creditpoints, Professor professor) throws Exception;
	public abstract void create(ArrayList<CourseInfoDTO> courses) throws Exception;
	//U - update
	public abstract void updateById(int id, String title, int creditpoints, Professor professor) throws Exception;
	
}