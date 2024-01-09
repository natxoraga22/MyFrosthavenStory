package txraga.mystory.frosthaven.files;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

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

			buildings.forEach((id, building) -> {
				// Set building id
				building.setId(id);

				building.getLevels().forEach((number, level) -> {
					// Set level number
					level.setNumber(Integer.parseInt(number));
					// Set sections ids
					level.getBuiltSections().forEach((sectionId, section) -> section.setId(sectionId));
				});
			});
			return log.exit(buildings);
		}
		catch (IOException e) {
			log.warn("Error reading and parsing file '" + BUILDINGS_FILE_PATH + "'", e);
			return log.exit(Map.of());
		}
	}

}
