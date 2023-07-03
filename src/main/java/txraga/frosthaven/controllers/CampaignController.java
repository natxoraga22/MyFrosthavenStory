package txraga.frosthaven.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.XSlf4j;
import txraga.frosthaven.FrosthavenFiles;
import txraga.frosthaven.Frosthaven;
import txraga.frosthaven.model.Building;
import txraga.frosthaven.model.Event;
import txraga.frosthaven.model.FhCharacter;
import txraga.frosthaven.model.OutpostPhase;
import txraga.frosthaven.model.PersonalStory;
import txraga.frosthaven.model.Scenario;
import txraga.frosthaven.model.Section;
import txraga.frosthaven.model.StoryItem;
import txraga.frosthaven.model.StoryObject;


@XSlf4j
@Controller
@RequestMapping("/")
public class CampaignController {
	
	@Autowired private Frosthaven frosthaven;


	@GetMapping("")
	public ModelAndView campaign(Model model) throws IOException {
		log.entry();
		model.addAttribute("welcome", FrosthavenFiles.getWelcome());
		return log.exit(new ModelAndView("campaign"));
	}

	@PostMapping("/personalStory")
	public ModelAndView personalStory(Model model, @RequestBody PersonalStory personalStory) throws IOException {
		log.entry(personalStory);

		// Fill storyObjects list with the elements from myStory list
		int outpostPhaseId = 1;
		List<StoryObject> story = new ArrayList<>();
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
				OutpostPhase outpostPhase = getOutpostPhase(storyItem.getOutpostPhase(), outpostPhaseId);
				if (outpostPhase != null) story.add(outpostPhase);
				outpostPhaseId++;
			}
		}

		model.addAttribute("welcome", FrosthavenFiles.getWelcome());
		model.addAttribute("party", getParty(personalStory.getParty()));
		model.addAttribute("story", story);
		return log.exit(new ModelAndView("campaign :: campaign"));
	}


	/* ----- */
	/* PARTY */
	/* ----- */

	private List<FhCharacter> getParty(List<FhCharacter> personalStoryParty) {
		log.entry(personalStoryParty);
		List<FhCharacter> party = new ArrayList<>();
		for (FhCharacter personalStoryPartyMember : personalStoryParty) {
			FhCharacter partyMember = frosthaven.getCharacter(personalStoryPartyMember.getId());
			if (partyMember != null) {
				// Set personal quest from personal story
				partyMember.setPersonalQuest(personalStoryPartyMember.getPersonalQuest());
				party.add(partyMember);
			}
		}
		return log.exit(party);
	}


	/* ----- */
	/* EVENT */
	/* ----- */

	private Event getEvent(Event storyItemEvent) {
		log.entry(storyItemEvent);
		Event event = frosthaven.getEvent(storyItemEvent.getId());
		if (event != null) {
			event.setChosenOption(storyItemEvent.getChosenOption());
			// Set section from section book
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
			// Set path from personal story
			if (storyItemScenario.getPath() != null) scenario.setPath(storyItemScenario.getPath());
		}
		return log.exit(scenario);
	}


	/* ------------- */
	/* OUTPOST PHASE */
	/* ------------- */

	private OutpostPhase getOutpostPhase(OutpostPhase outpostPhase, int id) {
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

		// LEVEL UP
		if (outpostPhase.getLevelUp() != null) {
			List<FhCharacter> levelUp = new ArrayList<>();
			for (FhCharacter characterLevelingUp : outpostPhase.getLevelUp()) {
				FhCharacter character = frosthaven.getCharacter(characterLevelingUp.getId());
				if (character != null) {
					character.setLevel(characterLevelingUp.getLevel());
					levelUp.add(character);
				}
			}
			outpostPhase.setLevelUp(levelUp);
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
