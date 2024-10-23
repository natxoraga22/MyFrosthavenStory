package txraga.mystory.frosthaven.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import txraga.mystory.frosthaven.files.utils.IconsDeserializer;


@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class Scenario implements Comparable<Scenario> {
	
	@ToString.Include private String id;
	@ToString.Include private String name;
	private QuestLine questLine;
	private String coordinates;
	private Requirement requirement;

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<UnlockedBy> unlockedBy;

	private int complexity;
	private String location;

	@JsonDeserialize(using = IconsDeserializer.class)
	private String goals;

	@JsonDeserialize(using = IconsDeserializer.class)
	private String effects;

	private Map<String,Section> sections = Map.of();
	private List<String> path = List.of();


	@Override
	public int compareTo(Scenario other) {
		return this.getId().compareTo(other.getId());
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

		public boolean isMainQuest() {
			return List.of(Introduction, AlgoxSnowspeaker, AlgoxIcespeaker, Algox, Unfettered, Lurker).contains(this);
		}
	}

	
	/* ----------- */
	/* REQUIREMENT */
	/* ----------- */

	@NoArgsConstructor
	@Getter
	@Setter
	@ToString
	public static class Requirement {
		private String transport;
		private String status;
	}

}
