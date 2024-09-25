package txraga.mystory.frosthaven.global;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.model.Building;
import txraga.mystory.frosthaven.model.FhCharacter;
import txraga.mystory.frosthaven.model.PersonalQuest;
import txraga.mystory.frosthaven.model.Scenario;
import txraga.mystory.frosthaven.model.Section;
import txraga.mystory.frosthaven.model.played.PlayedEvent;
import txraga.mystory.frosthaven.model.raw.RawEvent;
import txraga.mystory.frosthaven.services.FrosthavenService;


@XSlf4j
@RequiredArgsConstructor
@Component
public class Frosthaven {
	
	private final FrosthavenService fhService;

	private String welcome;
	private Map<String,FhCharacter> characters;
	private Map<String,PersonalQuest> personalQuests;
	private Map<String,RawEvent> events;
	private Map<String,Scenario> scenarios;
	private Map<String,Building> buildings;
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


	/* ------ */
	/* EVENTS */
	/* ------ */

	public List<String> getAllRawEventsIds() {
		log.entry();
		return log.exit(events.keySet().stream().sorted().toList());
	}

	public Map<String,RawEvent> getAllRawEventsAsMap() {
		log.entry();
		return log.exit(events);
	}

	public RawEvent getRawEvent(String eventId) {
		log.entry(eventId);
		RawEvent event = events.get(eventId);
		if (event == null) {
			log.warn("Event '{}' not found", eventId);
			return log.exit(null);
		}
		else return log.exit(event);
	}

	public PlayedEvent getPlayedEvent(String eventId, List<String> chosenOptionsIds, String randomScenarioSectionId) {
		log.entry(eventId, chosenOptionsIds, randomScenarioSectionId);
		RawEvent rawEvent = getRawEvent(eventId);
		if (rawEvent != null) {
			PlayedEvent playedEvent = new PlayedEvent(rawEvent);
			// Set chosen options
			if (chosenOptionsIds != null && !chosenOptionsIds.isEmpty()) {
				playedEvent.setChosenOptions(chosenOptionsIds.stream().map(chosenOptionId -> rawEvent.getOptions().get(chosenOptionId)).toList());
			}
			// Set randomScenarioSection
			if (randomScenarioSectionId != null) {
				Section section = getSection(randomScenarioSectionId);
				if (section != null) playedEvent.setRandomScenarioSection(section);
			}
			return log.exit(playedEvent);
		}
		else return log.exit(null);
	}



	public Scenario getScenario(String id) {
		log.entry(id);
		Scenario scenario = scenarios.get(id);
		if (scenario == null) {
			log.warn("Scenario '{}' not found", id);
			return log.exit(null);
		}
		else return log.exit(scenario);
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

	public Section getSection(String id) {
		log.entry(id);
		Section section = sections.get(id);
		if (section == null) {
			log.warn("Section '{}' not found", id);
			return log.exit(null);
		}
		else return log.exit(section);
	}

}
