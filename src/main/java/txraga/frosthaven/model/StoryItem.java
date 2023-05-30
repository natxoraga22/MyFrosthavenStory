package txraga.frosthaven.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class StoryItem {

	/* Scenario */
	private String scenario;
	private List<String> path;

	/* Event */
	private String event;
	private String option;
	
}
