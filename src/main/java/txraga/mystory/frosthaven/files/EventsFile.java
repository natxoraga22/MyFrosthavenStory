package txraga.mystory.frosthaven.files;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.model.raw.RawEvent;


@XSlf4j
@Component
public class EventsFile {
	
	private final static String EVENTS_FOLDER_PATH = "static/json/events";


	public Map<String,RawEvent> findAllEventsAsMap() {
		log.entry();
		Map<String,RawEvent> events = new HashMap<>();
		for (RawEvent.TypeAndSeason typeAndSeason : RawEvent.TypeAndSeason.values()) {
			events.putAll(findEventsByTypeAndSeasonAsMap(typeAndSeason));
		}
		return log.exit(events);
	}

	private Map<String,RawEvent> findEventsByTypeAndSeasonAsMap(RawEvent.TypeAndSeason typeAndSeason) {
		log.entry(typeAndSeason);
		String filePath = EVENTS_FOLDER_PATH + "/" + typeAndSeason.name() + ".json";
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			InputStream eventsInputStream = new ClassPathResource(filePath).getInputStream();
			Map<String,RawEvent> events = objectMapper.readValue(eventsInputStream, new TypeReference<Map<String,RawEvent>>(){});

			events.forEach((id, event) -> {
				// Set event id, type and season
				event.setId(id);
				event.setTypeAndSeason(typeAndSeason);
				// Set options ids
				event.getOptions().forEach((optionId, option) -> {
					if (option.getId() == null) option.setId(optionId);
				});
			});
			return log.exit(events);
		}
		catch (IOException e) {
			log.warn("Error reading and parsing file '" + filePath + "'", e);
			return log.exit(Map.of());
		}
	}

}
