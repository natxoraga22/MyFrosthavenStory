package txraga.mystory.frosthaven.model;

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
		if (this.rewards != null) this.rewards.replaceIcons();
		if (this.alternateRewards != null) this.alternateRewards.replaceIcons();
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
