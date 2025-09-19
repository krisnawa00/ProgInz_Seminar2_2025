package lv.venta.model;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "CourseTable") //MYSQL - course_table
@Entity
public class Course {
	@Setter(value = AccessLevel.NONE)
	@Id
	@Column(name = "CId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;
	
	@Column(name = "Title")
	@NotNull
	@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀČŅa-zēūīļķģšāžčņ ]+")
	@Size(min = 4, max = 50)
	private String title;
	
	@Column(name = "Creditpoints")
	@Min(0)
	@Max(30)
	private int creditpoints;
	
	@OneToOne
	@JoinColumn(name = "PId")//izveidos kursa tabulā papildus kolonu PiD, kas sasaistīs profesoru ar kursu
	private Professor professor;
	
	@OneToMany(mappedBy = "course")
	@ToString.Exclude
	private Collection<Grade> grades;
	
	
	
	
	
	public Course(String title, int creditpoints, Professor professor) {
		setTitle(title);
		setCreditpoints(creditpoints);
		setProfessor(professor);
	}
	
}
