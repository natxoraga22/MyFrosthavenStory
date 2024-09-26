package txraga.mystory.frosthaven.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class UnlockedBy {

	private UnlockedBy.Event event;
	private UnlockedBy.Scenario scenario;
	private UnlockedBy.Section section;
	private String other;


	@Getter
	@Setter
	@ToString
	public static class Event {
		private String id;
	}

	@Getter
	@Setter
	@ToString
	public static class Scenario {
		private String id;
	}

	@Getter
	@Setter
	@ToString
	public static class Section {
		private String id;

		public String getHtmlSafeId() {
			return id.replace(".", "_");
		}
	}

}
