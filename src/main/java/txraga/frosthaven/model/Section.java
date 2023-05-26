package txraga.frosthaven.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Section {
	
	private String id;
	private String trigger;

	private String specialRules;
	private String introduction;
	private String text;
	private String conclusion;
	private String rewards;


	public void replaceIcons() {
		this.trigger = Scenario.replaceIcons(this.trigger);
		this.specialRules = Scenario.replaceIcons(this.specialRules);
		this.rewards = Scenario.replaceIcons(this.rewards);
	}
}
