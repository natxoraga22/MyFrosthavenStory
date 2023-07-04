package txraga.frosthaven.model.personal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import txraga.frosthaven.model.Event;
import txraga.frosthaven.model.OutpostPhase;
import txraga.frosthaven.model.Scenario;
import txraga.frosthaven.model.Section;


@Getter
@Setter
@ToString
public class StoryItem {

	private Event event;
	private Scenario scenario;
	private OutpostPhase outpostPhase;

	private Section section;

}
