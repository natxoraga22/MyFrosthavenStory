package txraga.frosthaven.model.utils;

import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.XSlf4j;
import txraga.frosthaven.controllers.CampaignUtils;
import txraga.frosthaven.model.Event;


@XSlf4j
@Component
public class Events {
	
	private Map<String,Event> events;


	@PostConstruct
	private void init() {
		log.entry();
		events = CampaignUtils.getEvents();
		log.exit();
	}

	public Event get(String id) {
		log.entry(id);
		Event event = events.get(id);
		if (event == null) log.warn("Event '{}' not found", id);
		return log.exit(event);
	}

}
