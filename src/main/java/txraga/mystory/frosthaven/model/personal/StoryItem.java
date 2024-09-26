package txraga.mystory.frosthaven.model.personal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import txraga.mystory.frosthaven.model.OutpostPhase;
import txraga.mystory.frosthaven.model.Scenario;
import txraga.mystory.frosthaven.model.Section;
import txraga.mystory.frosthaven.model.played.PlayedEvent;


@Getter
@Setter
@ToString
public class StoryItem {

	private PlayedEvent event;
	private Scenario scenario;
	private OutpostPhase outpostPhase;

	private Section section;

	private String other;

}
