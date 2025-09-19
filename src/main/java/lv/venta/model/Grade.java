package lv.venta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "GradeTable") //MYSQL - grade_table
@Entity
public class Grade {
	@Setter(value = AccessLevel.NONE)
	@Id
	@Column(name = "GId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int gid;
	
	@Column(name = "Grvalue")
	@Min(1)
	@Max(10)
	private int grvalue;
	
	@ManyToOne
	@JoinColumn(name = "Stid")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "CId")
	private Course course;
	
	
	public Grade(int grvalue, Student student, Course course) {
		setGrvalue(grvalue);
		setStudent(student);
		setCourse(course);
	}
	
}
