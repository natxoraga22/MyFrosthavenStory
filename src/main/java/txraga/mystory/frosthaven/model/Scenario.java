package txraga.mystory.frosthaven.model;

import java.util.List;
import java.util.Map;

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
public class Scenario extends StoryObject {
	
	@ToString.Include private String id;
	@ToString.Include private String name;
	private QuestLine questLine;
	private String coordinates;
	private Requirement requirement;

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<StoryItem> unlockedBy;

	private int complexity;
	private String location;

	private boolean linked = false;
	private boolean forceLinked = false;

	@JsonDeserialize(using = IconsDeserializer.class)
	private String goals;

	@JsonDeserialize(using = IconsDeserializer.class)
	private String effects;

	private Map<String,Section> sections = Map.of();
	private List<String> path;

	// Some chests unlock a random side scenario (meaning a section is read)
	private Section randomScenarioSection;


	@Override
	public StoryObject.Type getObjectType() {
		return StoryObject.Type.SCENARIO;
	}


	/* --------- */
	/* QUESTLINE */
	/* --------- */

	public enum QuestLine {
		Introduction,
		AlgoxSnowspeaker,
		AlgoxIcespeaker,
		Algox,
		Unfettered,
		Lurker,
		PuzzleBook,
		PersonalQuest,
		JobPosting,
		RandomScenario,
		Other;

		@Override
		public String toString() {
			switch(this) {
				case AlgoxSnowspeaker: return "Algox Snowspeaker";
				case AlgoxIcespeaker: return "Algox Icespeaker";
				case PuzzleBook: return "Puzzle Book";
				case PersonalQuest: return "Personal Quest";
				case JobPosting: return "Job Posting";
				case RandomScenario: return "Random Scenario";
				default: return this.name();
			}
		}
	}

	
	/* ----------- */
	/* REQUIREMENT */
	/* ----------- */

	@Getter
	@Setter
	@ToString
	public static class Requirement {
		private String transport;
		private String status;
	}

}
