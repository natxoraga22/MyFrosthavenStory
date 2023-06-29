package txraga.frosthaven.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class StoryItem {

	private Event event;
	private Scenario scenario;
	private OutpostPhase outpostPhase;

}
