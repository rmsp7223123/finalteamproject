package cloudWeb.visual;

import java.sql.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberVO {
	private String gender, age;
	private int population;
}
