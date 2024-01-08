package txraga.mystory.frosthaven.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import txraga.mystory.frosthaven.files.utils.IconsDeserializer;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class Rewards {
	
	@JsonDeserialize(using = IconsDeserializer.class)
	private String text;

	private Map<String,List<Event>> events;
	private Map<String,List<Event>> removedEvents;
	private List<Scenario> scenarios;
	private List<Scenario> lockedOutScenarios;

}
