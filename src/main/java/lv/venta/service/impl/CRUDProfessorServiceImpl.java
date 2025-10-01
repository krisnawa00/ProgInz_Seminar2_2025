package lv.venta.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.Course;
import lv.venta.model.Professor;
import lv.venta.model.enums.Degree;
import lv.venta.repo.ICourseRepo;
import lv.venta.repo.IProfessorRepo;
import lv.venta.service.ICRUDProfessorService;

@Service
public class CRUDProfessorServiceImpl implements ICRUDProfessorService{

	//TODO uztaisīt arī CRUD servisus priekš Student, Grade un Course
	@Autowired
	private IProfessorRepo profRepo;
	
	@Autowired
	private ICourseRepo courseRepo;
	
	@Override
	public ArrayList<Professor> retrieveAll() throws Exception {
		if(profRepo.count() == 0)
		{
			throw new Exception("Nav neviena profesora DB");
		}
		
		return (ArrayList<Professor>) profRepo.findAll();
	}

	@Override
	public Professor retreiveById(int id) throws Exception {
		if(id < 0)
		{
			throw new Exception("Id nevar būt negatīvs");
		}
		
		if(!profRepo.existsById(id))
		{
			throw new Exception("Professors ar tādu id neeksistē");
		}
		
		Professor retrievedProduct = profRepo.findById(id).get();
		return retrievedProduct;
	}

	@Override
	public void deleteById(int id) throws Exception {
		//TODO atsaistēt profesoru no kursiem, kam tas ir piesaistīts
		Professor professorForDelete = retreiveById(id);
		ArrayList<Course> coursesForProfessor = courseRepo.findByProfessorPid(id);
		
		for(Course tempC: coursesForProfessor) {
			tempC.setProfessor(null);//noņem to profesoru, kuru dzēšam ārā
			courseRepo.save(tempC);
		}
		
		profRepo.delete(professorForDelete);
	
	}

	@Override
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
		
	}

	@Override
	public void updateById(int id, String name, String surname, Degree degree) throws Exception {
		if(name == null || surname == null || degree == null)
		{
			throw new Exception("Ievades parametri nav pareizi");
		}
		Professor retrievedProf = retreiveById(id);
		retrievedProf.setName(name);
		retrievedProf.setSurname(surname);
		retrievedProf.setDegree(degree);
		profRepo.save(retrievedProf);
		
		
	}

}