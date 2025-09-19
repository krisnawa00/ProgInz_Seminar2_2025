package lv.venta.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import jakarta.validation.Valid;
import lv.venta.model.Professor;

import lv.venta.service.ICRUDProfessorService;

@Controller
@RequestMapping("/crud/professor")
public class CRUDProfessorController {
	
	@Autowired
	private ICRUDProfessorService profService;

	@GetMapping("/all")//localhost:8080/crud/professor/all
	public String getAllProfessorController(Model model) {
		try
		{
			ArrayList<Professor> allProfessors = profService.retrieveAll();
			model.addAttribute("professors", allProfessors);
			return "show-all-professors-page";//visi profesoru dati tiks ievietoti show-all-professors-pga.html
		}
		catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error-page";
		}
		
	}
	
	@GetMapping("/all/{id}")//localhost:8080/crud/professor/all/1
	public String getOneProfessorController(Model model, @PathVariable(name = "id") int id ) {
		try
		{
			Professor oneProfessor = profService.retreiveById(id);
			model.addAttribute("package", oneProfessor);
			return "show-one-professor-page";//parādīs show-one-professor-page.html
		}
		catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error-page";
		}
		
	}
	
	@GetMapping("/create")//localhost:8080/crud/professor/create
	public String getCreateNewProfessor(Model model) {
		Professor emptyProfessor = new Professor();
		model.addAttribute("professor", emptyProfessor);
		return "create-new-professor";//atvērsies create-new-professor.html ar neaizpildītu profesoru
	}
	
	@PostMapping("/create")
	public String postCreateNewProfessor(@Valid Professor professor, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "create-new-professor";
		}
		else
		{
			try
			{
				profService.create(professor.getName(), professor.getSurname(), professor.getDegree());
				return "redirect:/crud/professor/all";
			}
			catch (Exception e) {
				model.addAttribute("package", e.getMessage());
				return "show-error-page";
			}
		}
	}
	
	
	@GetMapping("/update/{id}")
	public String getUpdateProfessorById(@PathVariable(name="id") int id, Model model) {
		try
		{
			Professor profForUpdating = profService.retreiveById(id);
			model.addAttribute("professor", profForUpdating);
			return "update-professor";
		}
		catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error-page";
		}
		
	}
	@PostMapping("/update/{id}")
	public String postUpdateProfessorById(@Valid Professor professor, BindingResult result,
			Model model, @PathVariable(name = "id") int id) {
		if(result.hasErrors()) {
			return "update-professor";
		}
		else
		{
			try
			{
				profService.updateById(id, professor.getName(), professor.getSurname(), professor.getDegree());
				return "redirect:/crud/professor/all/" + id;
			}
			catch (Exception e) {
				model.addAttribute("package", e.getMessage());
				return "show-error-page";
			}
		}
	}
	
	
	@GetMapping("/delete/{id}")
	public String getDeleteProfessorById( @PathVariable(name = "id") int id, Model model) {
		try
		{
			profService.deleteById(id);
			ArrayList<Professor> allProfessors = profService.retrieveAll();
			model.addAttribute("professors", allProfessors);
			return "show-all-professors-page";
		}
		catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "show-error-page";
		}
	}
	
}
