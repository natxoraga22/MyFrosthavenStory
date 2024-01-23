package txraga.mystory.frosthaven;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.model.Event;
import txraga.mystory.frosthaven.model.OutpostPhase;
import txraga.mystory.frosthaven.model.Rewards;
import txraga.mystory.frosthaven.model.Scenario;
import txraga.mystory.frosthaven.model.Section;


@XSlf4j
@Getter
public class CampaignTracker {

	private final Frosthaven frosthaven;

	//private List<FhCharacter> party;
	//private Map<String,List<Event>> availableEvents;
	private List<Scenario> availableScenarios;


	public CampaignTracker(Frosthaven frosthaven) {
		this.frosthaven = frosthaven;

		availableScenarios = new ArrayList<>();
		availableScenarios.add(frosthaven.getScenario("000"));
		availableScenarios.add(frosthaven.getScenario("001"));
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
		log.exit();
	}


	private void trackRewards(Rewards rewards) {
		if (rewards != null) {
			// New scenarios
			rewards.getScenarios().forEach(rewardScenario -> {
				availableScenarios.add(frosthaven.getScenario(rewardScenario.getId()));
			});
			// Locked out scenarios
			rewards.getLockedOutScenarios().forEach(rewardLockedOutScenario -> {
				availableScenarios.removeIf(availableScenario -> availableScenario.getId().equals(rewardLockedOutScenario.getId()));
			});
		}
	}

}