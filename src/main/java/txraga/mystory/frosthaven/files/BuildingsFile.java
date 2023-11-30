package txraga.mystory.frosthaven.files;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.model.Building;


@XSlf4j
@Component
public class BuildingsFile {
	
	private final static String BUILDINGS_FILE_PATH = "static/json/buildings.json";


	public Map<String,Building> findAllBuildingsAsMap() {
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
