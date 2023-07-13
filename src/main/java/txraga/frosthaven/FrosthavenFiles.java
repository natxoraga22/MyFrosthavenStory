package txraga.frosthaven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.XSlf4j;
import txraga.frosthaven.model.Building;
import txraga.frosthaven.model.Event;
import txraga.frosthaven.model.FhCharacter;
import txraga.frosthaven.model.PersonalQuest;
import txraga.frosthaven.model.Scenario;
import txraga.frosthaven.model.Section;


@XSlf4j
public final class FrosthavenFiles {
	
	private final static String WELCOME_FILE_PATH = "static/json/welcome.txt";
	private final static String CHARACTERS_FILE_PATH = "static/json/characters.json";
	private final static String PERSONAL_QUESTS_FILE_PATH = "static/json/personalQuests.json";
	private final static String EVENTS_FOLDER_PATH = "static/json/events";
	private final static String SCENARIOS_FOLDER_PATH = "static/json/scenarios";
	private final static String SECTIONS_FILE_PATH = "static/json/sections.json";
	private final static String BUILDINGS_FILE_PATH = "static/json/buildings.json";


	/**
	 * Gets chapter "Welcome to Frosthaven" content from "welcome.txt" file.
	 */
	public static String getWelcome() {
		log.entry();
		try {
			InputStream welcomeInputStream = new ClassPathResource(WELCOME_FILE_PATH).getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(welcomeInputStream));
			List<String> welcomeLines = bufferedReader.lines().collect(Collectors.toList());
			return log.exit(String.join("<br/>", welcomeLines));
		}
		catch (IOException e) {
			log.warn("Error reading file '" + WELCOME_FILE_PATH + "'", e);
			return log.exit(null);
		}
	}

	/**
	 * Gets all characters from "characters.json" file.
	 */
	public static Map<String,FhCharacter> getCharacters() {
		log.entry();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			InputStream charactersInputStream = new ClassPathResource(CHARACTERS_FILE_PATH).getInputStream();
			Map<String,FhCharacter> characters = objectMapper.readValue(charactersInputStream, new TypeReference<Map<String,FhCharacter>>(){});

			// Populate characters with additional info
			for (Entry<String,FhCharacter> characterEntry : characters.entrySet()) {
				FhCharacter character = characterEntry.getValue();
				character.populate();
			}
			return log.exit(characters);
		}
		catch (IOException e) {
			log.warn("Error reading and parsing file '" + CHARACTERS_FILE_PATH + "'", e);
			return log.exit(null);
		}
	}

	/**
	 * Gets all personal quests from "personalQuests.json" file.
	 */
	public static Map<String,PersonalQuest> getPersonalQuests() {
		log.entry();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			InputStream personalQuestsInputStream = new ClassPathResource(PERSONAL_QUESTS_FILE_PATH).getInputStream();
			Map<String,PersonalQuest> personalQuests = objectMapper.readValue(personalQuestsInputStream, new TypeReference<Map<String,PersonalQuest>>(){});

			// Populate personal quests with additional info
			for (Entry<String,PersonalQuest> personalQuestEntry : personalQuests.entrySet()) {
				PersonalQuest personalQuest = personalQuestEntry.getValue();
				personalQuest.populate(personalQuestEntry.getKey());
			}
			return log.exit(personalQuests);
		}
		catch (IOException e) {
			log.warn("Error reading and parsing file '" + PERSONAL_QUESTS_FILE_PATH + "'", e);
			return log.exit(null);
		}
	}

	/**
	 * Gets all events (road, outpost, etc.) from all files inside "events" folder (there is a file by each type/season)
	 */
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

	/**
	 * Gets events from its file based on event type and season
	 */
	public static Map<String,Event> getEvents(Event.Type type, Event.Season season) {
		log.entry(type, season);
		String seasonAndTypeString = type == Event.Type.B ? type.name() : season.name() + type.name();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			InputStream eventsInputStream = new ClassPathResource(EVENTS_FOLDER_PATH + "/" + seasonAndTypeString + ".json").getInputStream();
			Map<String,Event> events = objectMapper.readValue(eventsInputStream, new TypeReference<Map<String,Event>>(){});

			// Populate events with additional info
			for (Entry<String,Event> eventEntry : events.entrySet()) {
				Event event = eventEntry.getValue();
				event.populate(eventEntry.getKey(), type, season);
			}
			return log.exit(events);
		}
		catch (IOException e) {
			log.warn("Error reading and parsing file '" + EVENTS_FOLDER_PATH + "/" + seasonAndTypeString + ".json'", e);
			return log.exit(Map.of());
		}
	}

	/**
	 * Gets a scenario from its file based on scenario id
	 */
	public static Scenario getScenario(String scenarioId) {
		log.entry(scenarioId);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			InputStream scenarioInputStream = new ClassPathResource(SCENARIOS_FOLDER_PATH + "/" + scenarioId + ".json").getInputStream();
			Scenario scenario = objectMapper.readValue(scenarioInputStream, Scenario.class);
			scenario.populate();
			return log.exit(scenario);
		}
		catch (IOException e) {
			log.warn("Error reading and parsing file '" + SCENARIOS_FOLDER_PATH + "/" + scenarioId + ".json'", e);
			return log.exit(null);
		}
	}

	/**
	 * Gets all sections not related to scenarios (from the Section Book) from "sections.json" file
	 */
	public static Map<String,Section> getSections() {
		log.entry();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			InputStream sectionsInputStream = new ClassPathResource(SECTIONS_FILE_PATH).getInputStream();
			Map<String,Section> sections = objectMapper.readValue(sectionsInputStream, new TypeReference<Map<String,Section>>(){});

			// Populate sections with additional info
			for (Entry<String,Section> sectionEntry : sections.entrySet()) {
				Section section = sectionEntry.getValue();
				section.populate(sectionEntry.getKey(), false);
			}
			return log.exit(sections);
		}
		catch (IOException e) {
			log.warn("Error reading and parsing file '" + SECTIONS_FILE_PATH + "'", e);
			return log.exit(Map.of());
		}
	}

	/**
	 * Gets all buildings from "buildings.json" file
	 */
	public static Map<String,Building> getBuildings() {
		log.entry();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			InputStream buildingsInputStream = new ClassPathResource(BUILDINGS_FILE_PATH).getInputStream();
			Map<String,Building> buildings = objectMapper.readValue(buildingsInputStream, new TypeReference<Map<String,Building>>(){});

			// Populate buildings with additional info
			for (Entry<String,Building> buildingEntry : buildings.entrySet()) {
				Building building = buildingEntry.getValue();
				building.populate(buildingEntry.getKey());
			}
			return log.exit(buildings);
		}
		catch (IOException e) {
			log.warn("Error reading and parsing file '" + BUILDINGS_FILE_PATH + "'", e);
			return log.exit(null);
		}
	}

}