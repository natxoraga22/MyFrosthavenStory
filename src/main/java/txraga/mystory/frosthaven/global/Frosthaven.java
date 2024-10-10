package txraga.mystory.frosthaven.global;

import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.model.Building;
import txraga.mystory.frosthaven.model.Event;
import txraga.mystory.frosthaven.model.FhCharacter;
import txraga.mystory.frosthaven.model.PersonalQuest;
import txraga.mystory.frosthaven.model.Scenario;
import txraga.mystory.frosthaven.model.Section;
import txraga.mystory.frosthaven.services.FrosthavenService;


@XSlf4j
@RequiredArgsConstructor
@Component
public class Frosthaven {
	
	private final FrosthavenService fhService;

	private String welcome;
	private Map<String,FhCharacter> characters;
	private Map<String,PersonalQuest> personalQuests;
	private Map<String,Building> buildings;
	private Map<String,Event> events;
	private Map<String,Scenario> scenarios;
	private Map<String,Section> sections;


	@PostConstruct
	private void init() {
		log.entry();
		welcome = fhService.getWelcome();
		characters = fhService.findAllCharacters();
		scenarios = fhService.findAllScenarios();
		events = fhService.findAllEvents(scenarios);
		buildings = fhService.findAllBuildings(scenarios);
		personalQuests = fhService.findAllPersonalQuests(buildings);
		sections = fhService.findAllSections(scenarios);
		log.exit();
	}

	public String getWelcome() {
		log.entry();
		return log.exit(welcome);
	}


	/* ---------------------- */
	/* CHARACTERS & BUILDINGS */
	/* ---------------------- */

	public Map<String,FhCharacter> getAllCharactersAsMap() {
		log.entry();
		return log.exit(characters);
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

	public PersonalQuest getPersonalQuest(String id) {
		log.entry(id);
		PersonalQuest personalQuest = personalQuests.get(id);
		if (personalQuest == null) {
			log.warn("Personal quest '{}' not found", id);
			return log.exit(null);
		}
		else return log.exit(personalQuest);
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


	/* ------ */
	/* EVENTS */
	/* ------ */

	public Map<String,Event> getAllEventsAsMap() {
		log.entry();
		return log.exit(events);
	}


	/* --------- */
	/* SCENARIOS */
	/* --------- */

	public Map<String,Scenario> getAllScenariosAsMap() {
		log.entry();
		return log.exit(scenarios);
	}


	/* -------- */
	/* SECTIONS */
	/* -------- */

	public Map<String,Section> getAllSectionsAsMap() {
		log.entry();
		return log.exit(sections);
	}

}
