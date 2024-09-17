package txraga.mystory.frosthaven.model.raw;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import txraga.mystory.frosthaven.files.utils.IconsDeserializer;
import txraga.mystory.frosthaven.model.Rewards;
import txraga.mystory.frosthaven.model.personal.StoryItem;


@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class RawEvent {
	
	@ToString.Include private String id;
	private TypeAndSeason typeAndSeason;

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<StoryItem> unlockedBy;

	private String text;
	private Map<String,Option> options = Map.of();
	private OutpostAttack outpostAttack;


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
		private String race;
		private int attack;
		private String target;
		private String targetPriority;
		private String text;
		private Rewards rewards;
	}


	/* --------------- */
	/* TYPE AND SEASON */
	/* --------------- */

	public enum TypeAndSeason {
		SO, SR, WO, WR, B;

		@Override
		public String toString() {
			switch(this) {
				case SO: return "Summer Outpost";
				case SR: return "Summer Road";
				case WO: return "Winter Outpost";
				case WR: return "Winter Road";
				case B: return "Boat";
				default: throw new IllegalArgumentException();
			}
		}
	}

}
