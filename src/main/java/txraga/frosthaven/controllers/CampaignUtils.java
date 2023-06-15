package txraga.frosthaven.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.XSlf4j;
import txraga.frosthaven.model.Event;
import txraga.frosthaven.model.FhCharacter;
import txraga.frosthaven.model.Party;
import txraga.frosthaven.model.Scenario;
import txraga.frosthaven.model.Section;
import txraga.frosthaven.model.StoryItem;


@XSlf4j
public final class CampaignUtils {
	
	private final static String MY_STORY_FILE_PATH = "static/json/myStory.json";
	private final static String WELCOME_FILE_PATH = "static/json/welcome.txt";
	private final static String PARTY_FILE_PATH = "static/json/myParty.json";
	private final static String CHARACTERS_FILE_PATH = "static/json/characters.json";
	private final static String SECTIONS_FILE_PATH = "static/json/sections.json";
	private final static String EVENTS_FOLDER_PATH = "static/json/events/";


	/** Gets all StoryItem from "myParty.json" file */
	public static List<StoryItem> getMyStory() {
		log.entry();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File myStoryFile = new ClassPathResource(MY_STORY_FILE_PATH).getFile();
			return log.exit(objectMapper.readValue(myStoryFile, new TypeReference<List<StoryItem>>(){}));
		} 
		catch (IOException e) {
			log.warn("Error reading and parsing file '" + MY_STORY_FILE_PATH + "'", e);
			return log.exit(List.of());
		}
	}

	/** Gets chapter "Welcome to Frosthaven" content from "welcome.txt" file */
	public static String getWelcome() {
		log.entry();
		try {
			File welcomeFile = new ClassPathResource(WELCOME_FILE_PATH).getFile();
			List<String> welcomeLines = Files.readAllLines(welcomeFile.toPath());
			return log.exit(String.join("<br/>", welcomeLines));
		}
		catch (IOException e) {
			log.warn("Error reading file '" + WELCOME_FILE_PATH + "'", e);
			return log.exit(null);
		}
	}

	/** Gets chapter "The party" content from "myParty.json" file */
	public static Party getParty() {
		log.entry();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File partyFile = new ClassPathResource(PARTY_FILE_PATH).getFile();
			Party party = objectMapper.readValue(partyFile, Party.class);

			Map<String,FhCharacter> charactersBackgrounds = CampaignUtils.getCharacters();
			for (FhCharacter character : party.getCharacters()) {
				character.setBackground(charactersBackgrounds.get(character.getNameId()).getBackground());
			}
			return log.exit(party);
		}
		catch (IOException e) {
			log.warn("Error reading and parsing file '" + PARTY_FILE_PATH + "'", e);
			return log.exit(null);
		}
	}

	/** Gets all Frosthaven characters info (background, etc.) from "characters.json" file */
	public static Map<String,FhCharacter> getCharacters() {
		log.entry();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File charactersFile = new ClassPathResource(CHARACTERS_FILE_PATH).getFile();
			return log.exit(objectMapper.readValue(charactersFile, new TypeReference<Map<String,FhCharacter>>(){}));
		}
		catch (IOException e) {
			log.warn("Error reading and parsing file '" + CHARACTERS_FILE_PATH + "'", e);
			return log.exit(null);
		}
	}

	/** Gets a Scenario from its file based on scenario id */
	/*public static Scenario getScenario(String scenarioId) {
		log.entry(scenarioId, path);
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
	}*/

	/** Gets all sections not related to scenarios (from the Section Book) from "sections.json" file */
	public static Map<String,Section> getSections() {
		log.entry();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File sectionsFile = new ClassPathResource(SECTIONS_FILE_PATH).getFile();
			Map<String,Section> sections = objectMapper.readValue(sectionsFile, new TypeReference<Map<String,Section>>(){});
			sections.values().forEach(section -> section.setScenario(false));
			return log.exit(sections);
		}
		catch (IOException e) {
			log.warn("Error reading and parsing file '" + SECTIONS_FILE_PATH + "'", e);
			return log.exit(Map.of());
		}
	}

	/** Gets all Frosthaven events (road, outpost, etc.) from all files inside "events" folder (there is a file by each type of event) */
	public static Map<String,Map<String,Event>> getEvents() {
		log.entry();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String,Map<String,Event>> events = new HashMap<>();

			for (Event.Season season : Event.Season.values()) {
				for (Event.Type type : Event.Type.values()) {
					String seasonAndType = type == Event.Type.B ? type.name() : season.name() + type.name();

					File eventsFile = new ClassPathResource(EVENTS_FOLDER_PATH + seasonAndType + ".json").getFile();
					Map<String,Event> seasonAndTypeEvents = objectMapper.readValue(eventsFile, new TypeReference<Map<String,Event>>(){});
					for (Event event : seasonAndTypeEvents.values()) {
						event.setSeason(type == Event.Type.B ? null : season);
						event.setType(type);
						if (event.getOptions() != null) {
							for (Entry<String,Event.Option> optionEntry : event.getOptions().entrySet()) {
								if (optionEntry.getValue().getId() == null) optionEntry.getValue().setId(optionEntry.getKey());
							}
						}
						event.replaceIcons();
					}
					events.put(seasonAndType, seasonAndTypeEvents);
				}
			}
			return log.exit(events);
		}
		catch (IOException e) {
			log.warn("Error reading and parsing files inside '" + EVENTS_FOLDER_PATH + "'", e);
			return log.exit(Map.of());
		}
	}

}
