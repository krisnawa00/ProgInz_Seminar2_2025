package lv.venta.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import lv.venta.model.Grade;

public interface IGradeRepo extends CrudRepository<Grade, Integer> {

	public abstract ArrayList<Grade> findByStudentStid(int id);

	
	@Query(nativeQuery = true, value = "SELECT avg(grvalue) FROM grade_table WHERE cid = ?1;")
	public abstract float calculateAVGgradeInCourse(int id);

}
