package lv.venta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lv.venta.model.enums.Degree;
import jakarta.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "ProfessorTable") //MYSQL - professor_table
@Entity
public class Professor {
	@Setter(value = AccessLevel.NONE)
	@Id
	@Column(name = "PId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int pid;
	
	@NotNull
	@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀČŅ]{1}[a-zēūīļķģšāžčņ]+")
	@Size(min = 3, max = 20)
	@Column(name = "Name")
	private String name;
	
	@NotNull
	@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀČŅ]{1}[a-zēūīļķģšāžčņ]+")
	@Size(min = 3, max = 25)
	@Column(name = "Surname")
	private String surname;
	
	
	@NotNull
	@Column(name = "Degree")
	private Degree degree;
	
	
	@OneToOne(mappedBy = "professor")//nodrosinām sasaisti JAVA līmenī - otras klases mainīgā nosaukums
	@ToString.Exclude
	private Course course;
	
	
	
	
	
	
	public Professor(String name, String surname, Degree degree) {
		setName(name);
		setSurname(surname);
		setDegree(degree);
	}
	
	
	
}
