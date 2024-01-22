package txraga.mystory.frosthaven;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.model.Event;
import txraga.mystory.frosthaven.model.Scenario;
import txraga.mystory.frosthaven.model.Section;


@XSlf4j
@RequiredArgsConstructor
@Component
public class FrosthavenCampaign {

	private final Frosthaven frosthaven;


	/* ------ */
	/* EVENTS */
	/* ------ */

	public Event getEvent(String eventId, List<String> chosenOptions, String randomScenarioSectionId) {
		log.entry(eventId, chosenOptions);
		Event event = frosthaven.getEvent(eventId);
		if (event != null) {
			// Set chosen options
			event.setChosenOptions(chosenOptions);
			// Set randomScenarioSection
			if (randomScenarioSectionId != null) {
				Section section = frosthaven.getSection(randomScenarioSectionId);
				if (section != null) event.setRandomScenarioSection(section);
			}
		}
		return log.exit(event);
	}

	public Event getEvent(Event storyItemEvent) {
		log.entry(storyItemEvent);
		Section randomScenarioSection = storyItemEvent.getRandomScenarioSection();
		return log.exit(getEvent(storyItemEvent.getId(),
		                         storyItemEvent.getChosenOptions(),
		                         randomScenarioSection != null ? randomScenarioSection.getId() : null));
	}


	/* --------- */
	/* SCENARIOS */
	/* --------- */

	public Scenario getScenario(String scenarioId, List<String> path, String randomScenarioSectionId) {
		log.entry(scenarioId, path, randomScenarioSectionId);
		Scenario scenario = frosthaven.getScenario(scenarioId);
		if (scenario != null) {
			// Set path
			if (path != null) scenario.setPath(path);
			// Set randomScenarioSection
			if (randomScenarioSectionId != null) {
				Section section = frosthaven.getSection(randomScenarioSectionId);
				if (section != null) scenario.setRandomScenarioSection(section);
			}
		}
		return log.exit(scenario);
	}

	public Scenario getScenario(Scenario storyItemScenario) {
		log.entry(storyItemScenario);
		Section randomScenarioSection = storyItemScenario.getRandomScenarioSection();
		return log.exit(getScenario(storyItemScenario.getId(),
		                            storyItemScenario.getPath(),
		                            randomScenarioSection != null ? randomScenarioSection.getId() : null));
	}

}
