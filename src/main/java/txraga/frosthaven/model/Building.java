package txraga.frosthaven.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class Building {
	
	private String id;
	private int number;
	private String name;
	private Map<String,Level> levels = Map.of();

	private int level;


	public Building(Building other) {
		this.id = other.id;
		this.number = other.number;
		this.name = other.name;
		this.levels = new HashMap<>(other.levels);
		this.level = other.level;
	}

	public void populate(String id) {
		// Set building id
		if (this.id == null) this.id = id;

		for (Entry<String,Level> levelEntry : levels.entrySet()) {
			Level level = levelEntry.getValue();
			// Set level number
			if (level.getNumber() == 0) level.setNumber(Integer.parseInt(levelEntry.getKey()));
			// Populate builtSection
			if (level.getBuiltSection() != null) level.getBuiltSection().populate(null, false);
		}
	}


	/* ------ */
	/* LEVEL */
	/* ------ */

	@Getter
	@Setter
	@ToString
	public static class Level {
		private int number;
		private Section builtSection;
	}

}
