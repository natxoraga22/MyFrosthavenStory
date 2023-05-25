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
	private String text;
	private String conclusion;
	private String rewards;

}
