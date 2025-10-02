package lv.venta.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lv.venta.model.Course;
import lv.venta.model.Professor;
import lv.venta.model.enums.Degree;
import lv.venta.repo.ICourseRepo;
import lv.venta.repo.IProfessorRepo;
import lv.venta.service.ICRUDProfessorService;

@Service
public class CRUDProfessorServiceImpl implements ICRUDProfessorService{

	@Autowired
	private IProfessorRepo profRepo;
	
	@Autowired
	private ICourseRepo courseRepo;
	
	//Added @Cacheable annotation - caches all professors
	@Override
	@Cacheable(value = "professors")
	public ArrayList<Professor> retrieveAll() throws Exception {
		if(profRepo.count() == 0)
		{
			throw new Exception("Nav neviena profesora DB");
		}
		
		System.out.println("Fetching all professors from database...");
		return (ArrayList<Professor>) profRepo.findAll();
	}

	//@Cacheable annotation - caches individual professor by id
	@Override
	@Cacheable(value = "professor", key = "#id")
	public Professor retreiveById(int id) throws Exception {
		if(id < 0)
		{
			throw new Exception("Id nevar būt negatīvs");
		}
		
		if(!profRepo.existsById(id))
		{
			throw new Exception("Professors ar tādu id neeksistē");
		}
		
		System.out.println("Fetching professor with id " + id + " from database...");
		Professor retrievedProduct = profRepo.findById(id).get();
		return retrievedProduct;
	}

	//@CacheEvict annotations - clears cache when deleting
	@Override
	@CacheEvict(value = {"professor", "professors"}, allEntries = true)
	public void deleteById(int id) throws Exception {
		Professor professorForDelete = retreiveById(id);
		ArrayList<Course> coursesForProfessor = courseRepo.findByProfessorPid(id);
		
		for(Course tempC: coursesForProfessor) {
			tempC.setProfessor(null);
			courseRepo.save(tempC);
		}
		
		profRepo.delete(professorForDelete);
		System.out.println("Professor deleted and cache cleared");
	}

	//@CacheEvict annotations - clears cache when creating new professor
	@Override
	@CacheEvict(value = "professors", allEntries = true)
	public void create(String name, String surname, Degree degree) throws Exception {
		if(name == null || surname == null || degree == null)
		{
			throw new Exception("Ievades parametri nav pareizi");
		}
		
		if(profRepo.existsByNameAndSurnameAndDegree(name, surname, degree))
		{
			throw new Exception("Tāds profesors jau eksistē");
		}
		
		Professor newProfessor = new Professor(name, surname, degree);
		profRepo.save(newProfessor);
		System.out.println("New professor created and cache cleared");
	}

	//Added @CachePut and @CacheEvict annotations - updates cache when updating professor
	@Override
	@CachePut(value = "professor", key = "#id")
	@CacheEvict(value = "professors", allEntries = true)
	public Professor updateById(int id, String name, String surname, Degree degree) throws Exception {
		if(name == null || surname == null || degree == null)
		{
			throw new Exception("Ievades parametri nav pareizi");
		}
		Professor retrievedProf = retreiveById(id);
		retrievedProf.setName(name);
		retrievedProf.setSurname(surname);
		retrievedProf.setDegree(degree);
		profRepo.save(retrievedProf);
		System.out.println("Professor updated and cache refreshed");
		
		return retrievedProf;
	}

}