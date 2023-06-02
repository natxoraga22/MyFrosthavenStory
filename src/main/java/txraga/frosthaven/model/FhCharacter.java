package txraga.frosthaven.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class FhCharacter {
	
	private String name;
	private String background;
	private int level;
	private String personalQuest;

	public String getNameId() {
		return name.toLowerCase().replace(" ", "");
	}

}
