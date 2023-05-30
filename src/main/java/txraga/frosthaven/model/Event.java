package txraga.frosthaven.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Event extends StoryObject {
	
	public enum Season { S, W; }
	public enum Type { O, R, B; }

	@Getter
	@Setter
	@ToString
	public static class Option {
		private String trigger;
		private String text;
		private String rewards;
	}

	private String id;
	private String text;
	private Map<String,Option> options;
	
	@Override
	public StoryObject.Type getType() {
		return StoryObject.Type.EVENT;
	}

}
