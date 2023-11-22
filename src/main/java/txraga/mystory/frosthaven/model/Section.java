package txraga.mystory.frosthaven.model;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import txraga.mystory.frosthaven.model.personal.StoryItem;


@Getter
@Setter
@ToString
public class Section {
	
	private String id;
	private boolean partOfScenario;

	private String trigger;
	private String title;
	private StoryItem unlockedBy;

	private String introduction;
	private String text;
	private String specialRules;
	private Map<String,String> bossSpecials = Map.of();
	private String conclusion;
	private String rewards;
	private String sectionLinks;


	public String getHtmlSafeId() {
		return id.replace(".", "_");
	}

	public boolean hasMap(String scenarioId) {
		Resource resource = new ClassPathResource("static/img/scenarios/" + scenarioId + "_" + id + ".png");
		return resource.exists();
	}

	public void populate(String id, boolean partOfScenario) {
		// Set section id and partOfScenario
		if (this.id == null) this.id = id;
		this.partOfScenario = partOfScenario;
		replaceIcons();
	}

	public void replaceIcons() {
		this.trigger = ModelUtils.replaceIcons(this.trigger);
		this.specialRules = ModelUtils.replaceIcons(this.specialRules);
		for (Entry<String,String> bossSpecial : bossSpecials.entrySet()) {
			bossSpecials.put(bossSpecial.getKey(), ModelUtils.replaceIcons(bossSpecial.getValue()));
		}
		this.rewards = ModelUtils.replaceIcons(this.rewards);
		this.sectionLinks = ModelUtils.replaceIcons(this.sectionLinks);
	}

}
