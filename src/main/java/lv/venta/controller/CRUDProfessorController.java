
package lv.venta.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lv.venta.model.Professor;

import lv.venta.service.ICRUDProfessorService;

@RestController
@RequestMapping("/crud/professor")
public class CRUDProfessorController {
	
	@Autowired
	private ICRUDProfessorService profService;

	@GetMapping("/all")//localhost:8080/crud/professor/all
	public ResponseEntity<?> getAllProfessorController() {
		try
		{
			ArrayList<Professor> allProfessors = profService.retrieveAll();
			ResponseEntity<ArrayList<Professor>> response = 
				new ResponseEntity<ArrayList<Professor>>(allProfessors, HttpStatusCode.valueOf(200));	
			return response;
		}
		catch (Exception e) {
			ResponseEntity<String> response =
					new ResponseEntity<String>(e.getMessage(), HttpStatusCode.valueOf(500));
			return response;
		}
		
	}
	
	@GetMapping("/all/{id}")//localhost:8080/crud/professor/all/1
	public ResponseEntity<?> getOneProfessorController(@PathVariable(name = "id") int id ) {
		try
		{
			Professor oneProfessor = profService.retreiveById(id);
			ResponseEntity<Professor> response = 
					new ResponseEntity<Professor>(oneProfessor, HttpStatusCode.valueOf(200));
			return response;
		}
		catch (Exception e) {
			ResponseEntity<String> response =
					new ResponseEntity<String>(e.getMessage(), HttpStatusCode.valueOf(500));
			return response;
		}
		
	}
	
		
	@PostMapping("/create")//localhost:8080/crud/professor/create
	public ResponseEntity<?> postCreateNewProfessor(@RequestBody @Valid Professor professor, BindingResult result) {
		if(result.hasErrors()) {
			ResponseEntity<Integer> response =
					new ResponseEntity<Integer>(result.getErrorCount(), HttpStatusCode.valueOf(500));
			return response;
		}
		else
		{
			try
			{
				profService.create(professor.getName(), professor.getSurname(), professor.getDegree());
				ArrayList<Professor> allProfessors = profService.retrieveAll();
				ResponseEntity<ArrayList<Professor>> response = 
					new ResponseEntity<ArrayList<Professor>>(allProfessors, HttpStatusCode.valueOf(200));	
				return response;
				
				
			}
			catch (Exception e) {
				ResponseEntity<String> response =
						new ResponseEntity<String>(e.getMessage(), HttpStatusCode.valueOf(500));
				return response;
			}
		}
	}
	
	
	
	@PutMapping("/update/{id}")//localhost:8080/crud/professor/update/1
	public ResponseEntity<?> putUpdateProfessorById(@RequestBody @Valid Professor professor, BindingResult result, @PathVariable(name = "id") int id) {
		if(result.hasErrors()) {
			ResponseEntity<Integer> response =
					new ResponseEntity<Integer>(result.getErrorCount(), HttpStatusCode.valueOf(500));
			return response;
		}
		else
		{
			try
			{
				profService.updateById(id, professor.getName(), professor.getSurname(), professor.getDegree());
				Professor oneProfessor = profService.retreiveById(id);
				ResponseEntity<Professor> response = 
						new ResponseEntity<Professor>(oneProfessor, HttpStatusCode.valueOf(200));
				return response;
			}
			catch (Exception e) {
				ResponseEntity<String> response =
						new ResponseEntity<String>(e.getMessage(), HttpStatusCode.valueOf(500));
				return response;
			}
		}
	}
	
	
	@DeleteMapping("/delete/{id}")//localhost:8080/crud/professor/delete/1
	public ResponseEntity<?> deleteProfessorById( @PathVariable(name = "id") int id) {
		try
		{
			profService.deleteById(id);
			ArrayList<Professor> allProfessors = profService.retrieveAll();
			ResponseEntity<ArrayList<Professor>> response = 
					new ResponseEntity<ArrayList<Professor>>(allProfessors, HttpStatusCode.valueOf(200));	
				return response;
		}
		catch (Exception e) {
			ResponseEntity<String> response =
					new ResponseEntity<String>(e.getMessage(), HttpStatusCode.valueOf(500));
			return response;
		}
	}
	
}
