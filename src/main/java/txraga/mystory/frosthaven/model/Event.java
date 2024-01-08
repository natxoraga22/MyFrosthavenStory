package txraga.mystory.frosthaven.model;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import txraga.mystory.frosthaven.files.utils.IconsDeserializer;
import txraga.mystory.frosthaven.model.personal.StoryItem;


@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class Event extends StoryObject {
	
	@ToString.Include private String id;
	private Type type;
	private Season season;

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<StoryItem> unlockedBy;

	private String text;
	private Map<String,Option> options = Map.of();
	private OutpostAttack outpostAttack;
	
	@JsonAlias("chosenOption")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	@ToString.Include private List<String> chosenOptions;

	// Some events unlock a random side scenario (meaning a section is read)
	private Section section;
	
	
	@Override
	public StoryObject.Type getObjectType() {
		return StoryObject.Type.EVENT;
	}

	public boolean isSkipAttack() {
		if (outpostAttack == null) return true;
		for (String chosenOptionId : chosenOptions) {
			Option chosenOption = options.get(chosenOptionId);
			if (chosenOption != null && chosenOption.isSkipAttack()) return true;
		}
		return false;
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
	}


	/* ------ */
	/* OPTION */
	/* ------ */

	@Getter
	@Setter
	@ToString
	public static class Option {
		private String id;

		@JsonDeserialize(using = IconsDeserializer.class)
		private String trigger;

		@JsonDeserialize(using = IconsDeserializer.class)
		private String requirement;

		private String text;
		private Rewards rewards;
		private boolean skipAttack;
	}


	/* -------------- */
	/* OUTPOST ATTACK */
	/* -------------- */

	@Getter
	@Setter
	@ToString
	public static class OutpostAttack {
		private int attack;
		private int target;
		private String targetPriority;
		private String text;
		private Rewards rewards;
	}


	/* --------------- */
	/* TYPE AND SEASON */
	/* --------------- */

	public enum Type { 
		O, R, B;
	
		public String toLowerCase() {
			return this.toString().toLowerCase();
		}

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

		public String toLowerCase() {
			return this.toString().toLowerCase();
		}

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