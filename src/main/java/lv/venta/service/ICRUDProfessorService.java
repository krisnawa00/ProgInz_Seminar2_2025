package lv.venta.service;

import lv.venta.model.Professor;
import lv.venta.model.enums.Degree;

public interface ICRUDProfessorService extends ICRUDBase<Professor>{
	
	//R - retrieve all, R - retrieve by id, D - delete by id būs jau no ICRUDBASE 
	
	//C - create
	public abstract void create(String name, String surname, Degree degree) throws Exception;

	//U - update
	//Updated return type from void to Professor to support @CachePut
	public abstract Professor updateById(int id, String name, String surname, Degree degree) throws Exception;
		
}