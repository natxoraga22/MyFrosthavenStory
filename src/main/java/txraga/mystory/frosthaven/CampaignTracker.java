package txraga.mystory.frosthaven;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.model.Event;
import txraga.mystory.frosthaven.model.OutpostPhase;
import txraga.mystory.frosthaven.model.Rewards;
import txraga.mystory.frosthaven.model.Scenario;
import txraga.mystory.frosthaven.model.Section;
import txraga.mystory.frosthaven.model.Event.TypeAndSeason;


@XSlf4j
@Getter
public class CampaignTracker {

	private final Frosthaven frosthaven;

	private Map<Event.TypeAndSeason,List<Event>> availableEvents;
	private List<Scenario> availableScenarios;


	public CampaignTracker(Frosthaven frosthaven) {
		this.frosthaven = frosthaven;

		// Initial events
		availableEvents = new HashMap<>();
		for (Event.TypeAndSeason typeAndSeason : Event.TypeAndSeason.values()) {
			if (typeAndSeason != TypeAndSeason.B) {
				List<Event> events = new ArrayList<>();
				for (int i = 1; i <= 20; i++) {
					Event event = frosthaven.getEvent(typeAndSeason.name() + "-" + String.format("%02d", i));
					if (event != null) events.add(event);
				}
				availableEvents.put(typeAndSeason, events);
			}
		}
		// Initial scenarios
		availableScenarios = new ArrayList<>();
		availableScenarios.add(frosthaven.getScenario("000"));
		availableScenarios.add(frosthaven.getScenario("001"));
	}

	public List<Scenario> getMainQuestAvailableScenarios() {
		return availableScenarios.stream().filter(scenario -> scenario.getQuestLine().isMainQuest()).sorted().collect(Collectors.toList());
	}

	public List<Scenario> getSideQuestAvailableScenarios() {
		return availableScenarios.stream().filter(scenario -> !scenario.getQuestLine().isMainQuest()).sorted().collect(Collectors.toList());
	}


	public void trackEvent(Event event) {
		log.entry(event);

		// Chosen options rewards
		event.getChosenOptions().forEach(optionId -> {
			Rewards rewards = event.getOptions().get(optionId).getRewards();
			trackRewards(rewards);
		});
		// Outpost attack rewards
		if (event.getOutpostAttack() != null) {
			Rewards rewards = event.getOutpostAttack().getRewards();
			trackRewards(rewards);
		}
		log.exit();
	}

	public void trackScenario(Scenario scenario) {
		log.entry(scenario);
		availableScenarios.removeIf(availableScenario -> availableScenario.getId().equals(scenario.getId()));

		// Conclusion rewards
		scenario.getPath().forEach(sectionId -> {
			Rewards rewards = scenario.getSections().get(sectionId).getRewards();
			trackRewards(rewards);
		});
		// Random scenario section rewards
		Section randomScenarioSection = scenario.getRandomScenarioSection();
		if (randomScenarioSection != null) trackRewards(randomScenarioSection.getRewards());
		log.exit();
	}

	public void trackOutpostPhase(OutpostPhase outpostPhase) {
		log.entry(outpostPhase);
		if (outpostPhase.getTownGuardPerks() != null) {
			outpostPhase.getTownGuardPerks().forEach(section -> trackRewards(section.getRewards()));
		}
		if (outpostPhase.getPassageOfTime() != null) {
			outpostPhase.getPassageOfTime().forEach(section -> trackRewards(section.getRewards()));
		}
		if (outpostPhase.getOutpostEvent() != null) trackEvent(outpostPhase.getOutpostEvent());

		// TODO: Build, upgrade, retirement...

		log.exit();
	}


	/* --------------- */
	/* PRIVATE METHODS */
	/* --------------- */

	private void trackRewards(Rewards rewards) {
		if (rewards != null) {
			// New events
			rewards.getEvents().forEach((typeAndSeason, rewardEvents) -> {
				rewardEvents.forEach(rewardEvent -> {
					Event event = frosthaven.getEvent(rewardEvent.getId());
					if (event != null) availableEvents.get(typeAndSeason).add(event);
				});
			});
			// Removed events
			rewards.getRemovedEvents().forEach((typeAndSeason, rewardRemovedEvents) -> {
				rewardRemovedEvents.forEach(rewardRemovedEvent -> {
					availableEvents.get(typeAndSeason).removeIf(availableEvent -> availableEvent.getId().equals(rewardRemovedEvent.getId()));
				});
			});
			// New scenarios
			rewards.getScenarios().forEach(rewardScenario -> {
				Scenario scenario = frosthaven.getScenario(rewardScenario.getId());
				if (scenario != null) availableScenarios.add(scenario);
			});
			// Locked out scenarios
			rewards.getLockedOutScenarios().forEach(rewardLockedOutScenario -> {
				availableScenarios.removeIf(availableScenario -> availableScenario.getId().equals(rewardLockedOutScenario.getId()));
			});
		}
	}

}