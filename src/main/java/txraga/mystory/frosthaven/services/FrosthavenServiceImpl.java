package txraga.mystory.frosthaven.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.files.BuildingsFile;
import txraga.mystory.frosthaven.files.CharactersFile;
import txraga.mystory.frosthaven.files.EventsFile;
import txraga.mystory.frosthaven.files.PersonalQuestsFile;
import txraga.mystory.frosthaven.files.ScenariosFile;
import txraga.mystory.frosthaven.files.SectionsFile;
import txraga.mystory.frosthaven.files.WelcomeFile;
import txraga.mystory.frosthaven.model.Building;
import txraga.mystory.frosthaven.model.Event;
import txraga.mystory.frosthaven.model.FhCharacter;
import txraga.mystory.frosthaven.model.PersonalQuest;
import txraga.mystory.frosthaven.model.Scenario;
import txraga.mystory.frosthaven.model.Section;


@XSlf4j
@RequiredArgsConstructor
@Service
public class FrosthavenServiceImpl implements FrosthavenService {

	private final WelcomeFile welcomeFile;
	private final CharactersFile charactersFile;
	private final PersonalQuestsFile personalQuestsFile;
	private final EventsFile eventsFile;
	private final ScenariosFile scenariosFile;
	private final BuildingsFile buildingsFile;
	private final SectionsFile sectionsFile;


	@Override
	public String getWelcome() {
		log.entry();
		return log.exit(welcomeFile.getWelcome());
	}

	@Override
	public Map<String,FhCharacter> findAllCharacters() {
		log.entry();
		return log.exit(charactersFile.findAllCharactersAsMap());
	}

	@Override
	public Map<String,PersonalQuest> findAllPersonalQuests() {
		log.entry();
		return log.exit(personalQuestsFile.findAllPersonalQuestsAsMap());
	}

	@Override
	public Map<String,Event> findAllEvents() {
		log.entry();
		return log.exit(eventsFile.findAllEventsAsMap());
	}

	@Override
	public Map<String,Scenario> findAllScenarios() {
		log.entry();
		return log.exit(scenariosFile.findAllScenariosAsMap());
	}

	@Override
	public Map<String,Building> findAllBuildings() {
		log.entry();
		return log.exit(buildingsFile.findAllBuildingsAsMap());
	}

	@Override
	public Map<String,Section> findAllSections() {
		log.entry();
		return log.exit(sectionsFile.findAllSectionsAsMap());
	}
	
}
