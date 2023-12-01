package txraga.mystory.frosthaven.files;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.model.Event;


@XSlf4j
@Component
public class EventsFile {
	
	private final static String EVENTS_FOLDER_PATH = "static/json/events";


	public Map<String,Event> findAllEventsAsMap() {
		log.entry();
		Map<String,Event> events = new HashMap<>();
		for (Event.Type type : Event.Type.values()) {
			if (type == Event.Type.B) events.putAll(findEventsByTypeAndSeasonAsMap(type, null));
			else {
				for (Event.Season season : Event.Season.values()) {
					events.putAll(findEventsByTypeAndSeasonAsMap(type, season));
				}
			}
		}
		return log.exit(events);
	}

	public Map<String,Event> findEventsByTypeAndSeasonAsMap(Event.Type type, Event.Season season) {
		log.entry(type, season);
		String seasonAndTypeString = type == Event.Type.B ? type.name() : season.name() + type.name();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			InputStream eventsInputStream = new ClassPathResource(EVENTS_FOLDER_PATH + "/" + seasonAndTypeString + ".json").getInputStream();
			Map<String,Event> events = objectMapper.readValue(eventsInputStream, new TypeReference<Map<String,Event>>(){});

			// Populate events with additional info
			for (Entry<String,Event> eventEntry : events.entrySet()) {
				Event event = eventEntry.getValue();
				event.populate(eventEntry.getKey(), type, season);
			}
			return log.exit(events);
		}
		catch (IOException e) {
			log.warn("Error reading and parsing file '" + EVENTS_FOLDER_PATH + "/" + seasonAndTypeString + ".json'");
			return log.exit(Map.of());
		}
	}

}
