package txraga.frosthaven.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.XSlf4j;
import txraga.frosthaven.model.Event;
import txraga.frosthaven.model.FhCharacter;
import txraga.frosthaven.model.Party;
import txraga.frosthaven.model.Section;
import txraga.frosthaven.model.StoryItem;


@XSlf4j
public class CampaignUtils {
	
	private final static String MY_STORY_FILE_PATH = "static/json/myStory.json";
	private final static String WELCOME_FILE_PATH = "static/json/welcome.txt";
	private final static String PARTY_FILE_PATH = "static/json/myParty.json";
	private final static String CHARACTERS_FILE_PATH = "static/json/characters.json";
	private final static String SECTIONS_FILE_PATH = "static/json/sections.json";
	private final static String EVENTS_FOLDER_PATH = "static/json/events/";


	/** Get chapter "The party" content from "myParty.json" file */
	public static List<StoryItem> getMyStory() throws IOException {
		log.entry();
		ObjectMapper objectMapper = new ObjectMapper();
		File myStoryFile = new ClassPathResource(MY_STORY_FILE_PATH).getFile();
		return log.exit(objectMapper.readValue(myStoryFile, new TypeReference<List<StoryItem>>(){}));
	}

	/** Get chapter "Welcome to Frosthaven" content from "welcome.txt" file */
	public static String getWelcome() throws IOException {
		log.entry();
		File welcomeFile = new ClassPathResource(WELCOME_FILE_PATH).getFile();
		List<String> welcomeLines = Files.readAllLines(welcomeFile.toPath());
		return log.exit(String.join("<br/>", welcomeLines));
	}

	/** Get chapter "The party" content from "myParty.json" file */
	public static Party getParty() throws IOException {
		log.entry();
		ObjectMapper objectMapper = new ObjectMapper();
		File partyFile = new ClassPathResource(PARTY_FILE_PATH).getFile();
		Party party = objectMapper.readValue(partyFile, Party.class);

		Map<String,FhCharacter> charactersBackgrounds = CampaignUtils.getCharacters();
		for (FhCharacter character : party.getCharacters()) {
			character.setBackground(charactersBackgrounds.get(character.getNameId()).getBackground());
		}
		return log.exit(party);
	}

	/** Get all Frosthaven characters info (background, etc.) from "characters.json" file */
	public static Map<String,FhCharacter> getCharacters() throws IOException {
		log.entry();
		ObjectMapper objectMapper = new ObjectMapper();
		File charactersFile = new ClassPathResource(CHARACTERS_FILE_PATH).getFile();
		return log.exit(objectMapper.readValue(charactersFile, new TypeReference<Map<String,FhCharacter>>(){}));		
	}

	/** Get all sections not related to scenarios (from the Section Book) from "sections.json" file */
	public static Map<String,Section> getSections() throws IOException {
		log.entry();
		ObjectMapper objectMapper = new ObjectMapper();
		File sectionsFile = new ClassPathResource(SECTIONS_FILE_PATH).getFile();
		Map<String,Section> sections = objectMapper.readValue(sectionsFile, new TypeReference<Map<String,Section>>(){});
		sections.values().forEach(section -> section.setSectionBook(true));
		return log.exit(sections);
	}

	/** Get all Frosthaven events (road, outpost, etc.) from all files inside "events" folder (there is a file by each type of event) */
	public static Map<String,Map<String,Event>> getEvents() throws IOException {
		log.entry();
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String,Map<String,Event>> events = new HashMap<>();

		for (Event.Season season : Event.Season.values()) {
			for (Event.Type type : Event.Type.values()) {
				String seasonAndType = type == Event.Type.B ? type.name() : season.name() + type.name();

				File eventsFile = new ClassPathResource(EVENTS_FOLDER_PATH + seasonAndType + ".json").getFile();
				Map<String,Event> seasonAndTypeEvents = objectMapper.readValue(eventsFile, new TypeReference<Map<String,Event>>(){});
				for (Event event : seasonAndTypeEvents.values()) {
					event.setSeason(season);
					event.setType(type);
				}
				events.put(seasonAndType, seasonAndTypeEvents);
			}
		}
		return log.exit(events);
	}

}
