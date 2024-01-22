package txraga.mystory.frosthaven;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.model.Rewards;
import txraga.mystory.frosthaven.model.Scenario;


@XSlf4j
@RequiredArgsConstructor
@Getter
@Component
public class CampaignTracker {

	private final Frosthaven frosthaven;

	//private List<FhCharacter> party;
	//private Map<String,List<Event>> availableEvents;
	private List<Scenario> availableScenarios;


	@PostConstruct
	private void init() {
		log.entry();
		availableScenarios = new ArrayList<>();
		availableScenarios.add(frosthaven.getScenario("000"));
		availableScenarios.add(frosthaven.getScenario("001"));
		log.exit();
	}

	public void trackScenario(Scenario scenario) {
		log.entry(scenario);
		availableScenarios.removeIf(availableScenario -> availableScenario.getId().equals(scenario.getId()));

		scenario.getPath().forEach(sectionId -> {
			Rewards rewards = scenario.getSections().get(sectionId).getRewards();
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
		});
		log.exit();
	}

}