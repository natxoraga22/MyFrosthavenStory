package txraga.mystory.frosthaven.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private String number;
	private String name;
	private Map<String,Level> levels = Map.of();

	private int level;
	private List<String> path = List.of();


	public Building(Building other) {
		this.id = other.id;
		this.number = other.number;
		this.name = other.name;
		this.levels = new HashMap<>(other.levels);
		this.level = other.level;
		this.path = new ArrayList<>(other.path);
	}


	/* ------ */
	/* LEVEL */
	/* ------ */

	@Getter
	@Setter
	@ToString
	public static class Level {
		private int number;
		private Map<String,Section> builtSections = Map.of();
	}

}
