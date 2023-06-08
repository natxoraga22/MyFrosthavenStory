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
	private String title;
	private StoryItem unlockedBy;
	private String trigger;

	private String specialRules;
	private String introduction;
	private String text;
	private String conclusion;
	private String rewards;


	public boolean hasMap(String scenarioId) {
		Resource resource = new ClassPathResource("static/img/scenarios/" + scenarioId + "_" + id + ".png");
		return resource.exists();
	}

	public void replaceIcons() {
		this.trigger = Scenario.replaceIcons(this.trigger);
		this.specialRules = Scenario.replaceIcons(this.specialRules);
		this.rewards = Scenario.replaceIcons(this.rewards);
	}
}
