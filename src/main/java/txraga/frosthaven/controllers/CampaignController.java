package txraga.frosthaven.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
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
			Map<String,Event> events = getEvents(Event.Season.S, Event.Type.O);

			List<StoryObject> storyObjects = new ArrayList<>();
			for (StoryItem storyItem : myStory) {
				// Scenario
				if (storyItem.getScenario() != null) {
					storyObjects.add(getScenario(storyItem));
				}
				// Event
				else if (storyItem.getEvent() != null) {
					storyObjects.add(getEvent(storyItem, events));
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
		File scenarioFile = new ClassPathResource("static/json/scenarios/" + storyItem.getScenario() + ".json").getFile();
		Scenario scenario = objectMapper.readValue(scenarioFile, Scenario.class);
		if (storyItem.getPath() != null && storyItem.getPath().size() > 0) scenario.setPath(storyItem.getPath());
		scenario.replaceIcons();
		return log.exit(scenario);
	}

	private Map<String,Event> getEvents(Event.Season season, Event.Type type) throws IOException {
		log.entry(season, type);
		ObjectMapper objectMapper = new ObjectMapper();
		File eventsFile = new ClassPathResource("static/json/events/" + season + type + ".json").getFile();
		return log.exit(objectMapper.readValue(eventsFile, new TypeReference<Map<String,Event>>(){}));
	}

	private Event getEvent(StoryItem storyItem, Map<String,Event> events) {
		log.entry(storyItem);
		Event event = events.get(storyItem.getEvent());
		// TODO: Chose option
		//if (storyItem.getOption() != null) event.setOption(storyItem.getOption());
		return log.exit(event);
	} 

}
