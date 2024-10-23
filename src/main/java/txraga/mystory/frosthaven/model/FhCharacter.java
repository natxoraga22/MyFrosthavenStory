package txraga.mystory.frosthaven.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class FhCharacter {
	
	private String id;
	private String name;
	private String race;
	private String background;
	private Section retirement;


	public String getFullName() {
		return race + " " + name;
	}

}
