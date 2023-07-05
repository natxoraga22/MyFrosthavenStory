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
	private Rewards rewards;
	private Rewards alternateRewards;


	public void populate(String id) {
		// Set personal quest id
		if (this.id == null) this.id = id;
		replaceIcons();
	}

	public void replaceIcons() {
		this.rewards.replaceIcons();
		this.alternateRewards.replaceIcons();
	}


	/* ------ */
	/* REWARD */
	/* ------ */

	@Getter
	@Setter
	@ToString
	public static class Rewards {
		private String envelope;
		private String building;

		public void replaceIcons() {
			this.envelope = ModelUtils.replaceIcons(this.envelope);
		}
	}

}
