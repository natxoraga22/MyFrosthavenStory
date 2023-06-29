package txraga.frosthaven.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.XSlf4j;
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
	
	@GetMapping("")
	public ModelAndView campaign(Model model) throws IOException {
		log.entry();
		model.addAttribute("welcome", CampaignUtils.getWelcome());
		return log.exit(new ModelAndView("campaign"));
	}

	@PostMapping("/personalStory")
	public ModelAndView personalStory(Model model, @RequestBody PersonalStory personalStory) throws IOException {
		log.entry(personalStory);		
		// Get all characters, sections and events
		Map<String,FhCharacter> characters = CampaignUtils.getCharacters();
		Map<String,Section> sections = CampaignUtils.getSections();
		Map<String,Event> events = CampaignUtils.getEvents();

		// Fill storyObjects list with the elements from myStory list
		int outpostPhaseId = 1;
		List<StoryObject> story = new ArrayList<>();
		for (StoryItem storyItem : personalStory.getStory()) {
			// Event
			if (storyItem.getEvent() != null) {
				Event event = getEvent(storyItem.getEvent(), events);
				if (event == null) log.warn("Event {} not found", storyItem.getEvent().getId());
				else story.add(event);
			}
			// Scenario
			else if (storyItem.getScenario() != null) {
				Scenario scenario = CampaignUtils.getScenario(storyItem.getScenario().getId(), storyItem.getScenario().getPath());
				if (scenario == null) log.warn("Scenario {} not found", storyItem.getScenario().getId());
				else story.add(scenario);
			}
			// Outpost phase
			else if (storyItem.getOutpostPhase() != null) {
				OutpostPhase outpostPhase = getOutpostPhase(storyItem.getOutpostPhase(), outpostPhaseId, characters, sections, events);
				if (outpostPhase == null) log.warn("Outpost phase {} not found", outpostPhaseId);
				else story.add(outpostPhase);
				outpostPhaseId++;
			}
		}

		model.addAttribute("welcome", CampaignUtils.getWelcome());
		model.addAttribute("party", getParty(personalStory.getParty(), characters));
		model.addAttribute("story", story);
		return log.exit(new ModelAndView("campaign :: campaign"));
	}

	private List<FhCharacter> getParty(List<FhCharacter> personalStoryParty, Map<String,FhCharacter> characters) {
		log.entry(personalStoryParty);
		List<FhCharacter> party = new ArrayList<>();
		for (FhCharacter personalStoryPartyMember : personalStoryParty) {
			// Get party member (static info) from characters map
			FhCharacter partyMember = characters.get(personalStoryPartyMember.getId());
			if (partyMember == null) log.warn("Character {} not found", personalStoryPartyMember.getId());
			else {
				// Set personal quest from personal story
				partyMember.setPersonalQuest(personalStoryPartyMember.getPersonalQuest());
				party.add(partyMember);
			}
		}
		return log.exit(party);
	}

	private Event getEvent(Event storyItemEvent, Map<String,Event> events) {
		log.entry(storyItemEvent);
		Event event = events.get(storyItemEvent.getId());
		if (event != null) event.setChosenOption(storyItemEvent.getChosenOption());
		return log.exit(event);
	}

	private OutpostPhase getOutpostPhase(OutpostPhase outpostPhase, int id, Map<String,FhCharacter> characters, Map<String,Section> sections, Map<String,Event> events) {
		log.entry(outpostPhase);
		outpostPhase.setId(id);
		// Get sections content for passage of time
		if (outpostPhase.getPassageOfTime() != null) {
			List<Section> passageOfTime = new ArrayList<>();
			for (Section storyItemSection : outpostPhase.getPassageOfTime()) {
				Section section = sections.get(storyItemSection.getId());
				if (section == null) log.warn("Section {} not found", storyItemSection.getId());
				else passageOfTime.add(section);
			}
			outpostPhase.setPassageOfTime(passageOfTime);
		}
		// Get outpost event content
		outpostPhase.setOutpostEvent(getEvent(outpostPhase.getOutpostEvent(), events));
		// Get characters content
		if (outpostPhase.getLevelUp() != null) {
			List<FhCharacter> levelUp = new ArrayList<>();
			for (FhCharacter characterLevelingUp : outpostPhase.getLevelUp()) {
				FhCharacter character = characters.get(characterLevelingUp.getId());
				if (character == null) log.warn("Character {} not found", characterLevelingUp.getId());
				else {
					character.setLevel(characterLevelingUp.getLevel());
					levelUp.add(character);
				}
			}
			outpostPhase.setLevelUp(levelUp);
		}
		return log.exit(outpostPhase);
	}

}
