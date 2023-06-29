package txraga.frosthaven.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class StoryItem {

	private Scenario scenario;
	//private OutpostPhase outpostPhase;
	//private Event roadEvent;


	/** OLD WAY */
	/*
	/* Scenario /
	private String scenario;
	private List<String> path;

	/* Event /
	private String event;
	private String option;

	/* Outpost phase /
	private boolean outpostPhase;
	private List<String> passageOfTime;
	private List<FhCharacter> levelUp;
	private List<Building> build;
	private List<Building> upgrade;

	/* Section /
	private String section;
	*/

}
