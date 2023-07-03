package txraga.frosthaven.model;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


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
	private String conclusion;
	private String rewards;
	private String sectionLinks;


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
		this.rewards = ModelUtils.replaceIcons(this.rewards);
		this.sectionLinks = ModelUtils.replaceIcons(this.sectionLinks);
	}

}
