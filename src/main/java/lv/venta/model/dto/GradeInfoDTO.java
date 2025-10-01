package lv.venta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GradeInfoDTO {
	private int grvalue;
	private String studname;
	private String studsurname;
	private String coursetitle;

}