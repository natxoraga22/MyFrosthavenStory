package txraga.frosthaven.model.utils;

import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.XSlf4j;
import txraga.frosthaven.controllers.CampaignUtils;
import txraga.frosthaven.model.Section;


@XSlf4j
@Component
public class SectionBook {
	
	private Map<String,Section> sections;


	@PostConstruct
	private void init() {
		log.entry();
		sections = CampaignUtils.getSections();
		log.exit();
	}

	public Section get(String id) {
		log.entry(id);
		Section section = sections.get(id);
		if (section == null) log.warn("Section '{}' not found", id);
		return log.exit(section);
	}

}
