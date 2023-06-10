package txraga.frosthaven.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.XSlf4j;
import txraga.frosthaven.model.Event;
import txraga.frosthaven.model.OutpostPhase;
import txraga.frosthaven.model.Scenario;
import txraga.frosthaven.model.Section;
import txraga.frosthaven.model.StoryItem;
import txraga.frosthaven.model.StoryObject;


@XSlf4j
@Controller
@RequestMapping("/")
public class CampaignController {
	
	@GetMapping("")
	public ModelAndView campaign(Model model) {
		log.entry();
		try {
			List<StoryItem> myStory = CampaignUtils.getMyStory();
			
			// Get all sections and events
			Map<String,Section> sections = CampaignUtils.getSections();
			Map<String,Map<String,Event>> events = CampaignUtils.getEvents();

			// Fill storyObjects list with the elements from myStory list
			int outpostPhaseId = 1;
			List<StoryObject> storyObjects = new ArrayList<>();
			for (StoryItem storyItem : myStory) {
				// Scenario
				if (storyItem.getScenario() != null) {
					Scenario scenario = getScenario(storyItem);
					if (scenario == null) log.warn("Scenario {} not found", storyItem.getScenario());
					else storyObjects.add(scenario);
				}
				// Outpost phase
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
			}

			model.addAttribute("welcome", CampaignUtils.getWelcome());
			model.addAttribute("party", CampaignUtils.getParty());
			model.addAttribute("storyObjectsList", storyObjects);
		}
		catch (IOException e) {
			log.catching(e);
		}
		return log.exit(new ModelAndView("campaign"));
	}

	private Scenario getScenario(StoryItem storyItem) throws IOException {
		log.entry(storyItem);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			File scenarioFile = new ClassPathResource("static/json/scenarios/" + storyItem.getScenario() + ".json").getFile();
			Scenario scenario = objectMapper.readValue(scenarioFile, Scenario.class);
			if (storyItem.getPath() != null && storyItem.getPath().size() > 0) scenario.setPath(storyItem.getPath());
			scenario.replaceIcons();
			return log.exit(scenario);
		}
		catch (IOException e) {
			log.warn("Error reading and parsing JSON file for scenario " + storyItem.getScenario(), e);
			return log.exit(null);
		}
	}

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

}
