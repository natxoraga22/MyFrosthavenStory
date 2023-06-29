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
import txraga.frosthaven.model.Scenario;
import txraga.frosthaven.model.Section;


@XSlf4j
public final class CampaignUtils {
	
	private final static String WELCOME_FILE_PATH = "static/json/welcome.txt";
	private final static String CHARACTERS_FILE_PATH = "static/json/characters.json";
	private final static String SCENARIOS_FOLDER_PATH = "static/json/scenarios";
	private final static String SECTIONS_FILE_PATH = "static/json/sections.json";
	private final static String EVENTS_FOLDER_PATH = "static/json/events";


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

	/** Gets all Frosthaven characters info (name, race, background, etc.) from "characters.json" file */
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
	public static Scenario getScenario(String scenarioId, List<String> path) {
		log.entry(scenarioId, path);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File scenarioFile = new ClassPathResource(SCENARIOS_FOLDER_PATH + "/" + scenarioId + ".json").getFile();
			Scenario scenario = objectMapper.readValue(scenarioFile, Scenario.class);
			if (path != null && path.size() > 0) scenario.setPath(path);
			scenario.replaceIcons();
			return log.exit(scenario);
		}
		catch (IOException e) {
			log.warn("Error reading and parsing file '" + SCENARIOS_FOLDER_PATH + "/" + scenarioId + ".json'", e);
			return log.exit(null);
		}
	}

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
	public static Map<String,Event> getEvents() {
		log.entry();
		Map<String,Event> events = new HashMap<>();
		for (Event.Type type : Event.Type.values()) {
			if (type == Event.Type.B) events.putAll(getEvents(type, null));
			else {
				for (Event.Season season : Event.Season.values()) {
					events.putAll(getEvents(type, season));
				}
			}
		}
		return log.exit(events);
	}

	/** Gets Frosthaven events from its file based on event type and season */
	public static Map<String,Event> getEvents(Event.Type type, Event.Season season) {
		log.entry(type, season);
		String seasonAndTypeString = type == Event.Type.B ? type.name() : season.name() + type.name();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File eventsFile = new ClassPathResource(EVENTS_FOLDER_PATH + "/" + seasonAndTypeString + ".json").getFile();
			Map<String,Event> events = objectMapper.readValue(eventsFile, new TypeReference<Map<String,Event>>(){});
			for (Event event : events.values()) {
				event.setType(type);
				event.setSeason(type == Event.Type.B ? null : season);
				if (event.getOptions() != null) {
					for (Entry<String,Event.Option> optionEntry : event.getOptions().entrySet()) {
						if (optionEntry.getValue().getId() == null) optionEntry.getValue().setId(optionEntry.getKey());
					}
				}
				event.replaceIcons();
			}
			return log.exit(events);
		}
		catch (IOException e) {
			log.warn("Error reading and parsing file '" + EVENTS_FOLDER_PATH + "/" + seasonAndTypeString + ".json'", e);
			return log.exit(Map.of());
		}
	}

}