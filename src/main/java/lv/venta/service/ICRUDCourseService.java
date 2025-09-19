package lv.venta.service;

import lv.venta.model.Course;
import lv.venta.model.Professor;

public interface ICRUDCourseService extends ICRUDBase<Course> {
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE 
	
	//C - create
	public abstract void create(String title, int creditpoints, Professor professor) throws Exception;
	
	//U - update
	public abstract void updateById(int id, String title, int creditpoints, Professor professor) throws Exception;
	
}
