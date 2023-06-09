package txraga.frosthaven.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.XSlf4j;
import txraga.frosthaven.Frosthaven;
import txraga.frosthaven.FrosthavenFiles;
import txraga.frosthaven.model.Building;
import txraga.frosthaven.model.Event;
import txraga.frosthaven.model.FhCharacter;
import txraga.frosthaven.model.OutpostPhase;
import txraga.frosthaven.model.PersonalQuest;
import txraga.frosthaven.model.Scenario;
import txraga.frosthaven.model.Section;
import txraga.frosthaven.model.StoryObject;
import txraga.frosthaven.model.personal.PersonalStory;
import txraga.frosthaven.model.personal.StoryItem;


@XSlf4j
@Controller
@RequestMapping("/")
public class CampaignController {
	
	@Autowired private Frosthaven frosthaven;


	@GetMapping("")
	public ModelAndView campaign(Model model) throws IOException {
		log.entry();
		model.addAttribute("page", Page.CAMPAIGN);
		model.addAttribute("welcome", FrosthavenFiles.getWelcome());
		return log.exit(new ModelAndView("campaign"));
	}

	@PostMapping("/personalStory")
	public ModelAndView personalStory(Model model, @RequestBody(required = false) PersonalStory personalStory) throws IOException {
		log.entry(personalStory);
		Map<String,FhCharacter> party = getParty(personalStory != null ? personalStory.getParty() : new ArrayList<>());
		Map<String,FhCharacter> originalParty = new LinkedHashMap<>(party);

		// Fill storyObjects list with the elements from myStory list
		int outpostPhaseId = 1;
		List<StoryObject> story = new ArrayList<>();
		if (personalStory != null) {
			for (StoryItem storyItem : personalStory.getStory()) {
				// Event
				if (storyItem.getEvent() != null) {
					Event event = getEvent(storyItem.getEvent());
					if (event != null) story.add(event);
				}
				// Scenario
				else if (storyItem.getScenario() != null) {
					Scenario scenario = getScenario(storyItem.getScenario());
					if (scenario != null) story.add(scenario);
				}
				// Outpost phase
				else if (storyItem.getOutpostPhase() != null) {
					OutpostPhase outpostPhase = getOutpostPhase(storyItem.getOutpostPhase(), outpostPhaseId, party);
					if (outpostPhase != null) story.add(outpostPhase);
					outpostPhaseId++;
				}
			}
		}

		model.addAttribute("welcome", FrosthavenFiles.getWelcome());
		model.addAttribute("party", originalParty.values());
		model.addAttribute("story", story);
		return log.exit(new ModelAndView("fragments/campaign :: campaign"));
	}


	/* ----- */
	/* PARTY */
	/* ----- */

	private Map<String,FhCharacter> getParty(List<FhCharacter> personalStoryParty) {
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
				if (personalQuest != null) partyMember.setPersonalQuest(personalQuest);
			}
			return log.exit(partyMember);
		}
		return log.exit(null);
	}


	/* ----- */
	/* EVENT */
	/* ----- */

	private Event getEvent(Event storyItemEvent) {
		log.entry(storyItemEvent);
		Event event = frosthaven.getEvent(storyItemEvent.getId());
		if (event != null) {
			// Set chosen option
			event.setChosenOption(storyItemEvent.getChosenOption());
			// Set section
			if (storyItemEvent.getSection() != null) {
				Section section = frosthaven.getSection(storyItemEvent.getSection().getId());
				if (section != null) event.setSection(section);
			}
		}
		return log.exit(event);
	}


	/* -------- */
	/* SCENARIO */
	/* -------- */

	private Scenario getScenario(Scenario storyItemScenario) {
		log.entry(storyItemScenario);
		Scenario scenario = FrosthavenFiles.getScenario(storyItemScenario.getId());
		if (scenario != null) {
			// Set path
			if (storyItemScenario.getPath() != null) scenario.setPath(storyItemScenario.getPath());
			// Set randomScenarioSection
			if (storyItemScenario.getRandomScenarioSection() != null) {
				Section section = frosthaven.getSection(storyItemScenario.getRandomScenarioSection().getId());
				if (section != null) scenario.setRandomScenarioSection(section);
			}
		}
		return log.exit(scenario);
	}


	/* ------------- */
	/* OUTPOST PHASE */
	/* ------------- */

	private OutpostPhase getOutpostPhase(OutpostPhase outpostPhase, int id, Map<String,FhCharacter> party) {
		log.entry(outpostPhase);
		// ID
		outpostPhase.setId(id);

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
				FhCharacter character = party.get(characterLevelingUp.getId());
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
					upgrade.add(building);
				}
			}
			outpostPhase.setUpgrade(upgrade);
		}
		return log.exit(outpostPhase);
	}

}
