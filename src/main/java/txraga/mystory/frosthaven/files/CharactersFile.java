package txraga.mystory.frosthaven.files;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.model.FhCharacter;


@XSlf4j
@Component
public class CharactersFile {
	
	private final static String CHARACTERS_FILE_PATH = "static/json/characters.json";


	public Map<String,FhCharacter> findAllCharactersAsMap() {
		log.entry();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			InputStream charactersInputStream = new ClassPathResource(CHARACTERS_FILE_PATH).getInputStream();
			Map<String,FhCharacter> characters = objectMapper.readValue(charactersInputStream, new TypeReference<Map<String,FhCharacter>>(){});
			return log.exit(characters);
		}
		catch (IOException e) {
			log.warn("Error reading and parsing file '" + CHARACTERS_FILE_PATH + "'", e);
			return log.exit(Map.of());
		}
	}

}
