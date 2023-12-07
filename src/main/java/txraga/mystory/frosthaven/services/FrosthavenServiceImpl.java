package txraga.mystory.frosthaven.services;

import java.util.List;
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
import txraga.mystory.frosthaven.model.Rewards;
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
	public Map<String,Event> findAllEvents(Map<String,Scenario> scenarios) {
		log.entry();
		Map<String,Event> events = eventsFile.findAllEventsAsMap();
		for (Event event : events.values()) {
			for (Event.Option option : event.getOptions().values()) {
				populateRewards(option.getRewards(), scenarios);
			}
		}
		return log.exit(events);
	}

	@Override
	public Map<String,Scenario> findAllScenarios() {
		log.entry();
		Map<String,Scenario> scenarios = scenariosFile.findAllScenariosAsMap();
		for (Scenario scenario : scenarios.values()) {
			for (Section section : scenario.getSections().values()) {
				populateRewards(section.getRewards(), scenarios);
			}
		}
		return log.exit(scenarios);
	}

	@Override
	public Map<String,Building> findAllBuildings() {
		log.entry();
		return log.exit(buildingsFile.findAllBuildingsAsMap());
	}

	@Override
	public Map<String,Section> findAllSections(Map<String,Scenario> scenarios) {
		log.entry();
		Map<String,Section> sections = sectionsFile.findAllSectionsAsMap();
		for (Section section : sections.values()) populateRewards(section.getRewards(), scenarios);
		return log.exit(sections);
	}


	/* --------------- */
	/* PRIVATE METHODS */
	/* --------------- */

	private void populateRewards(Rewards rewards, Map<String,Scenario> scenarios) {
		if (rewards != null) {
			List<Scenario> rewardScenarios = rewards.getScenarios();
			if (rewardScenarios != null) populateRewardsScenarios(rewardScenarios, scenarios);

			List<Scenario> rewardLockedOutScenarios = rewards.getLockedOutScenarios();
			if (rewardLockedOutScenarios != null) populateRewardsScenarios(rewardLockedOutScenarios, scenarios);
		}
	}

	private void populateRewardsScenarios(List<Scenario> rewardScenarios, Map<String,Scenario> scenarios) {
		for (int i = 0; i < rewardScenarios.size(); i++) {
			Scenario scenario = scenarios.get(rewardScenarios.get(i).getId());
			if (scenario != null) {
				scenario.setLinked(rewardScenarios.get(i).isLinked());
				scenario.setForceLinked(rewardScenarios.get(i).isForceLinked());
				rewardScenarios.set(i, scenario);
			}
		}
	}
	
}
