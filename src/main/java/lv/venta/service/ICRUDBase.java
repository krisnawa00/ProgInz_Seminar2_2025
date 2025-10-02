package lv.venta.service;

import java.util.ArrayList;

public interface ICRUDBase <Ttype> {
	//R - retrieve all
	public abstract ArrayList<Ttype> retrieveAll() throws Exception;
	
	//R - retrieve by id
	public abstract Ttype retreiveById(int id) throws Exception;
	
	//D - delete
	public abstract void deleteById(int id) throws Exception;
}
