package txraga.frosthaven.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.XSlf4j;
import txraga.frosthaven.model.Event;
import txraga.frosthaven.model.FhCharacter;
import txraga.frosthaven.model.OutpostPhase;
import txraga.frosthaven.model.Party;
import txraga.frosthaven.model.Scenario;
import txraga.frosthaven.model.StoryItem;
import txraga.frosthaven.model.StoryObject;


@XSlf4j
@Controller
@RequestMapping("/")
public class CampaignController {
	
	@GetMapping("")
	public ModelAndView campaign(Model model) {
		log.entry();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// Party
			File partyFile = new ClassPathResource("static/json/party.json").getFile();
			Party party = objectMapper.readValue(partyFile, Party.class);
			log.debug("{}", party);

			// My Story
			File myStoryFile = new ClassPathResource("static/json/myStory.json").getFile();
			List<StoryItem> myStory = objectMapper.readValue(myStoryFile, new TypeReference<List<StoryItem>>(){});
			log.debug("{}", myStory);

			Map<String,Map<String,Event>> events = getEvents();
			int outpostPhaseId = 1;
			List<StoryObject> storyObjects = new ArrayList<>();
			for (StoryItem storyItem : myStory) {
				// Scenario
				if (storyItem.getScenario() != null) {
					Scenario scenario = getScenario(storyItem);
					if (scenario != null) storyObjects.add(scenario);
				}
				// Outpost phase
				else if (storyItem.isOutpost()) {
					OutpostPhase outpostPhase = getOutpostPhase(storyItem);
					outpostPhase.setId(outpostPhaseId++);
					outpostPhase.setEvent(getEvent(storyItem, events));
					if (outpostPhase != null) storyObjects.add(outpostPhase);
				}
				// Event
				else if (storyItem.getEvent() != null) {
					Event event = getEvent(storyItem, events);
					if (event != null) storyObjects.add(event);
				}
			}

			model.addAttribute("welcome", getWelcome());
			model.addAttribute("party", party);
			model.addAttribute("characters", getCharacters());
			model.addAttribute("storyObjectsList", storyObjects);
		}
		catch (IOException e) {
			log.catching(e);
		}
		return log.exit(new ModelAndView("campaign"));
	}

	private String getWelcome() throws IOException {
		log.entry();
		File welcomeFile = new ClassPathResource("static/json/welcome.txt").getFile();
		List<String> welcomeLines = Files.readAllLines(welcomeFile.toPath());
		return log.exit(String.join("<br/>", welcomeLines));
	}

	private Map<String,FhCharacter> getCharacters() throws IOException {
		log.entry();
		ObjectMapper objectMapper = new ObjectMapper();
		File charactersFile = new ClassPathResource("static/json/characters.json").getFile();
		return log.exit(objectMapper.readValue(charactersFile, new TypeReference<Map<String,FhCharacter>>(){}));		
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
			log.warn("JSON file for scenario '{}' not found", storyItem.getScenario());
			return log.exit(null);
		}
	}

	private OutpostPhase getOutpostPhase(StoryItem storyItem) {
		log.entry(storyItem);
		OutpostPhase outpostPhase = new OutpostPhase();
		outpostPhase.setLevelUp(storyItem.getLevelUp());
		outpostPhase.setBuild(storyItem.getBuild());
		outpostPhase.setUpgrade(storyItem.getUpgrade());
		return log.exit(outpostPhase);
	}

	private Map<String,Map<String,Event>> getEvents() throws IOException {
		log.entry();
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String,Map<String,Event>> events = new HashMap<>();

		for (Event.Season season : Event.Season.values()) {
			for (Event.Type type : Event.Type.values()) {
				String seasonAndType = type == Event.Type.B ? "B" : season.name() + type.name();

				log.debug("seasonAndType: {}", seasonAndType);

				File eventsFile = new ClassPathResource("static/json/events/" + seasonAndType + ".json").getFile();
				Map<String,Event> seasonAndTypeEvents = objectMapper.readValue(eventsFile, new TypeReference<Map<String,Event>>(){});
				for (Event event : seasonAndTypeEvents.values()) {
					event.setSeason(season);
					event.setType(type);
				}

				log.debug("seasonAndTypeEvents: {}", seasonAndTypeEvents);

				events.put(seasonAndType, seasonAndTypeEvents);
			}
		}
		return log.exit(events);
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
