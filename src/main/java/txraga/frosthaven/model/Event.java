package txraga.frosthaven.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Event extends StoryObject {
	
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

	private String id;
	private Season season;
	private Type type;
	private StoryItem unlockedBy;
	private String text;
	private Map<String,Option> options;
	private String chosenOption;
	
	
	@Override
	public StoryObject.Type getObjectType() {
		return StoryObject.Type.EVENT;
	}

	public void replaceIcons() {
		if (options != null) {
			for (Option option : options.values()) option.replaceIcons();
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