package txraga.mystory.frosthaven.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import txraga.mystory.frosthaven.model.played.PlayedEvent;


@Getter
@Setter
@ToString
public class OutpostPhase extends StoryObject {

	private int id;
	private List<Section> townGuardPerks;
	private List<Section> passageOfTime;
	private PlayedEvent outpostEvent;

	// Downtime
	private List<FhCharacter> levelUps;
	private List<FhCharacter> retirements;
	private List<FhCharacter> newMembers;

	// Construction
	private List<Building> build;
	private List<Building> upgrade;


	@Override
	public StoryObject.Type getObjectType() {
		return StoryObject.Type.OUTPOST_PHASE;
	}
	
}
