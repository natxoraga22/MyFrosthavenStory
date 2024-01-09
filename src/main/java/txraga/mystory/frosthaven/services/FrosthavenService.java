package txraga.mystory.frosthaven.services;

import java.util.Map;

import txraga.mystory.frosthaven.model.Building;
import txraga.mystory.frosthaven.model.Event;
import txraga.mystory.frosthaven.model.FhCharacter;
import txraga.mystory.frosthaven.model.PersonalQuest;
import txraga.mystory.frosthaven.model.Scenario;
import txraga.mystory.frosthaven.model.Section;


public interface FrosthavenService {

	/** Gets chapter "Welcome to Frosthaven" content from "welcome.txt" file */
	public String getWelcome();

	/** Gets all characters from "characters.json" file */
	public Map<String,FhCharacter> findAllCharacters();

	/** Gets all personal quests from "personalQuests.json" file */
	public Map<String,PersonalQuest> findAllPersonalQuests(Map<String,Building> buildings);

	/** Gets all events (road, outpost, etc.) from all files inside "events" folder (there is a file for each type/season) */
	public Map<String,Event> findAllEvents(Map<String,Scenario> scenarios);

	/** Gets all scenarios from all files inside "scenarios" folder (there is a file for each scenario) */
	public Map<String,Scenario> findAllScenarios();

	/** Gets all buildings from "buildings.json" file */
	public Map<String,Building> findAllBuildings(Map<String,Scenario> scenarios);

	/** Gets all sections not related to scenarios (from the Section Book) from "sections.json" file */
	public Map<String,Section> findAllSections(Map<String,Scenario> scenarios);

}
