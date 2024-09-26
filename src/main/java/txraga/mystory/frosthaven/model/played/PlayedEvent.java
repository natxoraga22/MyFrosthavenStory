package txraga.mystory.frosthaven.model.played;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import txraga.mystory.frosthaven.model.Section;
import txraga.mystory.frosthaven.model.UnlockedBy;
import txraga.mystory.frosthaven.model.raw.RawEvent;


@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class PlayedEvent /*extends StoryObject*/ {
	
	@ToString.Include private String id;
	private RawEvent.TypeAndSeason typeAndSeason;
	private List<UnlockedBy> unlockedBy;

	private String text;
	private List<RawEvent.Option> chosenOptions;
	private RawEvent.OutpostAttack outpostAttack;

	// Some events unlock a random side scenario (meaning a section is read)
	private Section randomScenarioSection;
	
	
	public PlayedEvent(RawEvent rawEvent) {
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
		if (outpostAttack == null || chosenOptions == null) return true;
		for (RawEvent.Option chosenOption : chosenOptions) {
			if (chosenOption != null && chosenOption.isSkipAttack()) return true;
		}
		return false;
	}

}
