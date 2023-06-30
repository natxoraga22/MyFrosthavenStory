package txraga.frosthaven.model.utils;

import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.XSlf4j;
import txraga.frosthaven.controllers.CampaignUtils;
import txraga.frosthaven.model.FhCharacter;


@XSlf4j
@Component
public class Characters {
	
	private Map<String,FhCharacter> characters;


	@PostConstruct
	private void init() {
		log.entry();
		characters = CampaignUtils.getCharacters();
		log.exit();
	}

	public FhCharacter get(String id) {
		log.entry(id);
		FhCharacter character = characters.get(id);
		if (character == null) log.warn("Character '{}' not found", id);
		return log.exit(character);
	}

}
