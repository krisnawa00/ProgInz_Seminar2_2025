package lv.venta.service;

import lv.venta.model.Course;
import lv.venta.model.Grade;

import lv.venta.model.Student;

public interface ICRUDGradeService extends ICRUDBase<Grade>{
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE 
	
	//C - create
	public abstract void create(int grvalue, Student student, Course course) throws Exception;
		
	//U - update
	public abstract void updateById(int id, int grvalue, Student student, Course course) throws Exception;

}
