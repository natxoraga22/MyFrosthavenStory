package txraga.frosthaven.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class PersonalQuest {

	private String id;
	private String title;
	private String description;
	private String requirements;
	private String rewards;
	private String alternateRewards;


	public void populate(String id) {
		// Set personal quest id
		if (this.id == null) this.id = id;
		replaceIcons();
	}

	public void replaceIcons() {
		this.rewards = ModelUtils.replaceIcons(this.rewards);
		this.alternateRewards = ModelUtils.replaceIcons(this.alternateRewards);
	}

}
