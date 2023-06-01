package txraga.frosthaven.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class OutpostPhase extends StoryObject {

	private int id;
	private Event event;
	private List<FhCharacter> levelUp;
	private List<Building> build;
	private List<Building> upgrade;


	@Override
	public StoryObject.Type getObjectType() {
		return StoryObject.Type.OUTPOST;
	}
	
}
