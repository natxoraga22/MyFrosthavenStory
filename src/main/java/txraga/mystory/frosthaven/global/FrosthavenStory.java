package txraga.mystory.frosthaven.global;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.model.Building;
import txraga.mystory.frosthaven.model.Event;
import txraga.mystory.frosthaven.model.FhCharacter;
import txraga.mystory.frosthaven.model.OutpostPhase;
import txraga.mystory.frosthaven.model.PersonalQuest;
import txraga.mystory.frosthaven.model.Scenario;
import txraga.mystory.frosthaven.model.Section;


@XSlf4j
@RequiredArgsConstructor
@Component
public class FrosthavenStory {

	private final Frosthaven frosthaven;


	/* ----- */
	/* PARTY */
	/* ----- */

	public Map<String,FhCharacter> getParty(List<FhCharacter> personalStoryParty) {
		log.entry(personalStoryParty);
		Map<String,FhCharacter> party = new LinkedHashMap<>();
		for (FhCharacter personalStoryPartyMember : personalStoryParty) {
			FhCharacter partyMember = getPartyMember(personalStoryPartyMember);
			if (partyMember != null) party.put(partyMember.getId(), partyMember);
		}
		return log.exit(party);
	}

	private FhCharacter getPartyMember(FhCharacter personalStoryPartyMember) {
		log.entry(personalStoryPartyMember);
		FhCharacter partyMember = frosthaven.getCharacter(personalStoryPartyMember.getId());
		if (partyMember != null) {
			if (personalStoryPartyMember.getPersonalQuest() != null) {
				// Set personal quest
				PersonalQuest personalQuest = frosthaven.getPersonalQuest(personalStoryPartyMember.getPersonalQuest().getId());
				if (personalQuest != null) {
					personalQuest.setUnlockedBuilding(personalStoryPartyMember.getPersonalQuest().getUnlockedBuilding());
					partyMember.setPersonalQuest(personalQuest);
				}
			}
			return log.exit(partyMember);
		}
		return log.exit(null);
	}


	/* ----- */
	/* EVENT */
	/* ----- */

	/*
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
	*/

	/*
	public Event getEvent(Event storyItemEvent) {
		log.entry(storyItemEvent);
		if (storyItemEvent == null) return log.exit(null);
		Section randomScenarioSection = storyItemEvent.getRandomScenarioSection();
		return log.exit(getEvent(storyItemEvent.getId(),
		                         storyItemEvent.getChosenOptions(),
		                         randomScenarioSection != null ? randomScenarioSection.getId() : null));
	}
	*/


	/* -------- */
	/* SCENARIO */
	/* -------- */

	public Scenario getScenario(String scenarioId, List<String> path, String randomScenarioSectionId) {
		log.entry(scenarioId, path, randomScenarioSectionId);
		Scenario scenario = frosthaven.getScenario(scenarioId);
		if (scenario != null) {
			// Set path
			if (path != null && !path.isEmpty()) scenario.setPath(path);
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


	/* ------------- */
	/* OUTPOST PHASE */
	/* ------------- */

	public OutpostPhase getOutpostPhase(OutpostPhase outpostPhase, int id, Map<String,FhCharacter> party) {
		log.entry(outpostPhase);
		// ID
		outpostPhase.setId(id);

		// TOWN GUARD PERKS
		if (outpostPhase.getTownGuardPerks() != null) {
			List<Section> townGuardPerks = new ArrayList<>();
			for (Section storyItemSection : outpostPhase.getTownGuardPerks()) {
				Section section = frosthaven.getSection(storyItemSection.getId());
				if (section != null) townGuardPerks.add(section);
			}
			outpostPhase.setTownGuardPerks(townGuardPerks);
		}

		// PASSAGE OF TIME
		if (outpostPhase.getPassageOfTime() != null) {
			List<Section> passageOfTime = new ArrayList<>();
			for (Section storyItemSection : outpostPhase.getPassageOfTime()) {
				Section section = frosthaven.getSection(storyItemSection.getId());
				if (section != null) passageOfTime.add(section);
			}
			outpostPhase.setPassageOfTime(passageOfTime);
		}

		// OUTPOST EVENT
		outpostPhase.setOutpostEvent(getEvent(outpostPhase.getOutpostEvent()));

		// LEVEL UPS
		if (outpostPhase.getLevelUps() != null) {
			List<FhCharacter> levelUps = new ArrayList<>();
			for (FhCharacter characterLevelingUp : outpostPhase.getLevelUps()) {
				//FhCharacter character = party.get(characterLevelingUp.getId());
				FhCharacter character = frosthaven.getCharacter(characterLevelingUp.getId());
				if (character != null) {
					// Set level
					character.setLevel(characterLevelingUp.getLevel());
					levelUps.add(character);
				}
			}
			outpostPhase.setLevelUps(levelUps);
		}

		// RETIREMENTS
		if (outpostPhase.getRetirements() != null) {
			List<FhCharacter> retirements = new ArrayList<>();
			for (FhCharacter characterRetiring : outpostPhase.getRetirements()) {
				FhCharacter character = party.get(characterRetiring.getId());
				if (character != null) {
					// Set additional personal quest
					if (characterRetiring.getAdditionalPersonalQuest() != null) {
						PersonalQuest additionalPersonalQuest = frosthaven.getPersonalQuest(characterRetiring.getAdditionalPersonalQuest().getId());
						if (additionalPersonalQuest != null) character.setAdditionalPersonalQuest(additionalPersonalQuest);
					}
					retirements.add(character);
				}
			}
			outpostPhase.setRetirements(retirements);
		}

		// NEW MEMBERS
		if (outpostPhase.getNewMembers() != null) {
			List<FhCharacter> newMembers = new ArrayList<>();
			for (FhCharacter characterJoining : outpostPhase.getNewMembers()) {
				FhCharacter character = getPartyMember(characterJoining);
				if (character != null) {
					newMembers.add(character);
					// Add new member to party map for future outpost phases
					party.put(character.getId(), character);
				}
			}
			outpostPhase.setNewMembers(newMembers);
		}

		// BUILD
		if (outpostPhase.getBuild() != null) {
			List<Building> build = new ArrayList<>();
			for (Building buildingBuilt : outpostPhase.getBuild()) {
				Building building = frosthaven.getBuilding(buildingBuilt.getId());
				if (building != null) {
					building.setLevel(buildingBuilt.getLevel());
					building.setPath(buildingBuilt.getPath());
					build.add(building);
				}
			}
			outpostPhase.setBuild(build);
		}
		
		// UPGRADE
		if (outpostPhase.getUpgrade() != null) {
			List<Building> upgrade = new ArrayList<>();
			for (Building buildingUpgraded : outpostPhase.getUpgrade()) {
				Building building = frosthaven.getBuilding(buildingUpgraded.getId());
				if (building != null) {
					building.setLevel(buildingUpgraded.getLevel());
					building.setPath(buildingUpgraded.getPath());
					upgrade.add(building);
				}
			}
			outpostPhase.setUpgrade(upgrade);
		}
		return log.exit(outpostPhase);
	}

}
