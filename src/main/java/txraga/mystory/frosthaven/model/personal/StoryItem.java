package txraga.mystory.frosthaven.model.personal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import txraga.mystory.frosthaven.model.Event;
import txraga.mystory.frosthaven.model.OutpostPhase;
import txraga.mystory.frosthaven.model.Scenario;
import txraga.mystory.frosthaven.model.Section;


@Getter
@Setter
@ToString
public class StoryItem {

	private Event event;
	private Scenario scenario;
	private OutpostPhase outpostPhase;

	private Section section;

	private String other;

}
