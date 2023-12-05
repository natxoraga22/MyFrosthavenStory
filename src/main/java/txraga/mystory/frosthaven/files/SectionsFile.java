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
import txraga.mystory.frosthaven.model.Section;


@XSlf4j
@Component
public class SectionsFile {
	
	private final static String SECTIONS_FILE_PATH = "static/json/sections.json";


	public Map<String,Section> findAllSectionsAsMap() {
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

}
