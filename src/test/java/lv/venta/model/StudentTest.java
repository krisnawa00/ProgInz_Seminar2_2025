package lv.venta.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StudentTest {

	Student testStudent1 = new Student("Jānis", "Bērziņš");//dati ok
	Student testStudent2 = new Student("23652764", "A");//nepareiza dati
	Student testStudent3 = new Student(null, null);//bezreferenču dati
	
	
	@Test
	void testGoodStudent() {
		assertEquals("Jānis", testStudent1.getName());
		assertEquals("Bērziņš", testStudent1.getSurname());
		
	}

	@Test
	void testBadStudent()
	{
		assertNull(testStudent2.getName());
		assertNull(testStudent2.getSurname());
	}
	
	@Test
	void testNullStudent() {
		assertEquals(null, testStudent3.getName());
		assertEquals(null, testStudent3.getSurname());
	}
}