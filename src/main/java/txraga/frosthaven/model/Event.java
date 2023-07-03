package txraga.frosthaven.model;

import java.util.Map;
import java.util.Map.Entry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Event extends StoryObject {
	
	private String id;
	private Type type;
	private Season season;
	private StoryItem unlockedBy;
	private String text;
	private Map<String,Option> options = Map.of();
	
	private String chosenOption;

	// Some events unlock a random side scenario (meaning a section is read)
	private Section section;
	
	
	@Override
	public StoryObject.Type getObjectType() {
		return StoryObject.Type.EVENT;
	}

	public void populate(String id, Type type, Season season) {
		// Set event id, type and season
		if (this.id == null) this.id = id;
		this.type = type;
		this.season = type == Type.B ? null : season;

		// Set option ids
		for (Entry<String,Option> optionEntry : options.entrySet()) {
			Option option = optionEntry.getValue();
			if (option.getId() == null) option.setId(optionEntry.getKey());
		}
		replaceIcons();
	}

	public void replaceIcons() {
		if (options != null) {
			for (Option option : options.values()) option.replaceIcons();
		}
	}


	/* ------ */
	/* OPTION */
	/* ------ */

	@Getter
	@Setter
	@ToString
	public static class Option {
		private String id;
		private String trigger;
		private String requirement;
		private String text;
		private String rewards;

		public void replaceIcons() {
			this.requirement = ModelUtils.replaceIcons(this.requirement);
			this.rewards = ModelUtils.replaceIcons(this.rewards);
		}
	}


	/* --------------- */
	/* TYPE AND SEASON */
	/* --------------- */

	public enum Type { 
		O, R, B;
	
		@Override
		public String toString() {
			switch(this) {
				case O: return "Outpost";
				case R: return "Road";
				case B: return "Boat";
				default: throw new IllegalArgumentException();
			}
		}
	}

	public enum Season { 
		S, W;

		@Override
		public String toString() {
			switch(this) {
				case S: return "Summer";
				case W: return "Winter";
				default: throw new IllegalArgumentException();
			}
		}
	}

}