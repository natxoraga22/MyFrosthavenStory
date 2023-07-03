package txraga.frosthaven;

import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.XSlf4j;
import txraga.frosthaven.model.Building;
import txraga.frosthaven.model.Event;
import txraga.frosthaven.model.FhCharacter;
import txraga.frosthaven.model.Section;


@XSlf4j
@Component
public class Frosthaven {
	
	private Map<String,Section> sections;
	private Map<String,FhCharacter> characters;
	private Map<String,Event> events;
	private Map<String,Building> buildings;


	@PostConstruct
	private void init() {
		log.entry();
		sections = FrosthavenFiles.getSections();
		characters = FrosthavenFiles.getCharacters();
		events = FrosthavenFiles.getEvents();
		buildings = FrosthavenFiles.getBuildings();
		log.exit();
	}

	public Section getSection(String id) {
		log.entry(id);
		Section section = sections.get(id);
		if (section == null) log.warn("Section '{}' not found", id);
		return log.exit(section);
	}

	public FhCharacter getCharacter(String id) {
		log.entry(id);
		FhCharacter character = characters.get(id);
		if (character == null) {
			log.warn("Character '{}' not found", id);
			return log.exit(null);
		}
		else return log.exit(new FhCharacter(character));
	}

	public Event getEvent(String id) {
		log.entry(id);
		Event event = events.get(id);
		if (event == null) log.warn("Event '{}' not found", id);
		return log.exit(event);
	}

	public Building getBuilding(String id) {
		log.entry(id);
		Building building = buildings.get(id);
		if (building == null) {
			log.warn("Building '{}' not found", id);
			return log.exit(null);
		}
		else return log.exit(new Building(building));
	}

}
