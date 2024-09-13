package txraga.mystory.frosthaven.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import txraga.mystory.frosthaven.model.personal.StoryItem;
import txraga.mystory.frosthaven.model.raw.RawEvent;


@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class Event /*extends StoryObject*/ {
	
	@ToString.Include private String id;
	private RawEvent.TypeAndSeason typeAndSeason;
	private List<StoryItem> unlockedBy;

	private String text;
	private List<RawEvent.Option> chosenOptions;
	private RawEvent.OutpostAttack outpostAttack;

	// Some events unlock a random side scenario (meaning a section is read)
	private Section randomScenarioSection;
	
	
	public Event(RawEvent rawEvent) {
		this.id = rawEvent.getId();
		this.typeAndSeason = rawEvent.getTypeAndSeason();
		this.unlockedBy = rawEvent.getUnlockedBy();
		this.text = rawEvent.getText();
		this.outpostAttack = rawEvent.getOutpostAttack();
	}

	/*
	@Override
	public StoryObject.Type getObjectType() {
		return StoryObject.Type.EVENT;
	}
	*/

	public boolean isSkipAttack() {
		if (outpostAttack == null) return true;
		for (RawEvent.Option chosenOption : chosenOptions) {
			if (chosenOption != null && chosenOption.isSkipAttack()) return true;
		}
		return false;
	}

}
