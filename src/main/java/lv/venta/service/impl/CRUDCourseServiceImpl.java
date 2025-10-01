package lv.venta.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.Course;
import lv.venta.model.Professor;
import lv.venta.model.dto.CourseInfoDTO;
import lv.venta.repo.ICourseRepo;
import lv.venta.repo.IProfessorRepo;
import lv.venta.service.ICRUDCourseService;

@Service
public class CRUDCourseServiceImpl implements ICRUDCourseService{

	//TODO pabeit visas pārējās funkcijas
	@Autowired
	private ICourseRepo courseRepo;
	
	@Autowired
	private IProfessorRepo profRepo;
	
	@Override
	public ArrayList<Course> retrieveAll() throws Exception {
		// TODO Auto-generated method stub
		return (ArrayList<Course>) courseRepo.findAll();
	}

	@Override
	public Course retreiveById(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(int id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(String title, int creditpoints, Professor professor) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(ArrayList<CourseInfoDTO> courses) throws Exception {
		for(CourseInfoDTO tempC : courses) {
			if(profRepo.existsById(tempC.getProfid())) {
				Professor prof = profRepo.findById(tempC.getProfid()).get();
				Course course = new Course(tempC.getTitle(), tempC.getCreditpoints(), prof);
				courseRepo.save(course);
				
			}
		}
		
		
	}

	@Override
	public void updateById(int id, String title, int creditpoints, Professor professor) throws Exception {
		// TODO Auto-generated method stub
		
	}

}