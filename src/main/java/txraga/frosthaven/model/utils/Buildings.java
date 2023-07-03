package txraga.frosthaven.model.utils;

import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.XSlf4j;
import txraga.frosthaven.controllers.CampaignUtils;
import txraga.frosthaven.model.Building;


@XSlf4j
@Component
public class Buildings {
	
	private Map<String,Building> buildings;


	@PostConstruct
	private void init() {
		log.entry();
		buildings = CampaignUtils.getBuildings();
		log.exit();
	}

	public Building get(String id) {
		log.entry(id);
		Building building = buildings.get(id);
		if (building == null) {
			log.warn("Building '{}' not found", id);
			return log.exit(null);
		}
		else return log.exit(new Building(building));
	}
	
}
