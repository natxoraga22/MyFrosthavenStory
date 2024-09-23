package txraga.mystory.frosthaven.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import txraga.mystory.frosthaven.files.utils.IconsDeserializer;
import txraga.mystory.frosthaven.model.raw.RawEvent;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class Rewards {
	
	@JsonDeserialize(using = IconsDeserializer.class)
	private String text;

	private Map<RawEvent.TypeAndSeason,List<Rewards.Event>> events;
	private Map<RawEvent.TypeAndSeason,List<Rewards.Event>> removedEvents;
	private List<Rewards.Scenario> scenarios;
	private List<Rewards.Scenario> lockedOutScenarios;


	@Getter
	@Setter
	@ToString
	public static class Event {
		private String id;
	}

	@Getter
	@Setter
	@ToString
	public static class Scenario {
		private String id;
		private String name;
		private boolean linked = false;
		private boolean forceLinked = false;
	}

}
