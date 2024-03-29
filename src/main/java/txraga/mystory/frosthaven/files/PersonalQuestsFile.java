package txraga.mystory.frosthaven.files;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.XSlf4j;
import txraga.mystory.frosthaven.model.PersonalQuest;


@XSlf4j
@Component
public class PersonalQuestsFile {

	private final static String PERSONAL_QUESTS_FILE_PATH = "static/json/personalQuests.json";


	public Map<String,PersonalQuest> findAllPersonalQuestsAsMap() {
		log.entry();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			InputStream personalQuestsInputStream = new ClassPathResource(PERSONAL_QUESTS_FILE_PATH).getInputStream();
			Map<String,PersonalQuest> personalQuests = objectMapper.readValue(personalQuestsInputStream, new TypeReference<Map<String,PersonalQuest>>(){});

			// Set personal quests ids
			personalQuests.forEach((id, personalQuest) -> personalQuest.setId(id));
			return log.exit(personalQuests);
		}
		catch (IOException e) {
			log.warn("Error reading and parsing file '" + PERSONAL_QUESTS_FILE_PATH + "'", e);
			return log.exit(Map.of());
		}
	}

}
