package txraga.frosthaven.model;

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

	private String personalQuest;
	private int level;


	public FhCharacter(FhCharacter other) {
		this.id = other.id;
		this.name = other.name;
		this.race = other.race;
		this.background = other.background;
		this.retirement = other.retirement;
		this.personalQuest = other.personalQuest;
		this.level = other.level;
	}

	public void populate() {
		// Populate retirement section
		if (retirement != null) retirement.populate(null, false);
	}

}
