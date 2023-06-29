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
import txraga.frosthaven.model.FhCharacter;
import txraga.frosthaven.model.PersonalStory;
import txraga.frosthaven.model.Scenario;
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
		// Get all sections and events
		//Map<String,Section> sections = CampaignUtils.getSections();
		//Map<String,Map<String,Event>> events = CampaignUtils.getEvents();

		// Fill storyObjects list with the elements from myStory list
		//int outpostPhaseId = 1;
		List<StoryObject> story = new ArrayList<>();
		for (StoryItem storyItem : personalStory.getStory()) {
			// Scenario
			if (storyItem.getScenario() != null) {
				Scenario scenario = CampaignUtils.getScenario(storyItem.getScenario().getId(), storyItem.getScenario().getPath());
				if (scenario == null) log.warn("Scenario {} not found", storyItem.getScenario().getId());
				else story.add(scenario);
			}
			// Outpost phase
			/*
			else if (storyItem.isOutpostPhase()) {
				OutpostPhase outpostPhase = getOutpostPhase(storyItem, outpostPhaseId, sections, events);
				if (outpostPhase == null) log.warn("Outpost phase #{} not found", outpostPhaseId);
				else storyObjects.add(outpostPhase);
				outpostPhaseId++;
			}
			// Event
			else if (storyItem.getEvent() != null) {
				Event event = getEvent(storyItem, events);
				if (event == null) log.warn("Event {} not found", storyItem.getEvent());
				else storyObjects.add(event);
			}
			*/
		}

		model.addAttribute("welcome", CampaignUtils.getWelcome());
		model.addAttribute("party", getParty(personalStory.getParty()));
		model.addAttribute("story", story);
		return log.exit(new ModelAndView("campaign :: campaign"));
	}

	private List<FhCharacter> getParty(List<FhCharacter> personalStoryParty) {
		log.entry(personalStoryParty);
		Map<String,FhCharacter> characters = CampaignUtils.getCharacters();
		List<FhCharacter> party = new ArrayList<>();
		for (FhCharacter personalStoryPartyMember : personalStoryParty) {
			// Get party member (static info) from characters map
			FhCharacter partyMember = characters.get(personalStoryPartyMember.getId());
			// Set personal quest from personal story
			partyMember.setPersonalQuest(personalStoryPartyMember.getPersonalQuest());
			party.add(partyMember);
		}
		return log.exit(party);
	}

	/*
	private OutpostPhase getOutpostPhase(StoryItem storyItem, int id, Map<String,Section> sections, Map<String,Map<String,Event>> events) {
		log.entry(storyItem);
		OutpostPhase outpostPhase = new OutpostPhase();
		outpostPhase.setId(id);
		if (storyItem.getPassageOfTime() != null) {
			outpostPhase.setSections(storyItem.getPassageOfTime().stream().map(section -> sections.get(section)).toList());
		}
		outpostPhase.setEvent(getEvent(storyItem, events));
		outpostPhase.setLevelUp(storyItem.getLevelUp());
		outpostPhase.setBuild(storyItem.getBuild());
		outpostPhase.setUpgrade(storyItem.getUpgrade());
		return log.exit(outpostPhase);
	}

	private Event getEvent(StoryItem storyItem, Map<String,Map<String,Event>> events) {
		log.entry(storyItem);
		String eventId = storyItem.getEvent();
		String eventSeasonAndType = eventId.substring(0, eventId.indexOf("-"));
		Event event = events.get(eventSeasonAndType).get(eventId);
		if (event != null) event.setChosenOption(storyItem.getOption());
		return log.exit(event);
	}
	*/

}
