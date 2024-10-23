package txraga.mystory.frosthaven.model;

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


	/* ----- */
	/* LEVEL */
	/* ----- */

	@NoArgsConstructor
	@Getter
	@Setter
	@ToString
	public static class Level {
		private int number;
		private Map<String,Section> builtSections = Map.of();
	}

}
