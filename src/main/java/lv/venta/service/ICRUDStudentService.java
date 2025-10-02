package lv.venta.service;

import lv.venta.model.Student;

public interface ICRUDStudentService extends ICRUDBase<Student> {
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE 
	
	//C - create
	public abstract void create(String name, String surname) throws Exception;

	//U - update
	public abstract void updateById(int id, String name, String surname) throws Exception;
		
}
