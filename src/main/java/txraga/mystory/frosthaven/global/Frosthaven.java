package txraga.mystory.frosthaven.global;

import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
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
@Getter
@Component
public class Frosthaven {
	
	private final FrosthavenService fhService;

	private String welcome;
	private Map<String,FhCharacter> characters;
	private Map<String,PersonalQuest> personalQuests;
	private Map<String,Scenario> scenarios;
	private Map<String,Event> events;
	private Map<String,Section> sections;
	private Map<String,Building> buildings;


	@PostConstruct
	private void init() {
		log.entry();
		welcome = fhService.getWelcome();
		characters = fhService.findAllCharacters();
		scenarios = fhService.findAllScenarios();
		events = fhService.findAllEvents(scenarios);
		sections = fhService.findAllSections(scenarios);
		buildings = fhService.findAllBuildings(scenarios);
		personalQuests = fhService.findAllPersonalQuests(buildings);
		log.exit();
	}

}
