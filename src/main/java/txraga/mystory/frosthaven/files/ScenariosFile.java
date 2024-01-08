package txraga.mystory.frosthaven.files;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.model.Scenario;


@XSlf4j
@Component
public class ScenariosFile {
	
	private final static String SCENARIOS_FOLDER_PATH = "static/json/scenarios";
	private final static int SCENARIOS_COUNT = 166;


	public Map<String,Scenario> findAllScenariosAsMap() {
		log.entry();
		Map<String,Scenario> scenarios = new HashMap<>();
		for (int i = 0; i <= SCENARIOS_COUNT; i++) {
			String scenarioId = String.format("%03d", i);
			Scenario scenario = findScenarioById(scenarioId);
			if (scenario != null) scenarios.put(scenarioId, scenario);
		}
		return log.exit(scenarios);		
	}

	public Scenario findScenarioById(String scenarioId) {
		log.entry(scenarioId);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			InputStream scenarioInputStream = new ClassPathResource(SCENARIOS_FOLDER_PATH + "/" + scenarioId + ".json").getInputStream();
			Scenario scenario = objectMapper.readValue(scenarioInputStream, Scenario.class);
			
			// Set sections ids
			scenario.getSections().forEach((id, section) -> section.setId(id));
			return log.exit(scenario);
		}
		catch (IOException e) {
			log.warn("Error reading and parsing file '" + SCENARIOS_FOLDER_PATH + "/" + scenarioId + ".json'");
			return log.exit(null);
		}
	}

}
