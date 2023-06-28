package txraga.frosthaven.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class FhCharacter {
	
	private String id;
	private String name;
	private String race;
	private String background;
	private String retirement;

	//private int level;
	private String personalQuest;

	
	public void fillData(FhCharacter character) {
		this.name = character.name;
		this.race = character.race;
		this.background = character.background;
	}

}
