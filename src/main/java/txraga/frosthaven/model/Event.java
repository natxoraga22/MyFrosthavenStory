package txraga.frosthaven.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Event {
	
	public enum Season { S, W; }
	public enum Type { O, R, B; }

	private String text;
	private Map<String,Option> options;

	@Getter
	@Setter
	@ToString
	public static class Option {
		private String trigger;
		private String text;
		private String rewards;
	}

}
